package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.logging.Logger;

public class CommandElixirItem extends Item {
    List<String> commands;

    private static final Logger LOGGER = Logger.getLogger(CommandElixirItem.class.getName());

    public CommandElixirItem(Properties properties, List<String> commands) {
        super(properties);
        this.commands = commands;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) entityLiving;
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
            // Get the playertag of the entity that used the item
            String entityTag = player.getDisplayName().getString();
            for (String command : this.commands) {
                // Construct the command with the playertag
                String commandToExecute = command + entityTag;
                // Run the command silently without any output in the chat or server log
                CommandSourceStack commandSource = player.createCommandSourceStack();
                // Ensure commandSource.getServer() and commandSource.getServer().getCommands() are not null
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
            if (stack.isEmpty()) {
                return new ItemStack(FoolishAsteroidsItems.FLASK.get());
            } else {
                if (!((Player) entityLiving).getAbilities().instabuild) {
                    stack.shrink(1);
                    ItemStack itemstack = new ItemStack(FoolishAsteroidsItems.FLASK.get());
                    Player playerEntity = (Player) entityLiving;
                    if (!playerEntity.getInventory().add(itemstack)) {
                        playerEntity.drop(itemstack, false);
                    }
                }
                return stack;
            }
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }


    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }
    public int getUseDuration(ItemStack p_43001_) {
        return ElixirConstants.DRINK_TIME;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
