package com.davigj.foolish_asteroids.common.item.medal;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlankMedallionItem extends Item {

    public BlankMedallionItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        // TODO: Coin unflip sound
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 30 * 20);
        if (!level.isClientSide) {
            demorph(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void demorph(Player player) {
        String entityTag = player.getDisplayName().getString();
        String commandToExecute = "/demorph " + entityTag;
        CommandSourceStack commandSource = player.createCommandSourceStack();
        MinecraftServer server = commandSource.getServer();
        Commands commands = server.getCommands();
        if (commands != null) {
            commands.performCommand(commandSource, commandToExecute);
        }
    }
}
