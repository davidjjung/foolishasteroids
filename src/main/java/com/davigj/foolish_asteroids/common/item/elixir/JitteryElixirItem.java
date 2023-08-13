package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class JitteryElixirItem extends ElixirItem {

    public JitteryElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float motion = ScaleTypes.MOTION.getScaleData(entityLiving).getBaseScale();
        float viewBobbing = ScaleTypes.VIEW_BOBBING.getScaleData(entityLiving).getBaseScale();
        float defense = ScaleTypes.DEFENSE.getScaleData(entityLiving).getBaseScale();

        if (motion < 2.8) {
            ScaleTypes.MOTION.getScaleData(entityLiving).setTargetScale(motion + 0.2f);
        }
        if (viewBobbing > .2) {
            ScaleTypes.VIEW_BOBBING.getScaleData(entityLiving).setTargetScale(viewBobbing - 0.1f);
        }
        if (defense > 0.2) {
            ScaleTypes.DEFENSE.getScaleData(entityLiving).setTargetScale(viewBobbing - 0.1f);
        }
    }
}
