package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.MorphUtilHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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

public class PanaceaItem extends Item {

    public PanaceaItem(Properties properties) {
        super(properties);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {

            this.affectConsumer(stack, level, consumer);
        }
        return this.isEdible() ? consumer.eat(level, stack) : stack;
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        if (entityLiving instanceof  Player player) {
            String entityTag = player.getDisplayName().getString();
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
