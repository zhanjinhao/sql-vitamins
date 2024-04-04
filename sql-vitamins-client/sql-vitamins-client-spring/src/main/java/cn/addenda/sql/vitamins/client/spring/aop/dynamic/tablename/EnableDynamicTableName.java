package cn.addenda.sql.vitamins.client.spring.aop.dynamic.tablename;

import cn.addenda.sql.vitamins.client.spring.aop.NamedConfigurer;
import cn.addenda.sql.vitamins.client.spring.aop.SqlVitaminsAopMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * @author addenda
 * @since 2023/6/10 18:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DynamicTableNameSelector.class)
public @interface EnableDynamicTableName {

  int order() default Ordered.LOWEST_PRECEDENCE;

  SqlVitaminsAopMode sqlVitaminsAopMode() default SqlVitaminsAopMode.PROXY_CONFIG;

  boolean removeEnter() default true;

  String dynamicTableNameRewriter() default NamedConfigurer.DEFAULT;

}