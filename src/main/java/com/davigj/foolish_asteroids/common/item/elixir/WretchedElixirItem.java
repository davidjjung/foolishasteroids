package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WretchedElixirItem extends ElixirItem {

    public WretchedElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
            living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 1, false, true));
            living.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 1, false, true));
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1, false, true));
            living.addEffect(new MobEffectInstance(MobEffects.WITHER, 300, 1, false, true));
        }
    }
}
