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

public class ProprietaryEmblemItem extends Item {


    public ProprietaryEmblemItem(Properties properties) {
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
        String morphToBe = "minecraft:armor_stand";
        switch (MorphUtilHelper.playerCurrentMorph(player)) {
            case "entity.minecraft.armor_stand" -> morphToBe = "dummmmmmy.target_dummy";
            case "entity.dummmmmmy.target_dummy" -> morphToBe = "mannequins:mannequin";
            case "entity.mannequins.mannequin" -> morphToBe = "mannequins:statue";
            case "entity.mannequins.statue" -> morphToBe = "minecraft:armor_stand";
            default -> {
                Random random = new Random();
                int randomIndex = random.nextInt(4);
                switch (randomIndex) {
                    case 0 -> morphToBe = "minecraft:armor_stand";
                    case 1 -> morphToBe = "dummmmmmy:target_dummy";
                    case 2 -> morphToBe = "mannequins:mannequin";
                    case 3 -> morphToBe = "mannequins:statue";
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
