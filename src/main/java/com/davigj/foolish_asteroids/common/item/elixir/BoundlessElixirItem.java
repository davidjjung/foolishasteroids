package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class BoundlessElixirItem extends ElixirItem {

    public BoundlessElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float jumpHeight = ScaleTypes.JUMP_HEIGHT.getScaleData(entityLiving).getBaseScale();
        float motion = ScaleTypes.MOTION.getScaleData(entityLiving).getBaseScale();

        if (jumpHeight < 4.4) {
            ScaleTypes.JUMP_HEIGHT.getScaleData(entityLiving).setTargetScale(jumpHeight + 0.2f);
        }
        if (motion > 0.2) {
            ScaleTypes.MOTION.getScaleData(entityLiving).setTargetScale(motion - 0.1f);
        }
    }
}
