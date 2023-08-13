package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.function.Predicate;

public class FreakyElixirItem extends ElixirItem {

    public FreakyElixirItem(Properties properties) {
        super(properties);
    }
    public static boolean hasNearbyPlayer(ServerPlayer player, double radius) {
        return player.getLevel().getPlayers((Predicate<? super ServerPlayer>) player).stream()
                .anyMatch(otherPlayer -> otherPlayer != player && otherPlayer.distanceToSqr(player) <= radius * radius);
    }

    public static ServerPlayer getClosestPlayer(ServerPlayer sourcePlayer) {
        double closestDistanceSq = Double.MAX_VALUE;
        ServerPlayer closestPlayer = null;

        for (ServerPlayer targetPlayer : sourcePlayer.getServer().getPlayerList().getPlayers()) {
            if (targetPlayer != sourcePlayer) {
                double distanceSq = sourcePlayer.distanceToSqr(targetPlayer);
                if (distanceSq < closestDistanceSq) {
                    closestDistanceSq = distanceSq;
                    closestPlayer = targetPlayer;
                }
            }
        }

        return closestPlayer;
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        MinecraftServer server = entityLiving.getLevel().getServer();
        if (server != null) {
            assert entityLiving instanceof ServerPlayer;
            if (hasNearbyPlayer((ServerPlayer)entityLiving, 4.0)) {
                ServerPlayer victim = getClosestPlayer((ServerPlayer)entityLiving);
                ScaleData sourceHeightData = ScaleTypes.HEIGHT.getScaleData(entityLiving);
                ScaleData sourceWidthData = ScaleTypes.WIDTH.getScaleData(entityLiving);
                ScaleData sourceAttackData = ScaleTypes.ATTACK.getScaleData(entityLiving);
                ScaleData sourceDefenseData = ScaleTypes.DEFENSE.getScaleData(entityLiving);

                ScaleData victimHeightData = ScaleTypes.HEIGHT.getScaleData(victim);
                ScaleData victimWidthData = ScaleTypes.WIDTH.getScaleData(victim);
                ScaleData victimAttackData = ScaleTypes.ATTACK.getScaleData(victim);
                ScaleData victimDefenseData = ScaleTypes.DEFENSE.getScaleData(victim);

                float sourceHeight = sourceHeightData.getBaseScale();
                float sourceWidth = sourceWidthData.getBaseScale();
                float sourceAttack = sourceAttackData.getBaseScale();
                float sourceDefense = sourceDefenseData.getBaseScale();

                float victimHeight = victimHeightData.getBaseScale();
                float victimWidth = victimWidthData.getBaseScale();
                float victimAttack = victimAttackData.getBaseScale();
                float victimDefense = victimDefenseData.getBaseScale();

                sourceHeightData.setTargetScale(victimHeight);
                sourceWidthData.setTargetScale(victimWidth);
                sourceAttackData.setTargetScale(victimAttack);
                sourceDefenseData.setTargetScale(victimDefense);

                victimHeightData.setTargetScale(sourceHeight);
                victimWidthData.setTargetScale(sourceWidth);
                victimAttackData.setTargetScale(sourceAttack);
                victimDefenseData.setTargetScale(sourceDefense);

                TranslatableComponent msg = new TranslatableComponent("message.freaky.freaker");
                ((Player)entityLiving).displayClientMessage(msg, true);
                TranslatableComponent vmsg = new TranslatableComponent("message.freaky.freakee");
                ((Player)entityLiving).displayClientMessage(vmsg, true);
            } else {
                TranslatableComponent msg = new TranslatableComponent("message.freaky.freakless");
                ((Player)entityLiving).displayClientMessage(msg, true);
            }
        }
    }
}
