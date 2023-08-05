package com.davigj.foolish_asteroids.common.item;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class AromaticElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(CommandElixirItem.class.getName());

    public AromaticElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            // Get the playertag of the entity that used the item
            String entityTag = player.getDisplayName().getString();
            String fruit = "";
            // Pick an item out of the forge:fruits item tag, and turn it into its string ("modid:item_name")

            // Define fruit as the string

            // Construct the command with the playertag
            String command = "/give " + entityTag + fruit;
            // Run the command silently without any output in the chat or server log
            CommandSourceStack commandSource = player.createCommandSourceStack();
            // Ensure commandSource.getServer() and commandSource.getServer().getCommands() are not null
            MinecraftServer server = commandSource.getServer();
            if (server != null) {
                Commands commands = server.getCommands();
                if (commands != null) {
                    commands.performCommand(commandSource, command);
//                        LOGGER.info(command);
                } else {
                    LOGGER.warning("Command instance is null.");
                }
            }

            return stack;
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }

    private String getRandomFruit() {
        // Replace this with the actual list of fruits from the forge:fruits item tag
        String[] fruits = {"neapolitan:strawberries", "neapolitan:banana", "environmental:cherry", "minecraft:sweet_berries",
                "autumnity:foul_berries", "minecraft:glow_berries", "minecraft:chorus_fruit", "minecraft:melon_slice",
                "atmospheric:passionfruit", "atmospheric:yucca"};

        // Pick a random fruit from the list
        Random random = new Random();
        int index = random.nextInt(fruits.length);
        return fruits[index];
    }

    public int getUseDuration(ItemStack p_43001_) {
        return 44;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
