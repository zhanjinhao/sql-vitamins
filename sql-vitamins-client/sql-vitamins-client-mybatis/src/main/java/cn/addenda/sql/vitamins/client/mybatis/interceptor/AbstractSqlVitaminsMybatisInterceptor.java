package cn.addenda.sql.vitamins.client.mybatis.interceptor;

import cn.addenda.sql.vitamins.client.mybatis.helper.DefaultMsIdExtractHelper;
import cn.addenda.sql.vitamins.client.mybatis.helper.MsIdExtractHelper;
import cn.addenda.sql.vitamins.rewriter.SqlVitaminsException;
import cn.addenda.sql.vitamins.rewriter.dynamic.DynamicSQLException;
import lombok.Setter;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @author addenda
 * @since 2023/6/11 12:19
 */
public abstract class AbstractSqlVitaminsMybatisInterceptor implements Interceptor {

  @Setter
  protected MsIdExtractHelper msIdExtractHelper;

  protected AbstractSqlVitaminsMybatisInterceptor(MsIdExtractHelper msIdExtractHelper) {
    this.msIdExtractHelper = msIdExtractHelper;
  }

  protected AbstractSqlVitaminsMybatisInterceptor() {
  }

  @Override
  public Object plugin(Object target) {
    // 兼容低版本 mybatis
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
    if (msIdExtractHelper != null) {
      String aMsIdExtractHelper = (String) properties.get("msIdExtractHelper");

      if (aMsIdExtractHelper != null) {
        Class<? extends MsIdExtractHelper> aClass;
        try {
          aClass = (Class<? extends MsIdExtractHelper>) Class.forName(aMsIdExtractHelper);
        } catch (Exception e) {
          String msg = String.format("找不到类，MsIdExtractHelper初始化失败：[%s]。", aMsIdExtractHelper);
          throw new DynamicSQLException(msg, e);
        }

        this.msIdExtractHelper = newInstance(aClass);
      } else {
        this.msIdExtractHelper = DefaultMsIdExtractHelper.getInstance();
      }
    }
  }

  private <T> T newInstance(Class<? extends T> aClass) {
    try {
      Method[] methods = aClass.getMethods();
      for (Method method : methods) {
        if (method.getName().equals("getInstance") && Modifier.isStatic(method.getModifiers()) &&
            method.getParameterCount() == 0) {
          return (T) method.invoke(null);
        }
      }
      return aClass.newInstance();
    } catch (Exception e) {
      throw new SqlVitaminsException("创建对象异常，MsIdExtractHelper初始化失败：" + aClass.getName(), e);
    }
  }

}
