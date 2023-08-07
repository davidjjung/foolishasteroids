package com.davigj.foolish_asteroids.core.other;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.UUID;

import static com.davigj.foolish_asteroids.common.item.IncendiaryElixirItem.smokingPlayers;
import static com.davigj.foolish_asteroids.common.item.IndomitableElixirItem.rainbowTimers;
import static com.davigj.foolish_asteroids.common.item.ProfoundElixirItem.chatDisableMap;


@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class FoolishAsteroidsEvents {
    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        if (chatDisableMap.containsKey(player)) {
//            The commented out code below would be implemented if I wanted the player to say something other than what they sent, as opposed to nothing
//            TranslatableComponent garbled = new TranslatableComponent("message.profound_elixir.garbled");
//            event.setComponent(garbled);
            event.setCanceled(true);
            TranslatableComponent message = new TranslatableComponent("message.profound.chat_disabled");
            player.displayClientMessage(message, true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.player != null) {
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

            // If the player's chat is disabled and the specified time has elapsed, re-enable chat
            if (chatDisableTime != null && System.currentTimeMillis() >= chatDisableTime) {
                TranslatableComponent message = new TranslatableComponent("message.profound.chat_enabled");
                player.displayClientMessage(message, true);
                chatDisableMap.remove(player);
            }
        }
        if (event.phase == TickEvent.Phase.START && event.player != null && event.player.level.isClientSide) {
            for (UUID playerUUID : smokingPlayers) {
                Level level = event.player.level;
                Player player = level.getPlayerByUUID(playerUUID);
                Random random = new Random();
                if (random.nextInt(10) < 8) {
                    if (player != null && !player.isSpectator() && !player.isCreative()) {
                        // Spawn campfire smoke particles
                        double x = player.getX();
                        double y = player.getY() + player.getEyeHeight();
                        double z = player.getZ();
                        level.addParticle(ParticleTypes.POOF, x, y, z, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            smokingPlayers.remove(player.getUUID());
        }
    }

}
