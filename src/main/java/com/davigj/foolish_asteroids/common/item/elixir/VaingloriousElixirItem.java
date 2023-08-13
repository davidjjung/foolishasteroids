package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class VaingloriousElixirItem extends ElixirItem {

    public VaingloriousElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float stepHeight = ScaleTypes.STEP_HEIGHT.getScaleData(entityLiving).getBaseScale();
        float fallDamage = ScaleTypes.FALLING.getScaleData(entityLiving).getBaseScale();
        float height = ScaleTypes.HEIGHT.getScaleData(entityLiving).getBaseScale();
        float width = ScaleTypes.WIDTH.getScaleData(entityLiving).getBaseScale();

        if (stepHeight < 10.0) {
            ScaleTypes.STEP_HEIGHT.getScaleData(entityLiving).setTargetScale(stepHeight + 1.0f);
        }
        if (fallDamage < 10.0) {
            ScaleTypes.FALLING.getScaleData(entityLiving).setTargetScale(fallDamage + 1.0f);
        }
        if (height < 5.0) {
            ScaleTypes.HEIGHT.getScaleData(entityLiving).setTargetScale(fallDamage + .5f);
        }
        if (width < 5.0) {
            ScaleTypes.WIDTH.getScaleData(entityLiving).setTargetScale(fallDamage + .5f);
        }
    }
}
