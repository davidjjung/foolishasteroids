package com.davigj.foolish_asteroids.common.item.tools;

import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Random;

import static com.davigj.foolish_asteroids.common.util.Constants.STICKY_HAND_CHARGE_TICKS;

public class StickyHandItem extends Item {
    private static final String NBT_CHARGE = "Charge";

    public StickyHandItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Adjust the duration as needed
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        CompoundTag tag = stack.getOrCreateTag();
        int charge = tag.getInt(NBT_CHARGE);
        if (!level.isClientSide) {
            tag.putInt(NBT_CHARGE, charge + 1);
        } else {
            if (tag.getInt(NBT_CHARGE) == STICKY_HAND_CHARGE_TICKS) {
                level.playSound((Player) entity, entity, AMSoundRegistry.GIANT_SQUID_HURT, SoundSource.NEUTRAL, 1.0F, 3.0F);
                Random random = new Random();
                Vec3 lookVector = entity.getLookAngle();
                Vec3 itemVelocity = lookVector.scale(0.3);
                for (int i = 0; i < 3; i++) {
                    level.addParticle(ParticleTypes.END_ROD, entity.getX() + itemVelocity.x + (0.5 * (random.nextDouble() - 0.5)),
                            (entity.getEyeHeight() * 0.65) + entity.getY() + random.nextDouble(),
                            entity.getZ() + itemVelocity.z + (0.5 * (random.nextDouble() - 0.5)), 0, 0, 0);
                }
            }
        }
        if (charge >= STICKY_HAND_CHARGE_TICKS && level.isClientSide) {
            float reachDistance = 2.0F * ScaleTypes.REACH.getScaleData(entity).getBaseScale()
                    * ScaleTypes.ENTITY_REACH.getScaleData(entity).getBaseScale();
            Vec3 lookVector = entity.getLookAngle();
            List<Entity> entities = level.getEntities((Entity) null, entity.getBoundingBox().expandTowards(
                    lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
            for (Entity targetEntity : entities) {
                if (targetEntity instanceof LivingEntity && targetEntity != entity && targetEntity.tickCount % 14 == 0) {
                    ItemStack mainHandItem = ((LivingEntity) targetEntity).getMainHandItem();
                    if (!mainHandItem.isEmpty()) {
                        Random random = new Random();
                        for (int i = 0; i < 1; i++) {
                            level.addParticle(ParticleTypes.END_ROD, targetEntity.getX() + random.nextDouble() - 0.5,
                                    targetEntity.getEyeY() + random.nextDouble(), targetEntity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                        }
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        CompoundTag tag = stack.getOrCreateTag();
        int charge = tag.getInt(NBT_CHARGE);
        if (charge >= STICKY_HAND_CHARGE_TICKS) {
            float reachDistance = 2.0F * ScaleTypes.REACH.getScaleData(entity).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(entity).getBaseScale(); // Adjust the reach distance as needed
            Vec3 lookVector = entity.getLookAngle();

            // Find the entity in range
            List<Entity> entities = level.getEntities((Entity) null, entity.getBoundingBox().expandTowards(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
            if (!level.isClientSide) {
                for (Entity targetEntity : entities) {
                    if (targetEntity instanceof LivingEntity && targetEntity != entity) {
                        // Check if the entity has a mainhand item
                        ItemStack mainHandItem = ((LivingEntity) targetEntity).getMainHandItem();
                        if (!mainHandItem.isEmpty()) {
                            // Drop the mainhand item as an item entity
                            Vec3 itemVector = targetEntity.getLookAngle();
                            Vec3 itemVelocity = itemVector.scale(0.2);
                            ItemEntity itemEntity = new ItemEntity(level, targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), mainHandItem);
                            itemEntity.setDefaultPickUpDelay();
                            itemEntity.setNoGravity(true);
                            double initialVelocityY = 0.11; // Adjust as needed
                            itemEntity.setDeltaMovement(itemVelocity.x, initialVelocityY, itemVelocity.z);
                            level.addFreshEntity(itemEntity);
                            ((LivingEntity) targetEntity).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                        }
                        break;
                    }
                }
            } else {
                level.playSound((Player) entity, entity, AMSoundRegistry.GIANT_SQUID_TENTACLE, SoundSource.NEUTRAL, 1.25F, 2.0F);
                level.playSound((Player) entity, entity, AMSoundRegistry.GIANT_SQUID_TENTACLE, SoundSource.NEUTRAL, .85F, 1.25F);
                for (Entity targetEntity : entities) {
                    if (targetEntity instanceof LivingEntity && targetEntity != entity) {
                        ItemStack mainHandItem = ((LivingEntity) targetEntity).getMainHandItem();
                        if (!mainHandItem.isEmpty()) {
                            Random random = new Random();
                            for (int i = 0; i < 4; i++) {
                                level.addParticle(ParticleTypes.END_ROD, targetEntity.getX() + random.nextDouble() - 0.5,
                                        targetEntity.getEyeY() + random.nextDouble(), targetEntity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        }
                        break;
                    }
                }
            }
            stack.hurtAndBreak(1, entity, (temp) -> {
                temp.broadcastBreakEvent(stack.isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
            });
        }

        // Reset the charge
        if (entity instanceof Player) {
            assert stack.getTag() != null;
            stack.getTag().remove(NBT_CHARGE);
        }
    }

    // /summon fox ~ ~1 ~ {HandItems:[{Count:1,id:diamond}], NoAI:true}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerIn, InteractionHand handIn) {
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }
}
