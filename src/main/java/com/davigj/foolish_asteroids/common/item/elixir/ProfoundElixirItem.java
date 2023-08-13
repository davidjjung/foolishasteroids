package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ProfoundElixirItem extends ElixirItem {
    public static final Map<ServerPlayer, Long> chatDisableMap = new HashMap<>();
    private static final long silenceDuration = 15000L;

    public ProfoundElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        MinecraftServer server = entityLiving.getLevel().getServer();
        if (server != null) {
            for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving.getBoundingBox().inflate(4.0D, 3.0D, 4.0D))) {
                if (!living.getUUID().equals(player.getUUID())) {
                    if (living instanceof Player) {
                        chatDisableMap.put((ServerPlayer) living, System.currentTimeMillis() + silenceDuration);
                        TranslatableComponent message = new TranslatableComponent("message.profound.victim");
                        ((Player) living).displayClientMessage(message, true);
                    }
                } else {
                    TranslatableComponent message = new TranslatableComponent("message.profound.used");
                    ((Player) living).displayClientMessage(message, true);
                }
            }
        }
    }
}
