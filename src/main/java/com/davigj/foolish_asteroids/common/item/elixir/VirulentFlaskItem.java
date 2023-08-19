package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.util.FoolishAsteroidsDamageSources;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Random;

public class VirulentFlaskItem extends ElixirItem {

    public VirulentFlaskItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        boolean virulentResistant = false;
        for(MobEffectInstance effectInstance : player.getActiveEffects()) {
            if(Objects.requireNonNull(effectInstance.getEffect().getRegistryName()).toString().equals("undergarden:virulent_resistance")) {
                virulentResistant = true;
            }
        }
        if (!virulentResistant) {
            switch (new Random().nextInt(5)) {
                case 0 -> player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false));
                case 1 -> player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 0, false, false));
                case 2 -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 1, false, false));
                case 3 -> player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false));
                case 4 -> player.addEffect(new MobEffectInstance(AMEffectRegistry.FEAR, 200, 0, false, false));
            }
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0, false, false));
            player.hurt(FoolishAsteroidsDamageSources.VIRULENT, 6.0F);
        }
        player.hurt(FoolishAsteroidsDamageSources.VIRULENT, 1.0F);
    }
}
