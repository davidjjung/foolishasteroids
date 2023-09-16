package com.davigj.foolish_asteroids.common.item.medal;

import com.davigj.foolish_asteroids.core.registry.FAItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InfectiousEmblemItem extends Item {
    public InfectiousEmblemItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        // TODO: Coin flip sound
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 2 * 20);
        player.getCooldowns().addCooldown(FAItems.BLANK_MEDALLION.get(), 20 * 20);
        if (!level.isClientSide) {
            morph(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void morph(Player player) {
        String morphToBe;
        BlockPos pos = player.blockPosition();
        String environment = Objects.requireNonNull(((Biome) player.getLevel().m_204166_(pos).m_203334_()).getRegistryName()).toString();
        environment = switch (environment) {
            case "minecraft:badlands", "minecraft:wooded_badlands", "minecraft:eroded_badlands",
                    "terralith:bryce_canyon", "terralith:red_oasis", "terralith:painted_mountains",
                    "minecraft:desert", "terralith:ancient_sands", "terralith:sandstone_canyon",
                    "terralith:desert_oasis", "terralith:desert_spires", "atmospheric:dunes",
                    "atmospheric:rocky_dunes", "atmospheric:petrified_dunes", "atmospheric:flourishing_dunes" -> "dry";
            default -> Objects.requireNonNull(((Biome) player.getLevel().m_204166_(pos).m_203334_()).getRegistryName()).toString();
        };
        if (player.isUnderWater()) {
            environment = "wet";
        }
        if (player.isInPowderSnow) {
            environment = "cold";
        }
        switch (environment) {
            case "dry" -> morphToBe = "minecraft:husk";
            case "cold" -> morphToBe = "rottencreatures:frostbitten";
            case "wet" -> morphToBe = "minecraft:drowned";
            default -> morphToBe = "minecraft:zombie";
        }

        String entityTag = player.getDisplayName().getString();
        String commandToExecute = "/morph " + entityTag + " " + morphToBe;
        CommandSourceStack commandSource = player.createCommandSourceStack();
        MinecraftServer server = commandSource.getServer();
        Commands commands = server.getCommands();
        commands.performCommand(commandSource, commandToExecute);
    }
}
