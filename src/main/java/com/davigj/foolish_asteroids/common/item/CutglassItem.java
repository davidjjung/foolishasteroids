package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.core.other.tags.FoolishAsteroidsBlockTags;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class CutglassItem extends SwordItem {
    public CutglassItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target != null) {
            // Apply poison effect to the target
            target.addEffect(new MobEffectInstance(AMEffectRegistry.EXSANGUINATION, 100)); // 100 ticks = 5 seconds
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 20.0F;
        } else if (state.m_204336_(FoolishAsteroidsBlockTags.GLASS_BLOCKS)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.m_204336_(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }


    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (state.m_204336_(FoolishAsteroidsBlockTags.GLASS_BLOCKS)) {
            if (!world.isClientSide()) {
                ItemStack glassItem = new ItemStack(state.getBlock());
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), glassItem));
            }
            return true; // Block was processed, don't call the superclass
        } else {
            return super.mineBlock(stack, world, state, pos, entity); // Call superclass for other blocks
        }
    }
}
