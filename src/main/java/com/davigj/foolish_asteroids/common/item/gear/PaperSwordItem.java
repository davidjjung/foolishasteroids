package com.davigj.foolish_asteroids.common.item.gear;

import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class PaperSwordItem extends SwordItem {

    public PaperSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean hasSharpness = false;
        CompoundTag itemNBT = stack.getTag();
        if (itemNBT != null && itemNBT.contains("Enchantments", 9)) {
            ListTag enchantments = itemNBT.getList("Enchantments", 10);
            for (int i = 0; i < enchantments.size(); i++) {
                CompoundTag enchantmentTag = enchantments.getCompound(i);
                String enchantmentId = enchantmentTag.getString("id");

                if (enchantmentId.equals("minecraft:sharpness")) {
                    hasSharpness = true;
                    break; // You can break out of the loop since Silk Touch is found
                }
            }
        }
        if (target != null) {
            target.addEffect(new MobEffectInstance(AMEffectRegistry.EXSANGUINATION, 60, (hasSharpness ? 1 : 0)));
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 20.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.m_204336_(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }
}
