package com.davigj.foolish_asteroids.common.effect;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public class AloftEffect extends MobEffect {
    public AloftEffect() {
        super(MobEffectCategory.BENEFICIAL, 16763749);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            Abilities abilities = player.getAbilities();
            abilities.mayfly = false;
            String playerCurrentMorph = MorphUtilHelper.playerCurrentMorph(player);
            if (!ALLOW_FLYING_MORPHS.contains(playerCurrentMorph)) {
                abilities.flying = false;
            }
            player.onUpdateAbilities();
        }
    }

    private static final Set<String> ALLOW_FLYING_MORPHS = new HashSet<>();
    static {
        ALLOW_FLYING_MORPHS.add("entity.minecraft.blaze");
        ALLOW_FLYING_MORPHS.add("entity.minecraft.bee");
        ALLOW_FLYING_MORPHS.add("entity.naturalist.butterfly");
        ALLOW_FLYING_MORPHS.add("entity.minecraft.ghast");
        ALLOW_FLYING_MORPHS.add("entity.minecraft.wither");
        ALLOW_FLYING_MORPHS.add("entity.alexsmobs.sunbird");
    }
}
