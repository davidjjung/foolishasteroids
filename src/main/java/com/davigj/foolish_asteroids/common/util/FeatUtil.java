package com.davigj.foolish_asteroids.common.util;

import com.davigj.foolish_asteroids.core.registry.FAItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FeatUtil {
    public static void medalMaterialize (Entity entity) {
        ItemEntity itemEntity = new ItemEntity(entity.level, entity.getX(), entity.getEyeY(), entity.getZ(),
                new ItemStack(FAItems.COMET_MEDAL.get()));
        itemEntity.setDefaultPickUpDelay();
        itemEntity.setNoGravity(true);
        double initialVelocityY = 0.11;
        itemEntity.setDeltaMovement(0, initialVelocityY, 0);
        entity.level.addFreshEntity(itemEntity);
    }

    public static int uuidToInt(Player player) {
        return player.getUUID().toString().hashCode();
    }
}
