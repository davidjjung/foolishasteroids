package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.entity.EntityHummingbird;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.logging.Logger;

public class BucolicElixirItem extends Item {
    private static final Logger LOGGER = Logger.getLogger(BucolicElixirItem.class.getName());

    public BucolicElixirItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player &&!level.isClientSide) {
            if (entity instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
            BlockPos playerPos = player.blockPosition();

            // Replicate the functionality on six random blocks around the player
            for (int i = 0; i < 6; i++) {
                BlockPos targetPos = getRandomTargetPos(level, player);

                if (targetPos != null) {
                    BlockState targetBlockState = level.getBlockState(targetPos);
                    Block targetBlock = targetBlockState.getBlock();

                    // Check if the top side of the block is air
                    if (level.isEmptyBlock(targetPos.above())) {
                        // Perform the bone meal effect on the block
                        if (targetBlock instanceof BonemealableBlock bonemealableBlock) {
                            if (bonemealableBlock.isValidBonemealTarget(level, targetPos, targetBlockState, level.isClientSide)) {
                                if (level instanceof ServerLevel) {
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 0));
                                    if (bonemealableBlock.isBonemealSuccess((ServerLevel) level, level.random, targetPos, targetBlockState)) {
                                        bonemealableBlock.performBonemeal((ServerLevel) level, level.random, targetPos, targetBlockState);
                                        if (level.random.nextFloat() < 0.1f) {
                                            BlockPos babyBeePos = targetPos.above();
                                            Bee babyBee = EntityType.BEE.create(level);
                                            if (babyBee != null) {
                                                babyBee.setBaby(true);
                                                babyBee.moveTo(babyBeePos, 0, 0); // Move to the correct position
                                                babyBee.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(babyBee.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                                                level.addFreshEntity(babyBee);
                                            }
                                        }
                                        if (level.random.nextFloat() < 0.05f) {
                                            BlockPos hummingbirdPos = targetPos.above();
                                            EntityHummingbird hummingbird = AMEntityRegistry.HUMMINGBIRD.get().create(level);
                                            if (hummingbird != null) {
                                                hummingbird.moveTo(hummingbirdPos, 0, 0); // Move to the correct position
                                                hummingbird.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(hummingbird.blockPosition()), MobSpawnType.TRIGGERED, null, null);
                                                level.addFreshEntity(hummingbird);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Trigger particle effect
                        level.levelEvent(2005, targetPos, 0);
                    }
                }
            }

            if (stack.isEmpty()) {
                return new ItemStack(FoolishAsteroidsItems.FLASK.get());
            } else {
                if (!((Player) entity).getAbilities().instabuild) {
                    stack.shrink(1);
                    ItemStack itemstack = new ItemStack(FoolishAsteroidsItems.FLASK.get());
                    Player playerEntity = (Player) entity;
                    if (!playerEntity.getInventory().add(itemstack)) {
                        playerEntity.drop(itemstack, false);
                    }
                }
                return stack;
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }

    // Helper method to get a random target position around the player
    private BlockPos getRandomTargetPos(Level level, Player player) {
        Random random = level.random;
        BlockPos playerPos = player.blockPosition();

        for (int i = 0; i < 10; i++) {
            int offsetX = random.nextInt(5) - 2;
            int offsetY = random.nextInt(4) - 2;
            int offsetZ = random.nextInt(5) - 2;

            BlockPos targetPos = playerPos.offset(offsetX, offsetY, offsetZ);

            // Ensure the target position is within the loaded chunks and is not the player's position
            if (level.hasChunkAt(targetPos) && !targetPos.equals(playerPos)) {
                // Check if the top side of the block is air
                BlockPos topBlockPos = targetPos.above();
                BlockState topBlockState = level.getBlockState(topBlockPos);

                if (topBlockState.isAir()) {
                    return targetPos;
                }
            }
        }

        return null;
    }
    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }
    public int getUseDuration(ItemStack p_43001_) {
        return ElixirConstants.DRINK_TIME;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
