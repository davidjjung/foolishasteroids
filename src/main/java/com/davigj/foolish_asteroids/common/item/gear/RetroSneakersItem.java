package com.davigj.foolish_asteroids.common.item.gear;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RetroSneakersItem extends ArmorItem {
    private static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(
            "retro_boost",
            0.2, // This adds 10% speed
            AttributeModifier.Operation.ADDITION
    );
    public RetroSneakersItem(ArmorMaterial material, EquipmentSlot slot, Properties builder) {
        super(material, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.level.isClientSide) {
            LocalPlayer local = (LocalPlayer) player;
            AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (local.input.forwardImpulse < 0 && speedAttribute != null) {
                if (!speedAttribute.hasModifier(SPEED_BOOST_MODIFIER)) {
                    speedAttribute.addTransientModifier(SPEED_BOOST_MODIFIER);
                    player.maxUpStep = Math.max(player.maxUpStep, 2.0F);
                }
                if (!player.isOnGround()) {
                    Vec3 motion = player.getDeltaMovement();
                    double newHorizontalSpeed = 1.08;
                    player.setDeltaMovement(new Vec3(
                            motion.x * newHorizontalSpeed,
                            motion.y,
                            motion.z * newHorizontalSpeed
                    ));
                }
            } else {
                if (speedAttribute != null && speedAttribute.hasModifier(SPEED_BOOST_MODIFIER)) {
                    speedAttribute.removeModifier(SPEED_BOOST_MODIFIER);
                    player.maxUpStep = 0.6F;
                }
            }
        }
    }
}

