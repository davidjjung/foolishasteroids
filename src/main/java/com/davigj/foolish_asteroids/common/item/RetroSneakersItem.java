package com.davigj.foolish_asteroids.common.item;

import com.teamabnormals.environmental.common.item.YakPantsItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.mixin.client.compat1182plus.ClientPlayerEntityMixin;

public class RetroSneakersItem extends ArmorItem {
    public RetroSneakersItem(ArmorMaterial material, EquipmentSlot slot, Properties builder) {
        super(material, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!player.isCrouching() && player.level.isClientSide) {
            if (((LocalPlayer) player).input.forwardImpulse < 0) {
                Vec3 motion = player.getDeltaMovement();
                double mPlus = 1.0;
                double gravity = 1.25;
                player.setDeltaMovement(motion.x * mPlus, motion.y * (motion.y > 0 ? gravity : 2 - gravity), motion.z * mPlus);
            }
        }
    }


    private boolean isMovingBackward(Player player) {
        Vec3 motion = player.getDeltaMovement().normalize();
        Vec3 lookVector = player.getLookAngle().normalize();
        double dotProduct = motion.dot(lookVector);
        if (player.tickCount % 10 == 0 && player.level.isClientSide) {
            System.out.println(((LocalPlayer)player).input.forwardImpulse);
        }
        double backwardThreshold = -0.1;
        return dotProduct < backwardThreshold;
    }

}

