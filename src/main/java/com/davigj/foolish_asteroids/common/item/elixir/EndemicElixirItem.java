package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EndemicElixirItem extends ElixirItem {

    public EndemicElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
            living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0, false, true));
        }
    }
}
