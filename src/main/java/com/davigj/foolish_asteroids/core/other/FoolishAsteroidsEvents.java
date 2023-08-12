package com.davigj.foolish_asteroids.core.other;

import com.brewinandchewin.core.registry.BCEffects;
import com.davigj.foolish_asteroids.common.item.elixir.HeresyElixirItem;
import com.davigj.foolish_asteroids.common.util.HearsayUtil;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
        if (event.phase == TickEvent.Phase.START && event.player != null && !event.player.level.isClientSide) {
            UUID playerId = event.player.getUUID();
            if (rainbowTimers.containsKey(playerId)) {
                int timerTicks = rainbowTimers.get(playerId);
                if (timerTicks > 0) {
                    timerTicks--;
                    rainbowTimers.put(playerId, timerTicks);
                    if (timerTicks == 0) {
                        // Rainbow effect duration has reached zero, remove the rainbow effect
                        RainbowUtil.setRainbowType(event.player, 0);
                        rainbowTimers.remove(playerId);
                    }
                }
            }
        }
        if (event.phase == TickEvent.Phase.END && !event.player.level.isClientSide) {
            ServerPlayer player = (ServerPlayer) event.player;
            Long chatDisableTime = chatDisableMap.get(player);
            Long oracleTime = oracleMap.get(player);

            // If the player's chat is disabled and the specified time has elapsed, re-enable chat
            if (chatDisableTime != null && System.currentTimeMillis() >= chatDisableTime) {
                TranslatableComponent message = new TranslatableComponent("message.profound.chat_enabled");
                player.displayClientMessage(message, true);
                chatDisableMap.remove(player);
            }

            Long oracleMapTime = oracleMap.get(player);
            if (oracleTime != null && System.currentTimeMillis() >= oracleMapTime) {
                TranslatableComponent message = new TranslatableComponent("message.hearsay.use_end");
                player.displayClientMessage(message, true);
                TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.HEARSAY_ACTIVE, false);
                oracleMap.remove(player);
            }
        }
        if (event.phase == TickEvent.Phase.START && event.player != null && event.player.level.isClientSide()) {
            UUID playerId = event.player.getUUID();
            Player player = event.player;
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
        }

    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            smokingPlayers.remove(player.getUUID());
            rainbowTimers.remove(player.getUUID());
            oracleMap.remove(player.getUUID());
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
        if (TrackedDataManager.INSTANCE.getValue(event.getPlayer(), FoolishAsteroidsMod.HIGHWAY_TO_HELL) != 0) {
            TrackedDataManager.INSTANCE.setValue(event.getPlayer(), FoolishAsteroidsMod.HIGHWAY_TO_HELL, 0);
            TranslatableComponent message = new TranslatableComponent("message.hearsay.forgiveness");
            event.getPlayer().displayClientMessage(message, true);
        }
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
