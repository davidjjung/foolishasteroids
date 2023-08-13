package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.logging.Logger;

public class CommandElixirItem extends ElixirItem {
    List<String> commands;
    private static final Logger LOGGER = Logger.getLogger(CommandElixirItem.class.getName());

    public CommandElixirItem(Properties properties, List<String> commands) {
        super(properties);
        this.commands = commands;
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        String entityTag = player.getDisplayName().getString();
        for (String command : this.commands) {
            String commandToExecute = command + entityTag;
            CommandSourceStack commandSource = player.createCommandSourceStack();
            MinecraftServer server = commandSource.getServer();
            if (server != null) {
                Commands commands = server.getCommands();
                if (commands != null) {
                    commands.performCommand(commandSource, commandToExecute);
//                        LOGGER.info(command);
                } else {
                    LOGGER.warning("Command instance is null.");
                }
            }
        }
    }
}
