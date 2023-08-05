package com.davigj.foolish_asteroids.common.item;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GarrulousElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(GarrulousElixirItem.class.getName());

    public GarrulousElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            Random random = new Random();
            int luckyDay = random.nextInt(10);
            MinecraftServer server = player.getLevel().getServer();
            String commandToExecute = "";

            if (luckyDay < 4 && server != null) {
                List<ServerPlayer> players = server.getPlayerList().getPlayers();
                if (!players.isEmpty()) {
                    ServerPlayer randomPlayer = players.get(new Random().nextInt(players.size()));
                    String pTag = randomPlayer.getDisplayName().getString();
                    commandToExecute += this.getStarterComment() + this.getIntermediateComment();
                    switch (luckyDay) {
                        case 1:
                            commandToExecute += pTag + " was caught skulking around latitude band " + randomPlayer.getX();
                        case 2:
                            commandToExecute += pTag + " was spotted meandering around altitude " + randomPlayer.getY();
                        case 3:
                            commandToExecute += pTag + " was heard maniacally laughing in longitude band " + randomPlayer.getY();
                    }
                    int posX = (int) randomPlayer.getX();

                }
            }

            // Get the playertag of the entity that used the item
            String entityTag = player.getDisplayName().getString();
            // Construct the command with the playertag

            // Run the command silently without any output in the chat or server log
            CommandSourceStack commandSource = player.createCommandSourceStack();
            // Ensure commandSource.getServer() and commandSource.getServer().getCommands() are not null
            MinecraftServer yeah = commandSource.getServer();
            if (yeah != null) {
                Commands commands = server.getCommands();
                if (commands != null) {
                    commands.performCommand(commandSource, commandToExecute);
                } else {
                    LOGGER.warning("Command instance is null.");
                }
            }
            return stack;
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }
    private String getStarterComment() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] comments = {"By the Pig's Pen! ", "Jumping jerboas... ", "Oh, my stars and garters... ", "Land sakes, alive and dead! ",
                "Well, I'll be a chimpanzee's uncle. ", "Hot diggity tamed wolf! ", "Ho ho ho, this just in... ", "No time to waste: ",
                "By the comets above! ", "Stop the mechanical presses! ", "On your left! ", "Oh no, say it isn't so! "};

        // Pick a random fruit from the list
        Random random = new Random();
        int index = random.nextInt(comments.length);
        return comments[index];
    }
    private String getIntermediateComment() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] comments = {"It appears that ", "If my sources are correct, ", "I heard through the mulberry vine that ", "" +
                "It has come to my information that ", "Not to be a purveyor of gossip, but ", "Is that...why yes, it is! ",
                "According to our local divination expert, ", "I feel it must be said: ", "Pains me as it does to say this, ",
                "", "It seems ", "You heard it here first: "};

        Random random = new Random();
        int index = random.nextInt(comments.length);
        return comments[index];
    }

    public int getUseDuration(ItemStack p_43001_) {
        return 44;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
