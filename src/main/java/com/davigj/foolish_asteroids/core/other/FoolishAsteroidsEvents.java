package com.davigj.foolish_asteroids.core.other;

import com.brewinandchewin.core.registry.BCEffects;
import com.davigj.foolish_asteroids.common.item.elixir.HeresyElixirItem;
import com.davigj.foolish_asteroids.common.util.HearsayUtil;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsParticleTypes;
import com.davigj.foolish_asteroids.core.util.FoolishAsteroidsDamageSources;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.teamabnormals.autumnity.core.registry.AutumnityParticleTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import static com.davigj.foolish_asteroids.common.item.elixir.HearsayElixirItem.oracleMap;
import static com.davigj.foolish_asteroids.common.item.elixir.IncendiaryElixirItem.smokingPlayers;
import static com.davigj.foolish_asteroids.common.item.elixir.IndomitableElixirItem.rainbowTimers;
import static com.davigj.foolish_asteroids.common.item.elixir.ProfoundElixirItem.chatDisableMap;
import static com.davigj.foolish_asteroids.common.util.HearsayUtil.conversations;


@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class FoolishAsteroidsEvents {

    private static final Logger LOGGER = Logger.getLogger(FoolishAsteroidsEvents.class.getName());

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String senderID;
        String trueSenderID = "<" + event.getUsername() + "> ";

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty() && helmet.getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
            CompoundTag helmetTag = helmet.getTag();
            if (helmetTag != null && helmetTag.contains("display", 10)) {
                CompoundTag displayTag = helmetTag.getCompound("display");
                if (displayTag.contains("Name", 8)) {
                    String nameJson = displayTag.getString("Name");
                    try {
                        Component nameComponent = Component.Serializer.fromJson(nameJson);
                        if (nameComponent instanceof TextComponent) {
                            senderID = "<" + ((TextComponent) nameComponent).getText() + "> ";
                        } else {
                            senderID = "<Anonymous> ";
                        }
                    } catch (Exception e) {
                        senderID = "<Anonymous> ";
                    }
                } else {
                    senderID = "<Anonymous> ";
                }
            } else {
                senderID = "<Anonymous> ";
            }
        } else {
            senderID = "<" + event.getUsername() + "> ";
        }

        TextComponent original = (TextComponent) new TextComponent(senderID).append(event.getMessage());
        TranslatableComponent modified = new TranslatableComponent(HearsayUtil.getDialogueLine(player).getKey(), senderID);
        if (chatDisableMap.containsKey(player)) {
            event.setCanceled(true);
            TranslatableComponent message = new TranslatableComponent("message.profound.chat_disabled");
            player.displayClientMessage(message, true);
        } else if (oracleMap.containsKey(player)) {
            for (ServerPlayer receiver : Objects.requireNonNull(player.level.getServer()).getPlayerList().getPlayers()) {
                if (!oracleMap.containsKey(receiver) || receiver == player) {
                    receiver.sendMessage(original, receiver.getUUID());
                } else {
                    receiver.sendMessage(modified, receiver.getUUID());
                    int dialogueIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX) + 1;
                    int convoIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.CONVO_INDEX);
                    TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, dialogueIndex);
                    if (dialogueIndex == conversations[convoIndex].length) {
                        Random random = new Random();
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, 0);
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.CONVO_INDEX, random.nextInt(3));
                    }
                }
            }
        } else {
            for (ServerPlayer receiver : Objects.requireNonNull(player.level.getServer()).getPlayerList().getPlayers()) {
                if (oracleMap.containsKey(receiver)) {
                    receiver.sendMessage(modified, receiver.getUUID());
                    int dialogueIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX) + 1;
                    int convoIndex = TrackedDataManager.INSTANCE.getValue(receiver, FoolishAsteroidsMod.CONVO_INDEX);
                    TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, dialogueIndex);
                    if (dialogueIndex == conversations[convoIndex].length) {
                        Random random = new Random();
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.DIALOGUE_INDEX, 0);
                        TrackedDataManager.INSTANCE.setValue(receiver, FoolishAsteroidsMod.CONVO_INDEX, random.nextInt(3));
                    }
                } else {
                    receiver.sendMessage(original, receiver.getUUID());
                }
            }
        }
        // anywho since we're hijacking this junt the original message has been disemboweled
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
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
            if (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE) && player.tickCount % 20 == 0) {
                Vec3 lookDirection = player.getLookAngle();
                double horizontalAngle = Math.atan2(lookDirection.z, lookDirection.x);
                double degree = Math.toDegrees(horizontalAngle);
                degree = (degree + 360) % 360;
                Boolean sinfulGaze = false;
                switch (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_DIR)) {
                    case 0 -> {
                        if (degree >= 180 && degree < 360) {
                            sinfulGaze = true;
                        }
                    }
                    case 1 -> {
                        if (degree >= 270 || degree < 90) {
                            sinfulGaze = true;
                        }
                    }
                    case 2 -> {
                        if (degree >= 0 || degree < 180) {
                            sinfulGaze = true;
                        }
                    }
                    case 3 -> {
                        if (degree >= 90 || degree < 270) {
                            sinfulGaze = true;
                        }
                    }
                }
                if (sinfulGaze) {
                    player.hurt(FoolishAsteroidsDamageSources.SERAPHIC, 6.0f);
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
                TrackedDataManager.INSTANCE.setValue(serverPlayer, FoolishAsteroidsMod.HEARSAY_ACTIVE, false);
                oracleMap.remove(serverPlayer);
            }
            if (player.tickCount % 20 == 0 && manager.getValue(player, FoolishAsteroidsMod.AUTUMNAL)) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, false, false));
            }
        }
        if (event.phase == TickEvent.Phase.START && player != null && player.level.isClientSide()) {
            UUID playerId = player.getUUID();
            ScaleData atk = ScaleTypes.ATTACK.getScaleData(player);
            if (smokingPlayers.containsKey(playerId)) {
                int remainingTicks = smokingPlayers.get(playerId);
                if (remainingTicks > 0) {
                    remainingTicks--;
                    smokingPlayers.put(playerId, remainingTicks);
                    float atkVal = atk.getBaseScale();
                    ParticleOptions particle;
                    if (remainingTicks <= 400 && remainingTicks >= 201) {
                        particle = ParticleTypes.POOF;
                        atk.setTargetScale(atkVal + 0.006f);
                    } else if (remainingTicks < 200) {
                        atk.setTargetScale(atkVal * 1.02f);
                        particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
                    } else {
                        particle = ParticleTypes.SMOKE;
                        atk.setTargetScale(atkVal + 0.01f);
                    }
                    // Spawn campfire smoke particles
                    Level level = player.level;
                    Random random = new Random();
                    double x = player.getX();
                    double y = player.getY() + player.getEyeHeight();
                    double z = player.getZ();
                    if (random.nextInt(10) >= 4 && !player.isSpectator() && !player.isCreative()) {
                        level.addParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D);
                    }
                    if (remainingTicks < 20) {
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
            int rads = manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS);
            if (rads > 0) {
                Level level = player.level;
                Random random = new Random();
                double x = player.getX();
                double y = player.getY() + player.getEyeHeight() - (ScaleTypes.HEIGHT.getScaleData(player).getBaseScale() / 2);
                double z = player.getZ();
                if (random.nextInt(40) <= rads && !player.isSpectator() && !player.isCreative()) {
                    level.addParticle(FoolishAsteroidsParticleTypes.ELECTRON.get(), x, y, z,
                            10, 3, 10);
                }
            }
            if (player.tickCount % 5 == 0 && manager.getValue(player, FoolishAsteroidsMod.AUTUMNAL)) {
                Random rand = new Random();
                rand.nextInt(1);
                double x = player.getX() - 0.5;
                double y = player.getY();
                double z = player.getZ() - 0.5;
                Level level = player.level;
                BlockPos pos = player.blockPosition();
                int color = ((Biome)level.m_204166_(pos).m_203334_()).getFoliageColor();
                double d0 = (double) ((float) (color >> 16 & 255) / 255.0F);
                double d1 = (double) ((float) (color >> 8 & 255) / 255.0F);
                double d2 = (double) ((float) (color & 255) / 255.0F);
                double d3 = (double) ((float) x + rand.nextFloat());
                double d6 = (double) ((float) z + rand.nextFloat());
                level.addParticle((ParticleOptions) AutumnityParticleTypes.FALLING_MAPLE_LEAF.get(), d3, y + 0.025, d6, d0, d1, d2);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        DamageSource source = event.getSource();
        if (event.getEntity() instanceof Player player && manager.getValue(player, FoolishAsteroidsMod.RAD_POISONING) && !source.isMagic()) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
        }
        if (event.getEntity() instanceof Player player && manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS) > 0 && !source.isMagic()) {
            double explosionX = player.getX();
            double explosionY = player.getY() + player.getEyeHeight();
            double explosionZ = player.getZ();
            float explosionPower = 1.3f; // Adjust explosion power as needed
            int electrons = manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS);
            double explosionRadius = (0.25 * electrons) + 1; // Adjust the radius as needed

            for (LivingEntity entity : player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(explosionRadius, 2.0D, explosionRadius))) {
                // Apply explosion forces to entities within the radius
                if (entity != player) {
                    Vec3 pushVector = entity.position().subtract(explosionX, explosionY, explosionZ).normalize();
                    double pushStrength = 0.6 + (0.05 * electrons); // Adjust the strength as needed
                    entity.setOnGround(false);
                    entity.push(pushVector.x * pushStrength, 0.3 + (electrons * 0.075)* (1.0D - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)), pushVector.z * pushStrength);
                }
            }
            player.level.explode(player, explosionX, explosionY, explosionZ, explosionPower, false, Explosion.BlockInteraction.NONE);
            manager.setValue(player, FoolishAsteroidsMod.STORED_ELECTRONS, manager.getValue(player, FoolishAsteroidsMod.STORED_ELECTRONS) - 1);
            Random random = new Random();
            if (random.nextInt(5) <= 2) {
                manager.setValue(player, FoolishAsteroidsMod.RAD_POISONING, false);
            }
        }
        if (event.getEntity() instanceof Player player) {
            // TODO: Play sound of magical wind dispelling
            manager.setValue(player, FoolishAsteroidsMod.AUTUMNAL, false);
        }
    }


    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            smokingPlayers.remove(player.getUUID());
            rainbowTimers.remove(player.getUUID());
            oracleMap.remove(player.getUUID());
        }
        if (event.getSource().equals(FoolishAsteroidsDamageSources.SERAPHIC) && event.getEntity() instanceof Player player) {
            BlockPos basaltPos = player.blockPosition();
            Level world = player.level;

            int towerHeight = Math.max(1, (int) (ScaleTypes.HEIGHT.getScaleData(player).getBaseScale() / .5f));
            int i = 0;
            while (i < towerHeight) {
                if (world.getBlockState(basaltPos).isAir()) {
                    world.setBlockAndUpdate(basaltPos, Blocks.BASALT.defaultBlockState());
                }
                basaltPos = basaltPos.above();
                i++;
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (entity instanceof Witch && !entity.level.isClientSide()) {
            // Only apply logic on the server side for Witches

            if (entity.tickCount % 20 == 0) {
                // Check every second (20 ticks) to reduce frequency

                Entity target = ((Witch) entity).getTarget();
                if (target instanceof Player) {
                    Player player = (Player) target;
                    if (player.getMainHandItem().getItem() instanceof HeresyElixirItem ||
                            player.getOffhandItem().getItem() instanceof HeresyElixirItem) {
                        // Cancel the event to prevent the Witch from attacking the player
                        ((Witch) entity).setTarget(null);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        Player player = event.getPlayer();
        if (manager.getValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL) != 0) {
            manager.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 0);
            TranslatableComponent message = new TranslatableComponent("message.hearsay.forgiveness");
            event.getPlayer().displayClientMessage(message, true);
        } else if (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE)) {
            manager.setValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE, false);
        }
        manager.setValue(player, FoolishAsteroidsMod.STORED_ELECTRONS, 0);
        manager.setValue(player, FoolishAsteroidsMod.RAD_POISONING, false);
        manager.setValue(player, FoolishAsteroidsMod.AUTUMNAL, false);
        manager.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, 0);
    }

    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionApplicableEvent event) {
        if (!event.getEntity().level.isClientSide()) {
            if (event.getPotionEffect().getEffect() == BCEffects.TIPSY.get() && event.getEntity() instanceof Player player) {
                MobEffectInstance tipsy = event.getPotionEffect();
                int antiDrunk = TrackedDataManager.INSTANCE.getValue(player, FoolishAsteroidsMod.ANTI_DRUNK);
                if (antiDrunk > 0) {
                    int lvl = tipsy.getAmplifier() + 1; // gets actual level
                    int duration = tipsy.getDuration();
                    int existingTipsyLvl = 0;
                    int existingTipsyDur = 0;
                    for (MobEffectInstance existingEffect : player.getActiveEffects()) {
                        if (existingEffect.getEffect().equals(BCEffects.TIPSY.get())) {
                            existingTipsyLvl = existingEffect.getAmplifier() + 1; // current level of tipsy
                            existingTipsyDur = existingEffect.getDuration();
                        }
                    }
                    if (antiDrunk - lvl >= 0) {
                        TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, antiDrunk - lvl);
                    } else {
                        // the incoming tipsy exceeds antidrunk
                        TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, 0);
                        if (existingTipsyLvl == 0) {
                            player.addEffect(new MobEffectInstance(BCEffects.TIPSY.get(), (int) (duration * 0.5), lvl - antiDrunk - 1));
                        } else {
                            player.addEffect(new MobEffectInstance(BCEffects.TIPSY.get(), (int) (existingTipsyDur + (duration * 0.5)), existingTipsyLvl + lvl - antiDrunk - 2));
                        }
                    }
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
