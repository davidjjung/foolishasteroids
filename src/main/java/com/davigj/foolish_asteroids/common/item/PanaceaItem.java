package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.HashSet;
import java.util.Set;

import static com.davigj.foolish_asteroids.common.item.elixir.HearsayElixirItem.oracleMap;
import static com.davigj.foolish_asteroids.common.item.elixir.IncendiaryElixirItem.smokingPlayers;
import static com.davigj.foolish_asteroids.common.item.elixir.IndomitableElixirItem.rainbowTimers;

public class PanaceaItem extends Item {

    public PanaceaItem(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            this.affectConsumer(consumer);
        }
        return this.isEdible() ? consumer.eat(level, stack) : stack;
    }

    public void affectConsumer(LivingEntity entityLiving) {
        if (entityLiving instanceof  Player player) {
            TrackedDataManager manager = TrackedDataManager.INSTANCE;
            String entityTag = player.getDisplayName().getString();
            smokingPlayers.remove(player.getUUID());
            rainbowTimers.remove(player.getUUID());
            oracleMap.remove(player.getUUID());
            if (manager.getValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL) != 0) {
                manager.setValue(player, FoolishAsteroidsMod.HIGHWAY_TO_HELL, 0);
                TranslatableComponent message = new TranslatableComponent("message.hearsay.forgiveness");
                player.displayClientMessage(message, true);
            } else if (manager.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE)) {
                manager.setValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE, false);
            }
            manager.setValue(player, FoolishAsteroidsMod.STORED_ELECTRONS, 0);
            manager.setValue(player, FoolishAsteroidsMod.RAD_POISONING, false);
            manager.setValue(player, FoolishAsteroidsMod.AUTUMNAL, false);
            manager.setValue(player, FoolishAsteroidsMod.ANTI_DRUNK, 0);

            if (MorphUtilHelper.isPlayerMorphed(player)) {
                String commandToExecute = "/demorph " + entityTag;
                CommandSourceStack commandSource = player.createCommandSourceStack();
                MinecraftServer server = commandSource.getServer();
                if (server != null) {
                    Commands commands = server.getCommands();
                    if (commands != null) {
                        commands.performCommand(commandSource, commandToExecute);
                    }
                }
            }
        }
        for (ScaleType scaleType : SCALE_TYPES) {
            scaleType.getScaleData(entityLiving).setTargetScale(1.0F);
        }
        entityLiving.removeAllEffects();
    }

    private static final Set<ScaleType> SCALE_TYPES = new HashSet<>();
    static {
        SCALE_TYPES.add(ScaleTypes.ENTITY_REACH);
        SCALE_TYPES.add(ScaleTypes.FALLING);
        SCALE_TYPES.add(ScaleTypes.BASE);
        SCALE_TYPES.add(ScaleTypes.HELD_ITEM);
        SCALE_TYPES.add(ScaleTypes.DEFENSE);
        SCALE_TYPES.add(ScaleTypes.ATTACK);
        SCALE_TYPES.add(ScaleTypes.ATTACK_SPEED);
        SCALE_TYPES.add(ScaleTypes.BLOCK_REACH);
        SCALE_TYPES.add(ScaleTypes.DROPS);
        SCALE_TYPES.add(ScaleTypes.EXPLOSIONS);
        SCALE_TYPES.add(ScaleTypes.EYE_HEIGHT);
        SCALE_TYPES.add(ScaleTypes.KNOCKBACK);
        SCALE_TYPES.add(ScaleTypes.MINING_SPEED);
        SCALE_TYPES.add(ScaleTypes.HEALTH);
        SCALE_TYPES.add(ScaleTypes.THIRD_PERSON);
        SCALE_TYPES.add(ScaleTypes.JUMP_HEIGHT);
        SCALE_TYPES.add(ScaleTypes.VIEW_BOBBING);
        SCALE_TYPES.add(ScaleTypes.VISIBILITY);
        SCALE_TYPES.add(ScaleTypes.HEIGHT);
        SCALE_TYPES.add(ScaleTypes.WIDTH);
        SCALE_TYPES.add(ScaleTypes.HITBOX_HEIGHT);
        SCALE_TYPES.add(ScaleTypes.HITBOX_WIDTH);
        SCALE_TYPES.add(ScaleTypes.STEP_HEIGHT);
        SCALE_TYPES.add(ScaleTypes.PROJECTILES);
        SCALE_TYPES.add(ScaleTypes.REACH);
    }
}
