package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.logging.Logger;

public class SeraphicElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(SeraphicElixirItem.class.getName());

    public SeraphicElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }

            if (!world.isClientSide && !TrackedDataManager.INSTANCE.getValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE)) {
                ScaleTypes.MOTION.getScaleData(player).setTargetScale(3.5f);
                Vec3 lookDirection = player.getLookAngle();
                // Calculate the direction based on the look direction
                Direction direction = getDirectionFromLookDirection(lookDirection, player);
                TrackedDataManager.INSTANCE.setValue(player, FoolishAsteroidsMod.SERAPHIC_ACTIVE, true);
                TranslatableComponent message = new TranslatableComponent("message.seraphic.command");
                player.displayClientMessage(message, true);
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

    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }
    public int getUseDuration(ItemStack p_43001_) {
        return ElixirConstants.DRINK_TIME;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
