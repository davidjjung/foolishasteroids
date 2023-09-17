package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.PickupUtil;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Queue;
import java.util.function.Supplier;

public class ToothItem extends Item {
    public ToothItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        Queue<ItemStack> teeth = manager.getValue(player, FADataProcessors.TEETH_DATA);
        if (teeth.size() < 3) {
            ItemStack stack = player.getItemInHand(hand);
            ItemStack implant = stack.copy();
            implant.setCount(1);
            teeth.add(implant);
            stack.shrink(1);
            manager.setValue(player, FADataProcessors.TEETH_DATA, teeth);
            // TODO: CROOOOMCH
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
