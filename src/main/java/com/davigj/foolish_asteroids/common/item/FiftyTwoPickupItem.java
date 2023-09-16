package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.PickupUtil;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class FiftyTwoPickupItem extends Item {
    public FiftyTwoPickupItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);
        Supplier<EntityType<?>> entityTypeSupplier = PickupUtil.getEntityTypeForItem(offStack.getItem().getRegistryName());
//        System.out.println(entityTypeSupplier);
        if (entityTypeSupplier != null) {
            EntityType<?> entityType = entityTypeSupplier.get();
            if (entityType != null) {
                shootEntityFromPlayerHead(world, player, entityType, offStack);
                offStack.shrink(1);
                return InteractionResultHolder.success(player.getItemInHand(hand));
            }
        }

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    private void shootEntityFromPlayerHead(Level level, Player player, EntityType<?> entityType, ItemStack stack) {
        // Create the entity instance
        Entity entity = entityType.create(level);
        if (entity instanceof Projectile projectile) {

            Vec3 playerPos = player.getEyePosition(1.0f);

            double motionX = level.random.nextGaussian();
            double motionY = level.random.nextGaussian();
            double motionZ = level.random.nextGaussian();
            double motionScale = 0.5; // Adjust this for speed

            projectile.setOwner(player);
            if (stack.hasTag()) {
                assert stack.getTag() != null;
                projectile.load(stack.getTag());
            }
            projectile.setDeltaMovement(motionX * motionScale, motionY * motionScale, motionZ * motionScale);
            projectile.setPos(playerPos.x, playerPos.y, playerPos.z);
            level.addFreshEntity(projectile);
        }
    }
}
