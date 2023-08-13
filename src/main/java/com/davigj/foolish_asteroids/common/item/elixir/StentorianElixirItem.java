package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class StentorianElixirItem extends ElixirItem {

    public StentorianElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float miningSpeed = ScaleTypes.MINING_SPEED.getScaleData(entityLiving).getBaseScale();
        float visibility = ScaleTypes.VISIBILITY.getScaleData(entityLiving).getBaseScale();

        if (miningSpeed < 2.8) {
            ScaleTypes.MINING_SPEED.getScaleData(entityLiving).setTargetScale(miningSpeed + 0.3f);
        }
        if (visibility < 5.0) {
            ScaleTypes.VISIBILITY.getScaleData(entityLiving).setTargetScale(visibility + 0.5f);
        }
    }
}
