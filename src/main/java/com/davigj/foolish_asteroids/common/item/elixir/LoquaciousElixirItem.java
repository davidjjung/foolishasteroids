package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoquaciousElixirItem extends ElixirItem {

    public LoquaciousElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        MinecraftServer server = entityLiving.getLevel().getServer();
        float atk = ScaleTypes.ATTACK.getScaleData(entityLiving).getBaseScale();

        if (atk < 4.0) {
            ScaleTypes.ATTACK.getScaleData(entityLiving).setTargetScale(atk + 0.2f);
            atk = ScaleTypes.ATTACK.getScaleData(entityLiving).getBaseScale();
        }
        if (server != null) {
            Random random = new Random();
            int prompt = random.nextInt(5);
            String announcement = "";
            switch (prompt) {
                case 0 -> {
                    announcement = "Good day for a swell battle! ";
                }
                case 1 -> {
                    announcement = "This match will get red hot! ";
                }
                case 2 -> {
                    announcement = "Here's a real high-class bout! ";
                }
                case 3 -> {
                    announcement = "A great slam and then some! ";
                }
                case 4 -> {
                    announcement = "A brawl is surely brewing! ";
                }
            }
            String result = announcement + player.getDisplayName().getString() + "'s attack is multiplied by a factor of " + String.format("%.2f", atk);
            TranslatableComponent message = new TranslatableComponent("message.loquacious.attribute", result);
            // Choose a random active player that isn't the player who consumed the elixir
            List<ServerPlayer> eligiblePlayers = new ArrayList<>(server.getPlayerList().getPlayers());
            eligiblePlayers.remove(player);
            if (!eligiblePlayers.isEmpty()) {
                ServerPlayer randomPlayer = eligiblePlayers.get(new Random().nextInt(eligiblePlayers.size()));
                // Display the message to the chosen player
                randomPlayer.displayClientMessage(message, true);
            }
        }
    }
}
