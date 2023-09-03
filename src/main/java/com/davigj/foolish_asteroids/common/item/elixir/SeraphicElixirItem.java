package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.api.ScaleTypes;

public class SeraphicElixirItem extends ElixirItem {
    public SeraphicElixirItem(Properties properties) {
        super(properties);
    }

    private Direction getDirectionFromLookDirection(Vec3 lookDirection, Player player) {
        double horizontalAngle = Math.atan2(lookDirection.z, lookDirection.x);
        double degree = Math.toDegrees(horizontalAngle);
        degree = (degree + 360) % 360;
        if (degree >= 45 && degree < 135) {
            TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.SERAPHIC_DIR, 0);
            return Direction.SOUTH;
        } else if (degree >= 135 && degree < 225) {
            TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.SERAPHIC_DIR, 1);
            return Direction.WEST;
        } else if (degree >= 225 && degree < 315) {
            TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.SERAPHIC_DIR, 2);
            return Direction.NORTH;
        } else {
            TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.SERAPHIC_DIR, 3);
            return Direction.EAST;
        }
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        if (!TrackedDataManager.INSTANCE.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE)){
            ScaleTypes.MOTION.getScaleData(player).setTargetScale(3.5f);
            Vec3 lookDirection = player.getLookAngle();
            // Calculate the direction based on the look direction
            getDirectionFromLookDirection(lookDirection, player);
            TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE, true);
            TranslatableComponent message = new TranslatableComponent("message.seraphic.command");
            player.displayClientMessage(message, true);
        } else {
            TranslatableComponent message = new TranslatableComponent("message.seraphic.refusal");
            player.displayClientMessage(message, true);
        }
    }
}
