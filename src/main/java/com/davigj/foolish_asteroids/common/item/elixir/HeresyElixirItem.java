package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Random;
import java.util.function.Function;

public class HeresyElixirItem extends ElixirItem {

    public HeresyElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        Random random = new Random();
        TrackedDataManager highway = TrackedDataManager.INSTANCE;
        int heresyLevel = highway.getValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL);
        ResourceKey<Level> dim = player.getLevel().dimension();
        if (dim == Level.OVERWORLD) {
            if (entityLiving instanceof ServerPlayer serverPlayer) {
                switch (heresyLevel) {
                    case 0 -> {
                        if (random.nextInt(5) == 1) {
                            TranslatableComponent message = new TranslatableComponent("message.heresy.snap");
                            ((Player) entityLiving).displayClientMessage(message, true);
                            player.setSecondsOnFire(10);
                            highway.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 1);
                        } else {
                            TranslatableComponent message = new TranslatableComponent("message.heresy.weaken");
                            ((Player) entityLiving).displayClientMessage(message, true);
                            player.setSecondsOnFire(5);
                        }
                    }
                    case 1 -> {
                        ServerLevel nether = serverPlayer.server.getLevel(Level.NETHER);
                        if (player.canChangeDimensions() && !player.isPassenger() && nether != null) {
                            double netherX = serverPlayer.getX() / 8.0;
                            double netherZ = serverPlayer.getZ() / 8.0;

                            // Find a safer position in the Nether
                            BlockPos netherPos = findSafeNetherPosition(nether, new BlockPos(netherX, 0, netherZ));
                            if (netherPos != null) {
                                // Create a portal info with the calculated Nether coordinates
                                PortalInfo portalInfo = new PortalInfo(Vec3.atCenterOf(netherPos), Vec3.ZERO, serverPlayer.getYRot(), serverPlayer.getXRot());

                                // Teleport the player to the Nether using changeDimension method
                                serverPlayer.changeDimension(nether, new ITeleporter() {
                                    @Override
                                    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                                        return repositionEntity.apply(true);
                                    }

                                    @Override
                                    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
                                        return portalInfo;
                                    }
                                });
                                TranslatableComponent message = new TranslatableComponent("message.heresy.snapping");
                                ((Player) entityLiving).displayClientMessage(message, true);
                            } else {
                                TranslatableComponent message = new TranslatableComponent("message.heresy.snap");
                                ((Player) entityLiving).displayClientMessage(message, true);
                                player.setSecondsOnFire(10);
                            }
                        }
                    }
                    case 2 -> {
                        TranslatableComponent message = new TranslatableComponent("message.heresy.snapped");
                        ((Player) entityLiving).displayClientMessage(message, true);
                        player.setSecondsOnFire(15);
                        player.hurt(DamageSource.IN_FIRE, 0.4F);
                    }
                }
            }
        } else if (dim == Level.NETHER) {
            TranslatableComponent message = new TranslatableComponent("message.heresy.snap");
            switch (heresyLevel) {
                case 0 -> {
                    message = new TranslatableComponent("message.heresy.snap");
                    highway.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 1);
                }
                case 1 -> {
                    message = new TranslatableComponent("message.heresy.snapping");
                    highway.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 2);
                }
                case 2 -> {
                    message = new TranslatableComponent("message.heresy.snapped");
                    player.setSecondsOnFire(15);
                    player.hurt(DamageSource.IN_FIRE, 0.4F);
                }
            }
            player.setSecondsOnFire(20);
            ((Player) entityLiving).displayClientMessage(message, true);
        } else {
            TranslatableComponent message = new TranslatableComponent("message.heresy.snap");
            switch (heresyLevel) {
                case 0:
                    if (random.nextInt(5) == 1) {
                        message = new TranslatableComponent("message.heresy.snap");
                        ((Player) entityLiving).displayClientMessage(message, true);
                        player.setSecondsOnFire(10);
                        highway.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 1);
                    } else {
                        message = new TranslatableComponent("message.heresy.weaken");
                        ((Player) entityLiving).displayClientMessage(message, true);
                        player.setSecondsOnFire(5);
                    }
                    break;
                case 1:
                    message = new TranslatableComponent("message.heresy.snap");
                    highway.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 2);
                    break;
                case 2:
                    message = new TranslatableComponent("message.heresy.snapped");
                    player.setSecondsOnFire(15);
                    player.hurt(DamageSource.IN_FIRE, 0.4F);
            }
            player.setSecondsOnFire(20);
            ((Player) entityLiving).displayClientMessage(message, true);
        }
    }

    private BlockPos findSafeNetherPosition(ServerLevel targetWorld, BlockPos destinationPos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        // Loop to find a safe position within the specified y-coordinate range
        for (int yOffset = 1; yOffset <= 127; yOffset++) {
            mutablePos.set(destinationPos.getX(), destinationPos.getY() + yOffset, destinationPos.getZ());

            if (isSafeNetherPosition(targetWorld, mutablePos)) {
                return mutablePos;
            }
        }

        return null;
    }

    private boolean isSafeNetherPosition(ServerLevel world, BlockPos pos) {
        // Check if the block and the block above are empty
        if (world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above())) {
            // Check if the block below is solid
            BlockPos downPos = pos.below();
            if (world.getBlockState(downPos).getMaterial().blocksMotion() && world.getBlockState(downPos).isCollisionShapeFullBlock(world, downPos)) {
                // Check if the position is within bounds
                return world.getWorldBorder().isWithinBounds(pos);
            }
        }
        return false;
    }


}
