package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.teamabnormals.upgrade_aquatic.core.registry.UAMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class IndomitableElixirItem extends ElixirItem {
    private static final int RAINBOW_DURATION = 160;
    public static final Map<UUID, Integer> rainbowTimers = new HashMap<>();

    public IndomitableElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        // Be invincible for eight seconds, have Speed II for eight seconds
        player.addEffect(new MobEffectInstance(UAMobEffects.REPELLENCE.get(), RAINBOW_DURATION, 4));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, RAINBOW_DURATION, 1));

        UUID playerId = player.getUUID();
        rainbowTimers.put(playerId, RAINBOW_DURATION); // Start the timer

        RainbowUtil.setRainbowType(entityLiving, 1);
    }
}
