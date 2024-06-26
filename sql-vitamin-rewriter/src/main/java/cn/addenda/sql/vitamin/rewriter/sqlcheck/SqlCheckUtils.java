package cn.addenda.sql.vitamin.rewriter.sqlcheck;

import cn.addenda.sql.vitamin.rewriter.util.ExceptionUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

/**
 * @author addenda
 * @since 2023/5/7 15:55
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlCheckUtils {

  public static void check(SqlCheckConfig sqlCheckConfig, Runnable runnable) {
    SqlCheckContext.push(sqlCheckConfig);
    try {
      runnable.run();
    } catch (Throwable e) {
      throw ExceptionUtil.reportAsRuntimeException(e, SqlCheckException.class);
    } finally {
      SqlCheckContext.pop();
    }
  }

  public static <T> T check(SqlCheckConfig sqlCheckConfig, Supplier<T> supplier) {
    SqlCheckContext.push(sqlCheckConfig);
    try {
      return supplier.get();
    } catch (Throwable e) {
      throw ExceptionUtil.reportAsRuntimeException(e, SqlCheckException.class);
    } finally {
      SqlCheckContext.pop();
    }
  }

}
