package com.davigj.foolish_asteroids.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PiglinPenItem extends Item {
    public PiglinPenItem(Properties p_41383_) {
        super(p_41383_);
    }
    public int getBarColor(ItemStack stack) {
        return 3093034;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
