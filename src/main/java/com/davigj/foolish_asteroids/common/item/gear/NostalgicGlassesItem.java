package com.davigj.foolish_asteroids.common.item.gear;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class NostalgicGlassesItem extends ArmorItem {

    public NostalgicGlassesItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.tickCount % 20 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, false));
        }
    }
}
