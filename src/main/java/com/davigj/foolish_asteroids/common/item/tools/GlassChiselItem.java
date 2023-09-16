package com.davigj.foolish_asteroids.common.item.tools;

import com.davigj.foolish_asteroids.core.other.tags.FABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class GlassChiselItem extends Item {
    public GlassChiselItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        ItemStack stack = context.getItemInHand();
        assert player != null;
        if (world.isClientSide()) {
            if (state.m_204336_(FABlockTags.GLASS_BLOCKS)) {
                Random random = new Random();
                for (int i = 0; i < 4; i++) {
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + random.nextDouble() - 0.5,
                            pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble() - 0.5, 0, 0, 0);
                }
                world.playSound(player, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 0.6F, 2.0F);
            }
            return InteractionResult.PASS;
        } else {
            if (state.m_204336_(FABlockTags.GLASS_BLOCKS)) {
                if (!world.isClientSide()) {
                    stack.hurtAndBreak(1, player, (entity) -> {
                        entity.broadcastBreakEvent(stack.isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND);
                    });
                    ItemStack glassItem = new ItemStack(state.getBlock());
                    world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), glassItem));
                    world.removeBlock(pos, true);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
