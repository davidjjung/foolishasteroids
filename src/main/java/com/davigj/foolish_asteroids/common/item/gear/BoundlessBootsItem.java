package com.davigj.foolish_asteroids.common.item.gear;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BoundlessBootsItem extends ArmorItem {
    public BoundlessBootsItem(ArmorMaterial material, EquipmentSlot slot, Properties builder) {
        super(material, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.level.isClientSide) {
            if (!player.isCrouching() && !player.isOnGround()) {
                player.setNoGravity(true);
            } else {
                player.setNoGravity(false);
            }
        }
    }
}