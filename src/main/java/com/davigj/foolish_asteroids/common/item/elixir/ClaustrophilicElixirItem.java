package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class ClaustrophilicElixirItem extends ElixirItem {

    public ClaustrophilicElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float reach = ScaleTypes.REACH.getScaleData(entityLiving).getBaseScale();
        float miningSpeed = ScaleTypes.MINING_SPEED.getScaleData(entityLiving).getBaseScale();
        float jumpHeight = ScaleTypes.JUMP_HEIGHT.getScaleData(entityLiving).getBaseScale();
        float health = ScaleTypes.HEALTH.getScaleData(entityLiving).getBaseScale();

        if (reach > 0.2f) {
            ScaleTypes.REACH.getScaleData(entityLiving).setTargetScale(reach - 0.1f);
        }
        if (miningSpeed > 0.2f) {
            ScaleTypes.MINING_SPEED.getScaleData(entityLiving).setTargetScale(miningSpeed - 0.1f);
        }
        if (jumpHeight > 0.2f) {
            ScaleTypes.JUMP_HEIGHT.getScaleData(entityLiving).setTargetScale(jumpHeight - 0.1f);
        }
        if (health < 5.0f) {
            ScaleTypes.HEALTH.getScaleData(entityLiving).setTargetScale(health + 1.0f);
        }
    }
}
