package cn.addenda.sql.vitamins.client.common.config;

import cn.addenda.sql.vitamins.client.common.annotation.*;
import cn.addenda.sql.vitamins.client.common.constant.BoolConfig;
import cn.addenda.sql.vitamins.client.common.constant.Propagation;
import cn.addenda.sql.vitamins.rewriter.tombstone.TombstoneConfig;
import cn.addenda.sql.vitamins.rewriter.tombstone.TombstoneContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author addenda
 * @since 2023/6/11 16:42
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TombstoneConfigUtils {

  public static void configTombstone(Propagation propagation, TombstoneConfig tombstoneConfig) {
    Propagation.assertNotNull(propagation);
    Boolean disable = tombstoneConfig.getDisable();
    if (disable != null) {
      Propagation.configWithPropagation(propagation, disable,
          TombstoneContext::setDisable, TombstoneContext::getDisable);
    }
    Boolean joinUseSubQuery = tombstoneConfig.getJoinUseSubQuery();
    if (joinUseSubQuery != null) {
      Propagation.configWithPropagation(propagation, joinUseSubQuery,
          TombstoneContext::setJoinUseSubQuery, TombstoneContext::getJoinUseSubQuery);
    }
  }

  public static void configTombstone(ConfigTombstone configTombstone) {
    Propagation propagation = configTombstone.propagation();
    configTombstone(propagation,
        TombstoneConfig.of(
            BoolConfig.toBoolean(configTombstone.disable()),
            BoolConfig.toBoolean(configTombstone.joinUseSubQuery())));
  }

  public static void pushTombstone(Propagation propagation) {
    Propagation.assertNotNull(propagation);
    // NEW 压入新的
    if (propagation == Propagation.NEW || !TombstoneContext.contextActive()) {
      TombstoneContext.push(new TombstoneConfig());
    }
    // MERGE_* 压入带参数的
    else {
      TombstoneConfig tombstoneConfig = TombstoneContext.peek();
      TombstoneContext.push(new TombstoneConfig(tombstoneConfig));
    }
  }

}
