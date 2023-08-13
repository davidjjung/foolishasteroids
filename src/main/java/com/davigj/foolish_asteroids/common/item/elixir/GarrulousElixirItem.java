package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GarrulousElixirItem extends ElixirItem {

    private static final Logger LOGGER = Logger.getLogger(GarrulousElixirItem.class.getName());

    public GarrulousElixirItem(Properties properties) {
        super(properties);
    }
    private String getStarterComment() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] comments = {"By the Pig's Pen! ", "Jumping jerboas... ", "Oh, my stars and garters... ", "Land sakes, alive and dead! ",
                "Well, I'll be a chimpanzee's uncle. ", "Hot diggity tamed wolf! ", "Oho, this just in... ", "No time to waste! ",
                "By the comets above! ", "Stop the mechanical presses! ", "On your left! ", "Oh no, say it isn't so! "};

        // Pick a random fruit from the list
        Random random = new Random();
        int index = random.nextInt(comments.length);
        return comments[index];
    }
    private String getIntermediateComment() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] comments = {"It appears that ", "If my sources are correct, ", "I heard through the mulberry vine that ", "" +
                "It has come to my attention that ", "Not to be a purveyor of gossip, but ", "Is that...why yes, it is! ",
                "According to our local divination expert, ", "I feel it must be said: ", "Pains me as it does to say this, ",
                "", "It seems ", "You heard it here first: "};

        Random random = new Random();
        int index = random.nextInt(comments.length);
        return comments[index];
    }
    private String getActionComment() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] comments = {" was spotted meandering around ", " was spotted communicating with snakes at ",
        " was last seen drinking copious amounts of coffee at ", " was reportedly swallowing jerboas whole at ",
        " was heard maniacally laughing somewhere around ", " was seen disappearing into the thicket roughly around ",
        " was heard jeering at some innocent villagers at ", " was observed consuming some foul-looking berries at "};

        Random random = new Random();
        int index = random.nextInt(comments.length);
        return comments[index];
    }
    private String getTailComment() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] comments = {". You heard it here first, folks. Stay safe!", ". What are you cooking?",
                ". Remember to lock your doors, kids!", ". Whatcha doooin'?",
                ". Stay safe out there.", ". Good luck to you all!",
                ". Nothing too extreme, I hope!"};

        Random random = new Random();
        int index = random.nextInt(comments.length);
        return comments[index];
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        MinecraftServer server = entityLiving.getLevel().getServer();
        if (entityLiving instanceof Player player && server != null) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
            Random random = new Random();
            int luckyDay = random.nextInt(8);
            String entityTag = player.getDisplayName().getString();
            String finalMessage = "<" + entityTag + "> ";
            int x = 0;
            int y = 0;
            int z = 0;
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            ServerPlayer randomPlayer = players.get(new Random().nextInt(players.size()));
            if (randomPlayer.getDisplayName().getString().equals(entityTag)) {
                luckyDay = random.nextInt(3) + 5;
            }

            if (luckyDay < 3) {
                while (true) {
                    randomPlayer = players.get(random.nextInt(players.size()));
                    // Check if the random player's display name is different from the entityTag
                    if (!randomPlayer.getDisplayName().getString().equals(entityTag)) {
                        String pTag = randomPlayer.getDisplayName().getString();
                        break; // Break the loop since we found a different player
                    }
                }
                String pTag = randomPlayer.getDisplayName().getString();
                finalMessage += this.getStarterComment() + this.getIntermediateComment() + pTag + this.getActionComment();
                x = (int) randomPlayer.getX();
                y = (int) randomPlayer.getY();
                z = (int) randomPlayer.getZ();
                switch (luckyDay) {
                    case 0 -> finalMessage += "latitude band " + x;
                    case 1 -> finalMessage += "altitude band " + y;
                    case 2 -> finalMessage += "longitude band " + z;
                }
                finalMessage += this.getTailComment();
            } else if (luckyDay > 2 && luckyDay < 5) {
                if (!players.isEmpty()) {
                    String pTag = randomPlayer.getDisplayName().getString();
                    while (true) {
                        randomPlayer = players.get(random.nextInt(players.size()));
                        // Check if the random player's display name is different from the entityTag
                        if (!randomPlayer.getDisplayName().getString().equals(entityTag)) {
                            pTag = randomPlayer.getDisplayName().getString();
                            break; // Break the loop since we found a different player
                        }
                    }
                    finalMessage += this.getStarterComment() + this.getIntermediateComment() + pTag + this.getActionComment();
                    x = (int) randomPlayer.getX();
                    y = (int) randomPlayer.getY();
                    z = (int) randomPlayer.getZ();
                    switch (luckyDay) {
                        case 3 ->
                                finalMessage += "latitude band " + x + " and altitude band " + y;
                        case 4 ->
                                finalMessage += "longitude band " + z + " and altitude band " + y;
                    }
                    finalMessage += this.getTailComment();
                }
            } else if (luckyDay >= 5) {
                finalMessage += "You can find me at ";
                x = (int) player.getX();
                y = (int) player.getY();
                z = (int) player.getZ();
                switch (luckyDay) {
                    case 5 -> finalMessage += "latitude band " + x;
                    case 6 -> finalMessage += "altitude band " + y;
                    case 7 -> finalMessage += "longitude band " + z;
                }
                finalMessage += this.getTailComment();
            }

            TextComponent messageComponent = new TextComponent(finalMessage);
            if (server != null) {
                for (ServerPlayer p : server.getPlayerList().getPlayers()) {
                    p.sendMessage(messageComponent, p.getUUID());
                }
            }
        }
    }
}
