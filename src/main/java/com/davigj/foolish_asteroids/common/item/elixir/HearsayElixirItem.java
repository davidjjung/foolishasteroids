package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class HearsayElixirItem extends ElixirItem {
    public static final Map<ServerPlayer, Long> oracleMap = new HashMap<>();
    private static final long oracleDuration = 60000L;

    public HearsayElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
        }
        MinecraftServer server = entityLiving.getLevel().getServer();
        if (server != null) {
            // for the specified duration, put the player on the oracleMap
            assert entityLiving instanceof ServerPlayer;
            oracleMap.put((ServerPlayer) entityLiving, System.currentTimeMillis() + oracleDuration);

            TrackedDataManager.INSTANCE.setValue(entityLiving, FoolishAsteroidsMod.HEARSAY_ACTIVE, true);
            TranslatableComponent message = new TranslatableComponent("message.hearsay.user");
            ((Player) entityLiving).displayClientMessage(message, true);
        }
    }
}
