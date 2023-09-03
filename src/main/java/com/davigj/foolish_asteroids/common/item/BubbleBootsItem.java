package com.davigj.foolish_asteroids.common.item;

import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BubbleBootsItem extends ArmorItem {
    public BubbleBootsItem (ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builder) {
        super(materialIn, slot, builder);
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        int currentDamage = this.getDamage(stack);
        BlockPos bubblePos = player.blockPosition().below();
        if (world.getBlockState(bubblePos).isAir() && player.tickCount % 5 == 0 && currentDamage < 250) {
            world.setBlockAndUpdate(bubblePos, ModRegistry.BUBBLE_BLOCK.get().defaultBlockState());
            stack.setDamageValue(currentDamage + 1);
        } else if (world.getBlockState(bubblePos).is(ModRegistry.SOAP_BLOCK.get())) {
            stack.setDamageValue(0);
        }
    }

    public int getMaxDamage(ItemStack stack) {
        return 250;
    }

    private int getRemainingBubbles(ItemStack stack) {
        return stack.getMaxDamage() - stack.getDamageValue();
    }
    public boolean isBarVisible(ItemStack stack) {
        return this.getRemainingBubbles(stack) > 0;
    }

    public int getBarColor(ItemStack stack) {
        return 15246564;
    }
}
