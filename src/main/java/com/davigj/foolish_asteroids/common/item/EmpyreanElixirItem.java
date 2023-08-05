package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.logging.Logger;

public class EmpyreanElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(EmpyreanElixirItem.class.getName());

    public EmpyreanElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
            // Get the playertag of the entity that used the item
            String entityTag = player.getDisplayName().getString();
            // Construct the command with the playertag
            String commandToExecute = "/tp " + entityTag + " ~ 256 ~";
            // Run the command silently without any output in the chat or server log
            CommandSourceStack commandSource = player.createCommandSourceStack();
            // Ensure commandSource.getServer() and commandSource.getServer().getCommands() are not null
            MinecraftServer server = commandSource.getServer();
            if (server != null) {
                Commands commands = server.getCommands();
                if (commands != null) {
                    commands.performCommand(commandSource, commandToExecute);
                } else {
                    LOGGER.warning("Command instance is null.");
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


    public int getUseDuration(ItemStack p_43001_) {
        return 44;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
