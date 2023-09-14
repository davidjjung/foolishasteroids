package com.davigj.foolish_asteroids.core.other.events;

import com.davigj.foolish_asteroids.common.item.PetrificationMaskItem;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.starfish_studios.naturalist.entity.Snake;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.galosphere.init.GBlocks;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

import static com.davigj.foolish_asteroids.common.util.Constants.MAX_SNAKES;
import static com.davigj.foolish_asteroids.common.util.Constants.MAX_SOAPINESS;


@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class PlayerActionEvents {

    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent
    public static void playerUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        if (player.getItemInHand(event.getHand()).getItem() == ModRegistry.SOAP.get()
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() == FoolishAsteroidsItems.BUBBLE_BOOTS.get()) {
            ItemStack armorStack = player.getItemBySlot(EquipmentSlot.FEET);
            armorStack.getOrCreateTag().putInt("Soapiness", MAX_SOAPINESS);
            ItemStack handStack = player.getItemInHand(event.getHand());
            handStack.shrink(1);
        }
    }

    @SubscribeEvent
    public static void playerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        if (player.getItemInHand(event.getHand()).getItem() == Items.GLASS_BOTTLE && event.getTarget() instanceof Ghast ghast
                && manager.getValue(ghast, FoolishAsteroidsMod.BLUSTER_RECHARGE) == 0 && event.getPlayer() != null && !player.getLevel().isClientSide()) {
            player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.8F, 0.6F);
            InteractionHand hand = event.getHand();
            manager.setValue(ghast, FoolishAsteroidsMod.BLUSTER_RECHARGE, 10);
            if (hand == InteractionHand.MAIN_HAND) {
                manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, 4);
            } else {
                manager.setValue(player, FoolishAsteroidsMod.BLUSTER_HARVEST, 2);
            }
        }
        if (player.getItemInHand(event.getHand()).m_204117_(Tags.Items.SHEARS) && event.getTarget() instanceof EnderMan enderman) {
            float playerYaw = player.getYRot();
            float playerPitch = player.getXRot();

            // Calculate the player's viewing direction vector and normalize it
            double playerViewX = -Math.sin(Math.toRadians(playerYaw)) * Math.cos(Math.toRadians(playerPitch));
            double playerViewY = -Math.sin(Math.toRadians(playerPitch));
            double playerViewZ = Math.cos(Math.toRadians(playerYaw)) * Math.cos(Math.toRadians(playerPitch));

            double playerViewLength = Math.sqrt(playerViewX * playerViewX + playerViewY * playerViewY + playerViewZ * playerViewZ);
            playerViewX /= playerViewLength;
            playerViewY /= playerViewLength;
            playerViewZ /= playerViewLength;

            // Calculate the vector from the player's position to the Enderman's head and normalize it
            double headX = enderman.getX();
            double headY = enderman.getEyeY(); // Adjust this to the exact height of the Enderman's head
            double headZ = enderman.getZ();
            double toHeadX = headX - player.getX();
            double toHeadY = headY - (player.getY() + player.getEyeHeight()); // Adjust this to the player's eye height
            double toHeadZ = headZ - player.getZ();

            double toHeadLength = Math.sqrt(toHeadX * toHeadX + toHeadY * toHeadY + toHeadZ * toHeadZ);
            toHeadX /= toHeadLength;
            toHeadY /= toHeadLength;
            toHeadZ /= toHeadLength;

            // Calculate the dot product of the two normalized vectors
            double dotProduct = toHeadX * playerViewX + toHeadY * playerViewY + toHeadZ * playerViewZ;

            if (dotProduct > 0.993) {
                int tongues = manager.getValue(enderman, FoolishAsteroidsMod.TONGUES);
                if (tongues > 0) {
                    player.getCooldowns().addCooldown(Items.SHEARS, 3 * 20);
                    manager.setValue(enderman, FoolishAsteroidsMod.TONGUES, tongues - 1);
                    enderman.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
                    ItemEntity itemEntity = new ItemEntity(player.level, enderman.getX(), enderman.getEyeY(), enderman.getZ(),
                            new ItemStack(FoolishAsteroidsItems.SEVERED_TONGUE.get()));
                    player.level.addFreshEntity(itemEntity);
                    enderman.hurt(DamageSource.GENERIC, 4.0F);
                    ScaleTypes.ATTACK.getScaleData(enderman).setTargetScale(1.2F);
                    enderman.setPersistentAngerTarget(player.getUUID());
                    // TODO: Visual/audio cues
                    if (!player.getLevel().isClientSide) {
                        teleport(player.getX(), player.getY(), player.getZ(), player);
                    }
                }
                if (tongues == 1) {
                    enderman.playSound(SoundEvents.ENDERMAN_DEATH, 1.0F, 0.4F);
                    enderman.setSilent(true);
                }
            }
        }
    }

    static void teleport(double x, double y, double z, LivingEntity livingEntity) {
        Level world = livingEntity.getLevel();

        for (int i = 0; i < 16; i++) {
            double d3 = livingEntity.getX() + (livingEntity.getRandom().nextDouble() - 0.5D) * 6.0D;
            double d4 = Mth.clamp(livingEntity.getY() + (double) (livingEntity.getRandom().nextInt(16) - 8), (double) world.getMinBuildHeight(), (double) (world.getMinBuildHeight() + ((ServerLevel) world).getLogicalHeight() - 1));
            double d5 = livingEntity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5D) * 6.0D;
            if (livingEntity instanceof Player) {
                if (livingEntity.randomTeleport(d3, d4, d5, true)) {
                    SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                    world.playSound((Player) null, x, y, z, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    livingEntity.playSound(soundevent, 1.0F, 1.0F);
                    break; // Exit the loop if teleportation is successful
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof PetrificationMaskItem) {
            Player player = event.getPlayer();
            Level level = event.getWorld();
            float reachDistance = 8 * ScaleTypes.REACH.getScaleData(player).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(player).getBaseScale();
            Vec3 lookVector = player.getLookAngle();
            List<Entity> entities = level.getEntities((Entity) null, player.getBoundingBox().expandTowards(
                    lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
            for (Entity targetEntity : entities) {
                if (targetEntity instanceof Snake) {
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerTouchSnek(PlayerInteractEvent.EntityInteract event) {
        ItemStack stack = event.getPlayer().getItemInHand(event.getHand());
        if (event.getTarget() instanceof Snake snake && stack.getItem() instanceof PetrificationMaskItem) {
            CompoundTag stackTag = stack.getOrCreateTag();
            ListTag snakesTag = stackTag.getList("Snakes", 10);
            if (snakesTag.size() < MAX_SNAKES) {
                CompoundTag snakeData = new CompoundTag();
                snake.save(snakeData);
                snakeData.putString("EntityType", getEntityResourceLocation(snake).toString()); // Store the entity ID
                snakesTag.add(snakeData);
                stackTag.put("Snakes", snakesTag);
                snake.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    private static ResourceLocation getEntityResourceLocation(Entity entity) {
        return Registry.ENTITY_TYPE.getKey(entity.getType());
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);
        BlockState clickedBlockState = player.level.getBlockState(event.getPos());
        if (!player.level.isClientSide() && heldItem.getItem() == Items.GLASS_BOTTLE && clickedBlockState.getBlock() == GBlocks.CHARGED_LUMIERE_BLOCK.get()) {
            if (hand == InteractionHand.MAIN_HAND || (hand == InteractionHand.OFF_HAND && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.GLASS_BOTTLE)) {
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1);  // Decrease the glass bottle stack size
                    if (!player.getAbilities().instabuild) {
                        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(player.getUsedItemHand(), new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()));
                        } else {
                            if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                                player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                            }
                        }
                    }
                } else if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                    player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                }
            } else if (hand == InteractionHand.OFF_HAND && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() != Items.GLASS_BOTTLE) {
                if (!player.isCreative()) {
                    player.getOffhandItem().shrink(1);  // Decrease the glass bottle stack size
                    if (!player.getAbilities().instabuild) {
                        ItemStack stack = player.getItemInHand(InteractionHand.OFF_HAND);
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(player.getUsedItemHand(), new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()));
                        } else {
                            if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                                player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                            }
                        }
                    }
                } else if (!player.getInventory().add(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()))) {
                    player.drop(new ItemStack(FoolishAsteroidsItems.LIGHTNING_BOTTLE.get()), false);
                }
            }

            // Replace the grass block with a bottle of lightning
            player.level.setBlockAndUpdate(event.getPos(), GBlocks.LUMIERE_BLOCK.get().defaultBlockState());


            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);

        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
    }
}
