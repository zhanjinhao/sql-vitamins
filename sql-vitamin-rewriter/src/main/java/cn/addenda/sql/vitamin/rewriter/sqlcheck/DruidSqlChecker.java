package cn.addenda.sql.vitamin.rewriter.sqlcheck;

import cn.addenda.sql.vitamin.rewriter.visitor.condition.DmlConditionExistsVisitor;
import cn.addenda.sql.vitamin.rewriter.visitor.identifier.ExactIdentifierVisitor;
import cn.addenda.sql.vitamin.rewriter.visitor.identifier.StarOfSelectItemExistsVisitor;

/**
 * @author addenda
 * @since 2023/5/7 19:58
 */
public class DruidSqlChecker implements SqlChecker {

  @Override
  public boolean exactIdentifier(String sql) {
    ExactIdentifierVisitor exactIdentifierVisitor = new ExactIdentifierVisitor(sql);
    exactIdentifierVisitor.visit();
    return exactIdentifierVisitor.isExact();
  }

  @Override
  public boolean allColumnExists(String sql) {
    StarOfSelectItemExistsVisitor starOfSelectItemExistsVisitor = new StarOfSelectItemExistsVisitor(sql);
    starOfSelectItemExistsVisitor.visit();
    return starOfSelectItemExistsVisitor.isExists();
  }

  @Override
  public boolean dmlConditionExists(String sql) {
    DmlConditionExistsVisitor dmlConditionExistsVisitor = new DmlConditionExistsVisitor(sql);
    dmlConditionExistsVisitor.visit();
    return dmlConditionExistsVisitor.isExists();
  }

}
