package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class DilatoryElixirItem extends ElixirItem {

    public DilatoryElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float thirdPerson = ScaleTypes.THIRD_PERSON.getScaleData(entityLiving).getBaseScale();
        float visibility = ScaleTypes.VISIBILITY.getScaleData(entityLiving).getBaseScale();
        float eyeHeight = ScaleTypes.EYE_HEIGHT.getScaleData(entityLiving).getBaseScale();

        if (thirdPerson < 5.0) {
            ScaleTypes.THIRD_PERSON.getScaleData(entityLiving).setTargetScale(thirdPerson + 0.3f);
        }
        if (visibility < 4.0) {
            ScaleTypes.VISIBILITY.getScaleData(entityLiving).setTargetScale(visibility + 0.3f);
        }
        if (eyeHeight > 5.0) {
            ScaleTypes.EYE_HEIGHT.getScaleData(entityLiving).setTargetScale(eyeHeight + 0.3f);
        }
    }
}
