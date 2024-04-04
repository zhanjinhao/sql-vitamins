package cn.addenda.sql.vitamins.rewriter.tombstone;

import cn.addenda.sql.vitamins.rewriter.AbstractSqlInterceptor;
import cn.addenda.sql.vitamins.rewriter.util.ExceptionUtil;
import cn.addenda.sql.vitamins.rewriter.util.JdbcSQLUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 只要配置了拦截器就会执行SQL拦截。
 *
 * @author addenda
 * @since 2023/5/2 17:33
 */
@Slf4j
public class TombstoneSqlInterceptor extends AbstractSqlInterceptor {

  private final TombstoneRewriter tombstoneRewriter;
  private final boolean defaultDisable;
  private final boolean defaultJoinUseSubQuery;

  public TombstoneSqlInterceptor(
      boolean removeEnter, TombstoneRewriter tombstoneRewriter,
      boolean defaultDisable, boolean joinUseSubQuery) {
    super(removeEnter);
    if (tombstoneRewriter == null) {
      throw new TombstoneException("`tombstoneRewriter` can not be null!");
    }
    this.tombstoneRewriter = tombstoneRewriter;
    this.defaultDisable = defaultDisable;
    this.defaultJoinUseSubQuery = joinUseSubQuery;
  }

  @Override
  public String rewrite(String sql) {

    Boolean disable = JdbcSQLUtils.getOrDefault(TombstoneContext.getDisable(), defaultDisable);
    if (Boolean.TRUE.equals(disable)) {
      return sql;
    }
    log.debug("Tombstone, before sql: [{}].", removeEnter(sql));

    if (!TombstoneContext.contextActive()) {
      try {
        TombstoneContext.push(new TombstoneConfig());
        sql = doRewrite(sql);
      } finally {
        TombstoneContext.remove();
      }
      return sql;
    } else {
      sql = doRewrite(sql);
    }

    log.debug("Tombstone, after sql: [{}].", removeEnter(sql));
    return sql;
  }

  private String doRewrite(String sql) {
    String newSql = sql;
    try {
      if (JdbcSQLUtils.isDelete(newSql)) {
        newSql = tombstoneRewriter.rewriteDeleteSql(newSql);
      } else if (JdbcSQLUtils.isSelect(newSql)) {
        Boolean useSubQuery =
            JdbcSQLUtils.getOrDefault(TombstoneContext.getJoinUseSubQuery(), defaultJoinUseSubQuery);
        newSql = tombstoneRewriter.rewriteSelectSql(newSql, useSubQuery);
      } else if (JdbcSQLUtils.isUpdate(newSql)) {
        newSql = tombstoneRewriter.rewriteUpdateSql(newSql);
      } else if (JdbcSQLUtils.isInsert(newSql)) {
        Boolean useSubQuery =
            JdbcSQLUtils.getOrDefault(TombstoneContext.getJoinUseSubQuery(), defaultJoinUseSubQuery);
        newSql = tombstoneRewriter.rewriteInsertSql(newSql, useSubQuery);
      } else {
        throw new TombstoneException("仅支持select、update、delete、insert语句，当前SQL：" + sql + "。");
      }
    } catch (TombstoneException tombstoneException) {
      throw tombstoneException;
    } catch (Throwable throwable) {
      String msg = String.format("物理删除改写为逻辑删除时出错，SQL：[%s]。", removeEnter(sql));
      throw new TombstoneException(msg, ExceptionUtil.unwrapThrowable(throwable));
    }
    return newSql;
  }

  @Override
  public int order() {
    // baseEntity不会处理delete语句，所以tombstone需要先于baseEntity执行
    return MAX / 2 - 70000;
  }

}