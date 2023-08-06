package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;
import java.util.logging.Logger;

public class ClaustrophilicElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(ClaustrophilicElixirItem.class.getName());

    public ClaustrophilicElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }

            float reach = ScaleTypes.REACH.getScaleData(entityLiving).getBaseScale();
            float miningSpeed = ScaleTypes.MINING_SPEED.getScaleData(entityLiving).getBaseScale();
            float jumpHeight = ScaleTypes.JUMP_HEIGHT.getScaleData(entityLiving).getBaseScale();
            float health = ScaleTypes.HEALTH.getScaleData(entityLiving).getBaseScale();

            if (reach > 0.2f) {
                ScaleTypes.REACH.getScaleData(entityLiving).setTargetScale(reach - 0.1f);
            }
            if (miningSpeed > 0.2f) {
                ScaleTypes.MINING_SPEED.getScaleData(entityLiving).setTargetScale(miningSpeed - 0.1f);
            }
            if (jumpHeight > 0.2f) {
                ScaleTypes.JUMP_HEIGHT.getScaleData(entityLiving).setTargetScale(jumpHeight - 0.1f);
            }
            if (health < 5.0f) {
                ScaleTypes.HEALTH.getScaleData(entityLiving).setTargetScale(health + 1.0f);
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


    public int getUseDuration(ItemStack p_43001_) {
        return ElixirConstants.DRINK_TIME;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
