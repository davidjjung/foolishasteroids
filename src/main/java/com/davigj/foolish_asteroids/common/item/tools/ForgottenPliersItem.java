package com.davigj.foolish_asteroids.common.item.tools;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.davigj.foolish_asteroids.core.util.FoolishAsteroidsDamageSources;
import com.github.alexthe666.alexsmobs.entity.EntityBoneSerpent;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.upgrade_aquatic.common.entity.monster.Thrasher;
import com.teamabnormals.upgrade_aquatic.core.registry.UAItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Random;

import static com.davigj.foolish_asteroids.common.util.Constants.PLIERS_CHARGE_TICKS;

public class ForgottenPliersItem extends Item {
    private static final String NBT_CHARGE = "Charge";
    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    public ForgottenPliersItem(Properties p_41383_) {
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
            if (tag.getInt(NBT_CHARGE) == PLIERS_CHARGE_TICKS) {
                // TODO: Pliers charge up sound
                level.playSound((Player) entity, entity, SoundEvents.BONE_MEAL_USE, SoundSource.NEUTRAL, 1.0F, 3.0F);
                Random random = new Random();
                Vec3 lookVector = entity.getLookAngle();
                Vec3 lookRay = lookVector.scale(0.3);
                for (int i = 0; i < 3; i++) {
                    level.addParticle(ParticleTypes.GLOW, entity.getX() + lookRay.x + (0.5 * (random.nextDouble() - 0.5)),
                            (entity.getEyeHeight() * 0.65) + entity.getY() + random.nextDouble(),
                            entity.getZ() + lookRay.z + (0.5 * (random.nextDouble() - 0.5)), 0, 0, 0);
                }
            }
        }
        if (charge >= PLIERS_CHARGE_TICKS && level.isClientSide) {
            float reachDistance = 2.0F * ScaleTypes.REACH.getScaleData(entity).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(entity).getBaseScale(); // Adjust the reach distance as needed
            Vec3 lookVector = entity.getLookAngle();
            List<Entity> entities = level.getEntities((Entity) null, entity.getBoundingBox().expandTowards(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
            entities.remove(entity);
            entities.removeIf(targetEntity -> !(targetEntity instanceof LivingEntity));
            entities.removeIf(targetEntity -> !(targetEntity instanceof Player || targetEntity instanceof Thrasher
                    || targetEntity instanceof EntityBoneSerpent));
            for (Entity targetEntity : entities) {
                if (targetEntity instanceof LivingEntity && targetEntity != entity && targetEntity.tickCount % 14 == 0
                && manager.getValue(targetEntity, FoolishAsteroidsMod.TEETH) > 0) {
                    Random random = new Random();
                    for (int i = 0; i < 1; i++) {
                        level.addParticle(ParticleTypes.SNEEZE, targetEntity.getX() + random.nextDouble() - 0.5,
                                targetEntity.getEyeY() + random.nextDouble(), targetEntity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                    }
                    break;
                }
            }
            if (entities.isEmpty() && manager.getValue(entity, FoolishAsteroidsMod.TEETH) > 0 && entity.tickCount % 14 == 0) {
                Random random = new Random();
                for (int i = 0; i < 1; i++) {
                    level.addParticle(ParticleTypes.SNEEZE, entity.getX() + random.nextDouble() - 0.5,
                            entity.getEyeY() + random.nextDouble(), entity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerIn, InteractionHand handIn) {
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        CompoundTag tag = stack.getOrCreateTag();
        int charge = tag.getInt(NBT_CHARGE);
        if (charge >= PLIERS_CHARGE_TICKS) {
            float reachDistance = 2.0F * ScaleTypes.REACH.getScaleData(entity).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(entity).getBaseScale(); // Adjust the reach distance as needed
            Vec3 lookVector = entity.getLookAngle();

            // Find the entity in range
            List<Entity> entities = level.getEntities((Entity) null, entity.getBoundingBox().expandTowards(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
            entities.remove(entity);
            entities.removeIf(targetEntity -> !(targetEntity instanceof LivingEntity));
            entities.removeIf(targetEntity -> !(targetEntity instanceof Player || targetEntity instanceof Thrasher
                    ||targetEntity instanceof EntityBoneSerpent));
            if (!level.isClientSide) {
                for (Entity targetEntity : entities) {
                    if (targetEntity instanceof LivingEntity && targetEntity != entity) {
                        if (manager.getValue(targetEntity, FoolishAsteroidsMod.TEETH) > 0) {
                            defang(targetEntity, level, stack, (Player)entity);
                        }
                        break;
                    }
                }
                if (entities.isEmpty() && manager.getValue(entity, FoolishAsteroidsMod.TEETH) > 0) {
                    defang(entity, level, stack, (Player)entity);
                }
            } else {
                // TODO: *CROMCH*
                level.playSound((Player) entity, entity, AMSoundRegistry.GIANT_SQUID_TENTACLE, SoundSource.NEUTRAL, 1.25F, 2.0F);
                for (Entity targetEntity : entities) {
                    if (targetEntity instanceof LivingEntity && targetEntity != entity) {
                        if (manager.getValue(targetEntity, FoolishAsteroidsMod.TEETH) > 0) {
                            Random random = new Random();
                            for (int i = 0; i < 4; i++) {
                                level.addParticle(ParticleTypes.SNEEZE, targetEntity.getX() + random.nextDouble() - 0.5,
                                        targetEntity.getEyeY() + random.nextDouble(), targetEntity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                            }
                        }
                        break;
                    }
                }
                if (entities.isEmpty() && manager.getValue(entity, FoolishAsteroidsMod.TEETH) > 0) {
                    Random random = new Random();
                    for (int i = 0; i < 1; i++) {
                        level.addParticle(ParticleTypes.SNEEZE, entity.getX() + random.nextDouble() - 0.5,
                                entity.getEyeY() + random.nextDouble(), entity.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
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

    public static void defang (Entity targetEntity, Level level, ItemStack stack, Player player) {
        Vec3 itemVector = targetEntity.getLookAngle();
        Vec3 itemVelocity = itemVector.scale(0.2);
        ItemEntity itemEntity = new ItemEntity(level, targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), new ItemStack(FoolishAsteroidsItems.TOOTH.get()));
        if (targetEntity instanceof EntityBoneSerpent) {
            itemEntity = new ItemEntity(level, targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), new ItemStack(AMItemRegistry.BONE_SERPENT_TOOTH.get()));
        } else if (targetEntity instanceof Thrasher) {
            itemEntity = new ItemEntity(level, targetEntity.getX(), targetEntity.getY(), targetEntity.getZ(), new ItemStack(UAItems.THRASHER_TOOTH.get()));
        }
        if (targetEntity instanceof Player) {
            manager.setValue(targetEntity, FoolishAsteroidsMod.TEETH, manager.getValue(targetEntity, FoolishAsteroidsMod.TEETH) - 1);
        }
        itemEntity.setDefaultPickUpDelay();
        double initialVelocityY = 0.11; // Adjust as needed
        itemEntity.setDeltaMovement(itemVelocity.x, initialVelocityY, itemVelocity.z);
        boolean hasSilkTouch = false;
        CompoundTag itemNBT = stack.getTag();

        if (itemNBT != null && itemNBT.contains("Enchantments", 9)) {
            ListTag enchantments = itemNBT.getList("Enchantments", 10);
            for (int i = 0; i < enchantments.size(); i++) {
                CompoundTag enchantmentTag = enchantments.getCompound(i);
                String enchantmentId = enchantmentTag.getString("id");

                if (enchantmentId.equals("minecraft:silk_touch")) {
                    hasSilkTouch = true;
                    break; // You can break out of the loop since Silk Touch is found
                }
            }
        }
        if (!hasSilkTouch) {
            targetEntity.hurt(FoolishAsteroidsDamageSources.TOOTH_PULL, 3.0F);
            if ((targetEntity instanceof Thrasher thrasher)) {
                thrasher.setLastHurtByPlayer(player);
            }
        }
        level.addFreshEntity(itemEntity);
        ((LivingEntity) targetEntity).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        // Check if the enchantment is Silk Touch, and allow it
        if (enchantment == Enchantments.SILK_TOUCH) {
            return true;
        }
        // If it's not Silk Touch, let the parent class handle it
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}
