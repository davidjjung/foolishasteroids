package com.davigj.foolish_asteroids.common.item.tools;

import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsParticleTypes;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ScheduledTick;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.davigj.foolish_asteroids.common.util.Constants.CURSED_STAFF_CHARGE_TICKS;

public class CursedStaffItem extends Item {
    private static final String NBT_CHARGE = "Charge";
    public CursedStaffItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Adjust the duration as needed
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        CompoundTag tag = stack.getOrCreateTag();
        int charge = tag.getInt(NBT_CHARGE);
        if (!level.isClientSide) {
            tag.putInt(NBT_CHARGE, charge + 1);
        } else {
            if (tag.getInt(NBT_CHARGE) == CURSED_STAFF_CHARGE_TICKS) {
                // Rip roarin' ready to go
                level.playSound((Player) entity, entity, AMSoundRegistry.APRIL_FOOLS_POWER_OUTAGE, SoundSource.NEUTRAL, 0.6F, 1.0F);
            }
        }
        if (charge >= CURSED_STAFF_CHARGE_TICKS / 5 && level.isClientSide) {
            Random random = new Random();
            if (random.nextBoolean()) {
                for (int i = 0; i < 1; i++) {
                    level.addParticle(FoolishAsteroidsParticleTypes.CURSE.get(), entity.getX() + (0.5 * (random.nextDouble() - 0.5)),
                            (entity.getEyeHeight() * 0.65) + entity.getY() + random.nextDouble(),
                            entity.getZ() + (0.5 * (random.nextDouble() - 0.5)), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        CompoundTag tag = stack.getOrCreateTag();
        int charge = tag.getInt(NBT_CHARGE);
        if (charge >= CURSED_STAFF_CHARGE_TICKS && isItemEnchantable(entity.getItemInHand(InteractionHand.OFF_HAND))) {
            if (!level.isClientSide && entity instanceof Player player) {
                ItemStack offhandItem = entity.getItemInHand(InteractionHand.OFF_HAND);
                if (offhandItem.getCount() > 1) {
                    ItemStack newItemStack = offhandItem.copy();
                    newItemStack.setCount(1);
                    offhandItem.shrink(1);
                    if (!player.getInventory().add(newItemStack)) {
                        player.drop(newItemStack, false);
                    }
                    applyRandomCurse(newItemStack);
                } else {
                    applyRandomCurse(offhandItem);
                    if (offhandItem.isDamageableItem()) {
                        offhandItem.setDamageValue(0);
                    }
                }
            } else {
                // TODO: fancy particle and sound wablooshkie
            }
//            stack.hurtAndBreak(1, entity, (temp) -> {
//                temp.broadcastBreakEvent(stack.isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
//            });
        }

        // Reset the charge
        if (entity instanceof Player) {
            assert stack.getTag() != null;
            stack.getTag().remove(NBT_CHARGE);
        }
    }

    private void applyRandomCurse (ItemStack stack) {
        // Create a list of possible curse enchantments
        List<Enchantment> curseEnchantments = new ArrayList<>();
        curseEnchantments.add(Enchantments.VANISHING_CURSE);
        curseEnchantments.add(Enchantments.BINDING_CURSE);

        List<Enchantment> enchantmentsToRemove = new ArrayList<>();
        for (Enchantment enchantment : curseEnchantments) {
            if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) > 0) {
                enchantmentsToRemove.add(enchantment);
            }
        }
        // Remove the enchantments that are already present
        for (Enchantment enchantment : enchantmentsToRemove) {
            curseEnchantments.remove(enchantment);
        }

        if (!curseEnchantments.isEmpty()) {
            Random random = new Random();
            Enchantment selectedEnchantment = curseEnchantments.get(random.nextInt(curseEnchantments.size()));
            Map<Enchantment, Integer> existingEnchantments = EnchantmentHelper.getEnchantments(stack);
            existingEnchantments.put(selectedEnchantment, 1);
            EnchantmentHelper.setEnchantments(existingEnchantments, stack);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerIn, InteractionHand handIn) {
        if (isItemEnchantable(playerIn.getItemInHand(InteractionHand.OFF_HAND))) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
        }
        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

    public boolean isItemEnchantable (ItemStack stack) {
        return stack.getItemEnchantability() != 0;
    }
}
