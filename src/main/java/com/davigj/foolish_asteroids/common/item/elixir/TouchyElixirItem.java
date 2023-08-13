package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

public class TouchyElixirItem extends ElixirItem {

    public TouchyElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        float reach = ScaleTypes.REACH.getScaleData(entityLiving).getBaseScale();
        float defense = ScaleTypes.DEFENSE.getScaleData(entityLiving).getBaseScale();
        float hitboxHeight = ScaleTypes.HITBOX_HEIGHT.getScaleData(entityLiving).getBaseScale();
        float hitboxWidth = ScaleTypes.HITBOX_WIDTH.getScaleData(entityLiving).getBaseScale();
        float explosion = ScaleTypes.EXPLOSIONS.getScaleData(entityLiving).getBaseScale();

        if (defense > 0.2f) {
            ScaleTypes.DEFENSE.getScaleData(entityLiving).setTargetScale(reach - 0.1f);
        }
        if (reach < 6.0f) {
            ScaleTypes.REACH.getScaleData(entityLiving).setTargetScale(reach + 0.3f);
        }
        if (hitboxWidth < 5.0f) {
            ScaleTypes.HITBOX_WIDTH.getScaleData(entityLiving).setTargetScale(hitboxWidth + 0.25f);
        }
        if (hitboxHeight < 5.0f) {
            ScaleTypes.HITBOX_HEIGHT.getScaleData(entityLiving).setTargetScale(hitboxHeight + 0.25f);
        }
        if (explosion < 5.0f) {
            ScaleTypes.EXPLOSIONS.getScaleData(entityLiving).setTargetScale(explosion + 0.25f);
        }
    }
}
