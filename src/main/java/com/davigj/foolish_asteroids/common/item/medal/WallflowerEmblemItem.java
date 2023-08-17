package com.davigj.foolish_asteroids.common.item.medal;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class WallflowerEmblemItem extends Item {
    public WallflowerEmblemItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        // TODO: Coin flip sound
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 2 * 20);
        player.getCooldowns().addCooldown(FoolishAsteroidsItems.BLANK_MEDALLION.get(), 20 * 20);
        if (!level.isClientSide) {
            morph(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void morph(Player player) {
        String morphToBe;
        Random random = new Random();
        BlockPos pos = player.blockPosition();
        String biomeName = Objects.requireNonNull(((Biome) player.getLevel().m_204166_(pos).m_203334_()).getRegistryName()).toString();
        biomeName = switch (biomeName) {
            case "terralith:ice_marsh", "terralith:orchid_swamp", "wildbackport:mangrove_swamp", "minecraft:swamp", "environmental:marsh" ->
                    "swamp";
            case "environmental:blossom_woods", "environmental:blossom_valley", "minecraft:bamboo_jungle",
                    "terralith:sakura_grove", "terralith:sakura_valley" -> "bamboo";
            case "minecraft:beach", "minecraft:gravel_beach", "minecraft:stony_shores" -> "beach";
            case "minecraft:dripstone_caves", "terralith:stony_spires" -> "dripstone";
            case "terralith:andesite_caves", "terralith:granite_caves", "terralith:diorite_caves" -> "cave";
            case "minecraft:mushroom_fields", "terralith:fungal_caves" -> "mushroom";
            case "minecraft:badlands", "minecraft:wooded_badlands", "minecraft:eroded_badlands",
                    "terralith:bryce_canyon", "terralith:red_oasis",
                    "terralith:painted_mountains" -> "badlands";
            case "minecraft:windswept_hills", "minecraft:windswept_gravelly_hills", "terralith:haze_mountain",
                    "terralith:cloud_forest", "terralith:rocky_mountains",
                    "terralith:highlands", "terralith:steppe" -> "hills";
            case "minecraft:savanna", "minecraft:savanna_plateau", "minecraft:windswept_savanna",
                    "terralith:savanna_badlands", "terralith:fractured_savanna", "terralith:ashen_savanna",
                    "terralith:savanna_slopes", "terralith:shrubland" -> "savanna";
            case "terralith:jungle_mountains", "terralith:rocky_jungle", "minecraft:jungle",
                    "terralith:sparse_jungle", "terralith:amethyst_canyon", "terralith:amethyst_rainforest",
                    "atmospheric:rainforest", "atmospheric:sparse_rainforest", "atmospheric:rainforest_basin" -> "jungle";
            case "minecraft:taiga", "terralith:siberian_taiga", "minecraft:old_growth_pine_taiga",
                    "minecraft:old_growth_spruce_taiga", "terralith:yellowstone", "terralith:yosemite_cliffs",
                    "terralith:yosemite_lowlands", "terralith:cold_shrubland", "terralith:shield", "terralith:shield_clearing",
                    "terralith:rocky_shrubland" -> "spruce";
            case "minecraft:grove", "minecraft:snowy_slopes", "minecraft:snowy_peaks",
                    "minecraft:jagged_peaks", "minecraft:snowy_taiga", "terralith:wintry_forest",
                    "terralith:wintry_lowlands", "terralith:alpine_grove", "terralith:frozen_cliffs", "terralith:glacial_chasm",
                    "terralith:snowy_maple_forest" -> "snowy";
            case "minecraft:desert", "terralith:ancient_sands", "terralith:sandstone_canyon",
                    "terralith:desert_oasis", "terralith:desert_spires", "atmospheric:dunes",
                    "atmospheric:rocky_dunes", "atmospheric:petrified_dunes", "atmospheric:flourishing_dunes" -> "desert";
            case "minecraft:dark_forest", "terralith:moonlight_grove", "terralith:moonlight_valley" -> "dark_oak";
            default -> Objects.requireNonNull(((Biome) player.getLevel().m_204166_(pos).m_203334_()).getRegistryName()).toString();
        };

        switch (biomeName) {
            case "swamp" -> morphToBe = "creeperoverhaul:swamp_creeper";
            case "bamboo" -> morphToBe = "creeperoverhaul:bamboo_creeper";
            case "cave" -> morphToBe = "creeperoverhaul:cave_creeper";
            case "beach" -> morphToBe = "creeperoverhaul:beach_creeper";
            case "dripstone" -> morphToBe = "creeperoverhaul:dripstone_creeper";
            case "jungle" -> morphToBe = "creeperoverhaul:jungle_creeper";
            case "savanna" -> morphToBe = "creeperoverhaul:savannah_creeper";
            case "spruce" -> morphToBe = "creeperoverhaul:spruce_creeper";
            case "snowy" -> morphToBe = "creeperoverhaul:snowy_creeper";
            case "hills" -> morphToBe = "creeperoverhaul:hills_creeper";
            case "dark_oak" -> morphToBe = "creeperoverhaul:dark_oak_creeper";
            case "desert" -> morphToBe = "creeperoverhaul:desert_creeper";
            default -> morphToBe = "minecraft:creeper";
        }
        String entityTag = player.getDisplayName().getString();
        String commandToExecute = "/morph " + entityTag + " " + morphToBe;
        CommandSourceStack commandSource = player.createCommandSourceStack();
        MinecraftServer server = commandSource.getServer();
        Commands commands = server.getCommands();
        commands.performCommand(commandSource, commandToExecute);
    }
}
