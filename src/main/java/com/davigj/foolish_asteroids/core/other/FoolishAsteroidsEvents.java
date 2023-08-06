package com.davigj.foolish_asteroids.core.other;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.davigj.foolish_asteroids.common.item.IndomitableElixirItem.rainbowTimers;
import static com.davigj.foolish_asteroids.common.item.ProfoundElixirItem.chatDisableMap;


@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class FoolishAsteroidsEvents {
    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        if (chatDisableMap.containsKey(player)) {
            TranslatableComponent garbled = new TranslatableComponent("message.profound_elixir.garbled");
            event.setComponent(garbled);
            event.setCanceled(true);
            TranslatableComponent message = new TranslatableComponent("message.profound_elixir.chat_disabled");
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
                TranslatableComponent message = new TranslatableComponent("message.profound_elixir.chat_enabled");
                player.displayClientMessage(message, true);
                chatDisableMap.remove(player);
            }
        }
    }

}
