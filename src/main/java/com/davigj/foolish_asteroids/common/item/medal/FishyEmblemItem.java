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

public class FishyEmblemItem extends Item {


    public FishyEmblemItem(Properties properties) {
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
        String morphToBe = "minecraft:cod";
        Random random = new Random();
        BlockPos pos = player.blockPosition();
        String biomeName = Objects.requireNonNull(((Biome) player.getLevel().m_204166_(pos).m_203334_()).getRegistryName()).toString();
        if (biomeName.equals("terralith:ice_marsh") || biomeName.equals("terralith:orchid_swamp") ||
                biomeName.equals("wildbackport:mangrove_swamp") || biomeName.equals("minecraft:swamp") ||
                biomeName.equals("environmental:marsh")) {
            biomeName = "swamp";
        } else if (biomeName.equals("environmental:blossom_woods") || biomeName.equals("environmental:blossom_valley")) {
            biomeName = "blossom";
        }

        switch (biomeName) {
            case "swamp" -> {
                switch(MorphUtilHelper.playerCurrentMorph(player)) {
                    case "entity.alexsmobs.catfish" -> morphToBe = "upgrade_aquatic:perch";
                    case "entity.upgrade_aquatic.perch" -> morphToBe = "upgrade_aquatic:pike";
                    case "entity.upgrade_aquatic.pike" -> morphToBe = "alexsmobs:catfish";
                    default -> {
                        // Randomly select one of the four options
                        int randomIndex = random.nextInt(3);
                        switch (randomIndex) {
                            case 0 -> morphToBe = "alexsmobs:catfish";
                            case 1 -> morphToBe = "upgrade_aquatic:pike";
                            case 2 -> morphToBe = "upgrade_aquatic:perch";
                        }
                    }
                }
            }
            case "blossom" -> morphToBe = "environmental:koi";
            default -> {
                switch(MorphUtilHelper.playerCurrentMorph(player)) {
                    case "entity.minecraft.cod" -> morphToBe = "minecraft:salmon";
                    case "entity.minecraft.salmon" -> morphToBe = "minecraft:cod";
                    default -> {
                        if (random.nextBoolean()) {
                            morphToBe = "minecraft:cod";
                        } else {
                            morphToBe = "minecraft:salmon";
                        }
                    }
                }
            }
        }
        String entityTag = player.getDisplayName().getString();
        String commandToExecute = "/morph " + entityTag + " " + morphToBe;
        CommandSourceStack commandSource = player.createCommandSourceStack();
        MinecraftServer server = commandSource.getServer();
        Commands commands = server.getCommands();
        commands.performCommand(commandSource, commandToExecute);
    }
}
