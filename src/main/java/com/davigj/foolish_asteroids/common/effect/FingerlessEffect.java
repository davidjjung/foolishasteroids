package com.davigj.foolish_asteroids.common.effect;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;

import java.util.HashSet;
import java.util.Set;

public class FingerlessEffect extends MobEffect {
    public FingerlessEffect() {
        super(MobEffectCategory.HARMFUL, 2332063);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            // Check if the player is trying to place a block
            Abilities abilities = player.getAbilities();
            abilities.mayBuild = false;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            Abilities abilities = player.getAbilities();
            abilities.mayBuild = true;
            player.onUpdateAbilities();
        }
    }

}
