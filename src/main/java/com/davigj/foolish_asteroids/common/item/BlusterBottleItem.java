package com.davigj.foolish_asteroids.common.item;

import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.level.Level;

public class BlusterBottleItem extends Item {
    public BlusterBottleItem(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.getCooldowns().addCooldown(this, 10);
        player.setOnGround(false);
        player.resetFallDistance();
        level.playSound(player, player, SoundEvents.GHAST_SHOOT, SoundSource.NEUTRAL, 0.4F, 3.0F);
        player.push(0, 1.425 - 0.5 + (0.075) * (1.0D - player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), 0);
        player.awardStat(Stats.ITEM_USED.get(this));
        boolean filler = true;
        if (!player.getAbilities().instabuild && !level.isClientSide) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                filler = false;
                player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                    player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                }
            }
        }

        return InteractionResultHolder.success(filler ? itemstack : new ItemStack(Items.GLASS_BOTTLE));
    }
}
