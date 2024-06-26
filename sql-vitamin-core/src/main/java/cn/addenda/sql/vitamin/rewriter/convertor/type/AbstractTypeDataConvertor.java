package cn.addenda.sql.vitamin.rewriter.convertor.type;

import cn.addenda.sql.vitamin.rewriter.convertor.sqlexpr.AbstractSQLExprDataConvertor;
import cn.addenda.sql.vitamin.rewriter.convertor.AbstractDataConvertor;
import com.alibaba.druid.sql.ast.SQLExpr;

/**
 * <ul>
 *   <li>String format(Object obj): Object -> MySQL String</li>
 *   <li>R parse(Object obj): Object -> SQLExpr</li>
 *   <li>R parse(String str): Java String -> SQLExpr</li>
 * </ul>
 *
 * @author addenda
 * @since 2023/7/2 15:53
 */
public abstract class AbstractTypeDataConvertor<T, R extends SQLExpr> extends AbstractDataConvertor<T, R> {

  private final AbstractSQLExprDataConvertor<R> formatter;

  protected AbstractTypeDataConvertor(AbstractSQLExprDataConvertor<R> formatter) {
    this.formatter = formatter;
  }

  @Override
  public String format(Object obj) {
    R parse = parse(obj);
    return formatter.format(parse);
  }

  @Override
  public R doParse(String str) {
    return formatter.doParse(str);
  }

  @Override
  public boolean strMatch(String str) {
    return formatter.strMatch(str);
  }

}
