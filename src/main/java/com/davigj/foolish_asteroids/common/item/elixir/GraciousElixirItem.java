package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class GraciousElixirItem extends ElixirItem {

    public GraciousElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float entityReach = ScaleTypes.ENTITY_REACH.getScaleData(entityLiving).getBaseScale();
        float fallDamage = ScaleTypes.FALLING.getScaleData(entityLiving).getBaseScale();

        if (entityReach > 0.2) {
            ScaleTypes.ENTITY_REACH.getScaleData(entityLiving).setTargetScale(entityReach - 0.2f);
        }
        if (fallDamage > 0.5) {
            ScaleTypes.FALLING.getScaleData(entityLiving).setTargetScale(fallDamage - 0.1f);
        }
    }
}
