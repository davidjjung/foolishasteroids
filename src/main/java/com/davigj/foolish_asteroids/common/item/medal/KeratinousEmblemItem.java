package com.davigj.foolish_asteroids.common.item.medal;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import com.davigj.foolish_asteroids.core.registry.FAItems;
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

import java.util.Random;

public class KeratinousEmblemItem extends Item {


    public KeratinousEmblemItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        // TODO: Coin flip sound
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 10 * 20);
        player.getCooldowns().addCooldown(FAItems.BLANK_MEDALLION.get(), 20 * 20);
        if (!level.isClientSide) {
            morph(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void morph(Player player) {
        String morphToBe = "minecraft:turtle";
        switch (MorphUtilHelper.playerCurrentMorph(player)) {
            case "entity.minecraft.turtle" -> morphToBe = "alexsmobs:terrapin";
            case "entity.alexsmobs.terrapin" -> morphToBe = "sullysmod:tortoise";
            case "entity.sullysmod.tortoise" -> morphToBe = "minecraft:turtle";
            default -> {
                // Randomly select one of the four options
                Random random = new Random();
                int randomIndex = random.nextInt(3);
                switch (randomIndex) {
                    case 0 -> morphToBe = "minecraft:turtle";
                    case 1 -> morphToBe = "sullysmod:tortoise";
                    case 2 -> morphToBe = "alexsmobs:terrapin";
                }
            }
        }

        String entityTag = player.getDisplayName().getString();
        String commandToExecute = "/morph " + entityTag + " " + morphToBe;
        CommandSourceStack commandSource = player.createCommandSourceStack();
        MinecraftServer server = commandSource.getServer();
        Commands commands = server.getCommands();
        if (commands != null) {
            commands.performCommand(commandSource, commandToExecute);
        }
    }
}
