package com.davigj.foolish_asteroids.core.other.events;

import com.davigj.foolish_asteroids.common.item.gear.NostalgicGlassesItem;
import com.davigj.foolish_asteroids.common.item.gear.PetrificationMaskItem;
import com.davigj.foolish_asteroids.common.item.gear.RetroSneakersItem;
import com.davigj.foolish_asteroids.common.item.elixir.HeresyElixirItem;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.davigj.foolish_asteroids.core.registry.FAItems;
import com.davigj.foolish_asteroids.core.registry.FAParticleTypes;
import com.davigj.foolish_asteroids.core.util.FADamageSources;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.teamabnormals.autumnity.core.registry.AutumnityParticleTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.davigj.foolish_asteroids.common.item.elixir.HearsayElixirItem.oracleMap;
import static com.davigj.foolish_asteroids.common.item.elixir.IncendiaryElixirItem.smokingPlayers;
import static com.davigj.foolish_asteroids.common.item.elixir.IndomitableElixirItem.rainbowTimers;
import static com.davigj.foolish_asteroids.common.item.elixir.ProfoundElixirItem.chatDisableMap;
import static com.davigj.foolish_asteroids.common.util.Constants.MAX_SNAKES;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class TickEvents {

    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.START && player != null && !player.level.isClientSide) {
            UUID playerId = player.getUUID();
            if (rainbowTimers.containsKey(playerId)) {
                int timerTicks = rainbowTimers.get(playerId);
                if (timerTicks > 0) {
                    timerTicks--;
                    rainbowTimers.put(playerId, timerTicks);
                    if (timerTicks == 0) {
                        // Rainbow effect duration has reached zero, remove the rainbow effect
                        RainbowUtil.setRainbowType(player, 0);
                        rainbowTimers.remove(playerId);
                    }
                }
            }
            if (manager.getValue(player, FADataProcessors.SERAPHIC_ACTIVE) && player.tickCount % 20 == 0) {
                Vec3 lookDirection = player.getLookAngle();
                double horizontalAngle = Math.atan2(lookDirection.z, lookDirection.x);
                double degree = Math.toDegrees(horizontalAngle);
                degree = (degree + 360) % 360;
                boolean sinfulGaze = false;
                switch (manager.getValue(player, FADataProcessors.SERAPHIC_DIR)) {
                    case 0 -> {
                        if (degree >= 180 && degree < 360) sinfulGaze = true;
                    }
                    case 1 -> {
                        if (degree >= 270 || degree < 90) sinfulGaze = true;
                    }
                    case 2 -> {
                        if (degree >= 0 && degree < 180) sinfulGaze = true;
                    }
                    case 3 -> {
                        if (degree >= 90 && degree < 270) sinfulGaze = true;
                    }
                }
                if (sinfulGaze) {
                    player.hurt(FADamageSources.SERAPHIC, 6.0f);
                    TranslatableComponent message = new TranslatableComponent("message.seraphic.damage");
                    player.displayClientMessage(message, true);
                }
            }
        }
        if (event.phase == TickEvent.Phase.END && !player.level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            Long chatDisableTime = chatDisableMap.get(serverPlayer);
            Long oracleTime = oracleMap.get(serverPlayer);

            // If the player's chat is disabled and the specified time has elapsed, re-enable chat
            if (chatDisableTime != null && System.currentTimeMillis() >= chatDisableTime) {
                TranslatableComponent message = new TranslatableComponent("message.profound.chat_enabled");
                serverPlayer.displayClientMessage(message, true);
                chatDisableMap.remove(serverPlayer);
            }

            Long oracleMapTime = oracleMap.get(serverPlayer);
            if (oracleTime != null && System.currentTimeMillis() >= oracleMapTime) {
                TranslatableComponent message = new TranslatableComponent("message.hearsay.use_end");
                serverPlayer.displayClientMessage(message, true);
                TrackedDataManager.INSTANCE.setValue(serverPlayer, FADataProcessors.HEARSAY_ACTIVE, false);
                oracleMap.remove(serverPlayer);
            }
            if (player.tickCount % 20 == 0 && manager.getValue(player, FADataProcessors.AUTUMNAL)) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, false, false));
            }
            int blusterHarvest = manager.getValue(player, FADataProcessors.BLUSTER_HARVEST);
            ItemStack stack;
            switch (blusterHarvest) {
                case 4, 2 -> manager.setValue(player, FADataProcessors.BLUSTER_HARVEST, blusterHarvest - 1);
                case 3 -> {
                    stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    Level level = player.getLevel();
                    if (stack.is(Items.GLASS_BOTTLE)) {
                        stack.shrink(1);
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL_POWDER_SNOW, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        if (stack.isEmpty()) {
                            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FAItems.BLUSTER_BOTTLE.get()));
                        } else if (!player.getInventory().add(new ItemStack(FAItems.BLUSTER_BOTTLE.get()))) {
                            player.drop(new ItemStack(FAItems.BLUSTER_BOTTLE.get()), false);
                        }
                    }
                    manager.setValue(player, FADataProcessors.BLUSTER_HARVEST, 0);
                }
                case 1 -> {
                    stack = player.getItemInHand(InteractionHand.OFF_HAND);
                    Level level = player.getLevel();
                    if (stack.is(Items.GLASS_BOTTLE)) {
                        stack.shrink(1);
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL_POWDER_SNOW, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        if (stack.isEmpty()) {
                            player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(FAItems.BLUSTER_BOTTLE.get()));
                        } else if (!player.getInventory().add(new ItemStack(FAItems.BLUSTER_BOTTLE.get()))) {
                            player.drop(new ItemStack(FAItems.BLUSTER_BOTTLE.get()), false);
                        }
                    }
                    manager.setValue(player, FADataProcessors.BLUSTER_HARVEST, 0);
                }
                default -> {
                    break;
                }
            }
        }
        if (event.phase == TickEvent.Phase.START && player != null) {
            UUID playerId = player.getUUID();
            ScaleData atk = ScaleTypes.ATTACK.getScaleData(player);
            if (smokingPlayers.containsKey(playerId)) {
                int remainingTicks = smokingPlayers.get(playerId);
                if (remainingTicks > 0) {
                    float atkVal = atk.getBaseScale();
                    if (!player.level.isClientSide()) {
                        remainingTicks--;
                        smokingPlayers.put(playerId, remainingTicks);
                        if (remainingTicks <= 400 && remainingTicks >= 201) {
                            atk.setTargetScale(atkVal + 0.006f);
                        } else if (remainingTicks < 200) {
                            atk.setTargetScale(atkVal * 1.02f);
                        } else {
                            atk.setTargetScale(atkVal + 0.01f);
                        }
                    }
                    ParticleOptions particle;
                    if (remainingTicks <= 400 && remainingTicks >= 201) {
                        particle = ParticleTypes.POOF;
                    } else if (remainingTicks < 200) {
                        particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
                    } else {
                        particle = ParticleTypes.SMOKE;
                    }
                    // Spawn campfire smoke particles
                    Level level = player.level;
                    Random random = new Random();
                    double x = player.getX();
                    double y = player.getY() + player.getEyeHeight();
                    double z = player.getZ();
                    if (random.nextInt(10) >= 4 && !player.isSpectator() && !player.isCreative() && player.level.isClientSide()) {
                        if (random.nextInt(10) == 1) {
                            player.playSound(SoundEvents.FURNACE_FIRE_CRACKLE, 1, 1);
                        }
                        level.addParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D);
                    }
                    if (remainingTicks < 10) {
                        player.setSecondsOnFire(8);
                        atk.setTargetScale(0.25f);
                    }
                    if (player.isInWaterRainOrBubble() || player.isInPowderSnow) {
                        atk.setTargetScale(atkVal * 0.75f);
                        player.playSound(SoundEvents.FIRE_EXTINGUISH, 1, 1);
                        smokingPlayers.remove(playerId);
                    }
                } else {
                    smokingPlayers.remove(playerId);
                }
            }
            int rads = manager.getValue(player, FADataProcessors.STORED_ELECTRONS);
            if (rads > 0) {
                Level level = player.level;
                Random random = new Random();
                double x = player.getX();
                double y = player.getY() + player.getEyeHeight() - (ScaleTypes.HEIGHT.getScaleData(player).getBaseScale() / 2);
                double z = player.getZ();
                if (random.nextInt(40) <= rads && !player.isSpectator() && !player.isCreative()) {
                    level.addParticle(FAParticleTypes.ELECTRON.get(), x, y, z,
                            10, 3, 10);
                }
            }
            if (player.tickCount % 5 == 0 && manager.getValue(player, FADataProcessors.AUTUMNAL)) {
                Random rand = new Random();
                double x = player.getX() - 0.5;
                double y = player.getY();
                double z = player.getZ() - 0.5;
                Level level = player.level;
                BlockPos pos = player.blockPosition();
                int color = ((Biome) level.m_204166_(pos).m_203334_()).getFoliageColor();
                double d0 = (double) ((float) (color >> 16 & 255) / 255.0F);
                double d1 = (double) ((float) (color >> 8 & 255) / 255.0F);
                double d2 = (double) ((float) (color & 255) / 255.0F);
                double d3 = (double) ((float) x + rand.nextFloat());
                double d6 = (double) ((float) z + rand.nextFloat());
                level.addParticle((ParticleOptions) AutumnityParticleTypes.FALLING_MAPLE_LEAF.get(), d3, y + 0.025, d6, d0, d1, d2);
            }
            int bluster = manager.getValue(player, FADataProcessors.BLUSTER_HARVEST);
            if (bluster == 3) {
                player.swing(InteractionHand.MAIN_HAND);
            } else if (bluster == 1) {
                player.swing(InteractionHand.OFF_HAND);
            }
        }
    }

    @SubscribeEvent
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack helm = player.getItemBySlot(EquipmentSlot.HEAD);
            if (helm.getItem() instanceof NostalgicGlassesItem) {
                event.setYaw(event.getYaw() + 180.0F);
            }
        }
    }

    @SubscribeEvent
    public static void onFOVSetup(EntityViewRenderEvent.FieldOfView event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack gauntlets = player.getItemBySlot(EquipmentSlot.FEET);
            if (gauntlets.getItem() instanceof RetroSneakersItem) {
                event.setFOV(70.0F);
            }
        }
    }

    @SubscribeEvent
    public static void renderFirstPersonArm(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack helm = player.getItemBySlot(EquipmentSlot.HEAD);
            if (helm.getItem() instanceof NostalgicGlassesItem) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPetrify(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        // TODO: Medusa particles
        if (entity.tickCount % 20 == 0) {
            float reachDistance = 36.0F * ScaleTypes.REACH.getScaleData(entity).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(entity).getBaseScale(); // Adjust the reach distance as needed
            Vec3 lookVector = entity.getLookAngle();
            Vec3 eyePosition = entity.getEyePosition(1.0F);
            Vec3 endPoint = eyePosition.add(lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance);
            int chunkRadius = 1;
            int minY = (int) entity.getY() - 30;
            int maxY = (int) entity.getY() + 30;
            ChunkPos playerChunkPos = new ChunkPos(entity.blockPosition());
            for (int xOffset = -chunkRadius; xOffset <= chunkRadius; xOffset++) {
                for (int zOffset = -chunkRadius; zOffset <= chunkRadius; zOffset++) {
                    ChunkPos targetChunkPos = new ChunkPos(playerChunkPos.x + xOffset, playerChunkPos.z + zOffset);
                    if (entity.level.hasChunk(targetChunkPos.x, targetChunkPos.z)) {
                        List<Entity> entitiesInChunk = entity.level.getEntities(null, new AABB(targetChunkPos.getMinBlockX(), minY, targetChunkPos.getMinBlockZ(), targetChunkPos.getMaxBlockX(), maxY, targetChunkPos.getMaxBlockZ()));
                        entitiesInChunk.removeIf(targetEntity -> !(targetEntity instanceof LivingEntity));
                        entitiesInChunk.removeIf(targetEntity -> !(targetEntity.getY() >= minY && targetEntity.getY() <= maxY));
                        for (Entity targetEntity : entitiesInChunk) {
                            AABB entityBoundingBox = targetEntity.getBoundingBox().inflate(0.5F);
                            EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity.level, entity, eyePosition, endPoint, entityBoundingBox, entity1 -> entity1 != entity);
                            if (entityHitResult != null) {
                                ClipContext clipContext = new ClipContext(eyePosition, endPoint, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
                                BlockHitResult hitResult = entity.level.clip(clipContext);
                                if (hitResult.getType() == HitResult.Type.BLOCK) {
                                    double blockHitDistance = hitResult.getLocation().distanceTo(eyePosition);
                                    double entityHitDistance = eyePosition.distanceTo(entityHitResult.getLocation());
                                    if (blockHitDistance > entityHitDistance) {
                                        Entity hitEntity = entityHitResult.getEntity();
                                        if (hitEntity instanceof LivingEntity living) {
                                            applyPetrifyEffect(living, entity);
                                            applyPetrifyEffect(entity, living);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
// /summon minecraft:zombie ~ ~ ~ {ArmorItems:[{id:"minecraft:chainmail_boots",Count:1b},{id:"minecraft:iron_leggings",Count:1b},{id:"minecraft:golden_chestplate",Count:1b},{id:"foolish_asteroids:mask_of_petrification",Count:1b}], NoAI:true}

    // Function to check if a player is wearing the Petrification Mask
    private static boolean isWearingPetrificationMask(LivingEntity living) {
        ItemStack headSlot = living.getItemBySlot(EquipmentSlot.HEAD);
        return !headSlot.isEmpty() && headSlot.getItem() instanceof PetrificationMaskItem;
    }

    private static void applyPetrifyEffect(LivingEntity villain, LivingEntity victim) {
        if (isWearingPetrificationMask(villain)) {
            CompoundTag stackTag = villain.getItemBySlot(EquipmentSlot.HEAD).getOrCreateTag();
            ListTag snakesTag = stackTag.getList("Snakes", 10);
            if (snakesTag.size() > 1) {
                victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0, false, false));
                if (snakesTag.size() == MAX_SNAKES) {
                    victim.hurt(FADamageSources.MEDUSA, 3.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (entity instanceof Witch && !entity.level.isClientSide()) {
            if (entity.tickCount % 20 == 0) {
                Entity target = ((Witch) entity).getTarget();
                if (target instanceof Player player) {
                    if (player.getMainHandItem().getItem() instanceof HeresyElixirItem ||
                            player.getOffhandItem().getItem() instanceof HeresyElixirItem) {
                        ((Witch) entity).setTarget(null);
                    }
                }
            }
        }
        if (entity instanceof Ghast ghast && entity.tickCount % 20 == 0) {
            int blusterTimer = manager.getValue(ghast, FADataProcessors.BLUSTER_RECHARGE);
            if (blusterTimer != 0) {
                manager.setValue(ghast, FADataProcessors.BLUSTER_RECHARGE, blusterTimer - 1);
            }
        }
    }
}
