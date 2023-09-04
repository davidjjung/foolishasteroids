package com.davigj.foolish_asteroids.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SeveredTongueItem extends Item {
    public SeveredTongueItem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            this.affectConsumer(consumer);
        }
        return this.isEdible() ? consumer.eat(level, stack) : stack;
    }

    public void affectConsumer(LivingEntity livingEntity) {
        Level world = livingEntity.getLevel();

        for (int i = 0; i < 16; i++) {
            double d3 = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5D) * 3.0D;
            double d4 = Mth.clamp(livingEntity.getY() + (double) (livingEntity.getRandom().nextInt(16) - 8), (double) world.getMinBuildHeight(), (double) (world.getMinBuildHeight() + ((ServerLevel) world).getLogicalHeight() - 1));
            double d5 = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5D) * 3.0D;
            if (livingEntity instanceof Player) {
                if (livingEntity.randomTeleport(d3, d4, d5, true)) {
                    SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                    world.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    livingEntity.playSound(soundevent, 1.0F, 1.0F);
                    break; // Exit the loop if teleportation is successful
                }
            }
        }
    }
}
