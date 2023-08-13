package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EarthboundElixirItem extends ElixirItem {

    public EarthboundElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        player.teleportTo(x, y-2, z);
    }
}
