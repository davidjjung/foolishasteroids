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

import java.util.logging.Logger;

public class DummyElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(DummyElixirItem.class.getName());

    public DummyElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            // Get the playertag of the entity that used the item
            String entityTag = player.getDisplayName().getString();

            LOGGER.info(entityTag);

            // Construct the command with the playertag
            String command = "/scale set pehkui:base 0.5 " + entityTag;

            // Run the command silently without any output in the chat or server log
            CommandSourceStack commandSource = player.createCommandSourceStack();

            // Ensure commandSource.getServer() and commandSource.getServer().getCommands() are not null
            MinecraftServer server = commandSource.getServer();
            if (server != null) {
                Commands commands = server.getCommands();
                if (commands != null) {
                    commands.performCommand(commandSource, command);
                } else {
                    LOGGER.warning("Commands instance is null.");
                }
            } else {
                // LOGGER.warning("MinecraftServer instance is null.");
            }
            return stack;
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }


    public int getUseDuration(ItemStack p_43001_) {
        return 44;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
