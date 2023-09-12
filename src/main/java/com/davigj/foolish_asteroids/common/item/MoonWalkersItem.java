package com.davigj.foolish_asteroids.common.item;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MoonWalkersItem extends ArmorItem {
    public MoonWalkersItem(ArmorMaterial material, EquipmentSlot slot, Properties builder) {
        super(material, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!player.isCrouching() && player.level.isClientSide && !player.isOnGround()) {
            player.setNoGravity(((LocalPlayer) player).input.forwardImpulse < 0);
        }
    }
}