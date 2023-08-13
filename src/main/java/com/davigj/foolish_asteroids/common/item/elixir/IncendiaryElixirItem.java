package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IncendiaryElixirItem extends ElixirItem {
    public static final Map<UUID, Integer> smokingPlayers = new HashMap<>();

    public IncendiaryElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        // Set the duration of the effect (in ticks)
        int effectDuration = 600; // Change this value to the desired duration
        // Activate the particle effect with the specified duration
        smokingPlayers.put(player.getUUID(), effectDuration);
    }
}
