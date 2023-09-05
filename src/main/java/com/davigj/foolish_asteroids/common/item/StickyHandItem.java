package com.davigj.foolish_asteroids.common.item;

import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import quek.undergarden.item.tool.slingshot.SlingshotItem;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Random;

public class StickyHandItem extends Item {
    private static final String NBT_CHARGE = "Charge";
    private static final int MAX_CHARGE = 20; // Adjust as needed

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
        if (!level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            int charge = tag.getInt(NBT_CHARGE) + 1;
            tag.putInt(NBT_CHARGE, charge);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        CompoundTag tag = stack.getOrCreateTag();
        int charge = tag.getInt(NBT_CHARGE);
        if (charge >= MAX_CHARGE) {
            float reachDistance = 2.0F * ScaleTypes.REACH.getScaleData(entity).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(entity).getBaseScale(); // Adjust the reach distance as needed
            Vec3 lookVector = entity.getLookAngle();
            Vec3 startVec = entity.getEyePosition(1.0F);

            // Find the entity in range
            List<Entity> entities = level.getEntities((Entity) null, entity.getBoundingBox().expandTowards(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
            Entity itemEntity = null;
            if (!level.isClientSide) {
                for (Entity targetEntity : entities) {
                    if (targetEntity instanceof LivingEntity && targetEntity != entity) {
                        // Check if the entity has a mainhand item
                        ItemStack mainHandItem = ((LivingEntity) targetEntity).getMainHandItem();
                        if (!mainHandItem.isEmpty()) {
                            // Drop the mainhand item as an item entity
                            assert mainHandItem.getEntityRepresentation() != null;
                            itemEntity = targetEntity.spawnAtLocation(mainHandItem);
                            ((LivingEntity) targetEntity).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                        }
                        break;
                    }
                }
            } else {
                for (Entity targetEntity : entities) {
                    if (targetEntity instanceof LivingEntity && targetEntity != entity) {
                        ItemStack mainHandItem = ((LivingEntity) targetEntity).getMainHandItem();
                        if (!mainHandItem.isEmpty()) {
                            level.playSound((Player) entity, entity, AMSoundRegistry.GIANT_SQUID_TENTACLE, SoundSource.NEUTRAL, 1.0F, 2.0F);
                            Random random = new Random();
                            for (int i = 0; i < 4; i++) {
                                level.addParticle(ParticleTypes.END_ROD, targetEntity.getX() + random.nextDouble() - 0.5,
                                        targetEntity.getY() + random.nextDouble(), targetEntity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        }
                        break;
                    }
                }
            }
            if (itemEntity != null) {
                itemEntity.setOnGround(false);
                itemEntity.setNoGravity(true);
                double initialVelocityY = 0.12; // Adjust as needed
                itemEntity.setDeltaMovement(0, initialVelocityY, 0);
            }
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
