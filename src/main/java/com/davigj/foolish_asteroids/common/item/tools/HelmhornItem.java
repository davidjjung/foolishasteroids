package com.davigj.foolish_asteroids.common.item.tools;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Random;

public class HelmhornItem extends Item {
    public HelmhornItem(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);
        ItemStack helm = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helm.isEmpty() && EnchantmentHelper.hasBindingCurse(helm)) {
            if (level.isClientSide) {
                Random random = new Random();
                for (int i = 0; i < 3; i++) {
                    level.addParticle(ParticleTypes.SMOKE, player.getX() + random.nextDouble() - 0.5,
                            player.getEyeY() + random.nextDouble(), player.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                }
            }
            return InteractionResultHolder.fail(stack);
        }
        if (hand == InteractionHand.MAIN_HAND && !offStack.isEmpty()) {
            if (!level.isClientSide) {
                if (!player.getInventory().add(helm)) {
                    player.drop(helm, false);
                }
                ItemStack helmToBe = offStack.copy();
                helmToBe.setCount(1);
                player.getInventory().armor.set(3, helmToBe);
                offStack.shrink(1);
            } else {
                Random random = new Random();
                for (int i = 0; i < 4; i++) {
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, player.getX() + random.nextDouble() - 0.5,
                            player.getEyeY() + random.nextDouble(), player.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                }
                level.playSound(player, player.getOnPos(), SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 0.6F, 2.5F);
            }
        }
        return InteractionResultHolder.pass(stack);
    }
}
