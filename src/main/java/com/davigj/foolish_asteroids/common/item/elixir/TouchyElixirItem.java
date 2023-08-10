package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
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
import virtuoel.pehkui.api.ScaleTypes;

import java.util.logging.Logger;

public class TouchyElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(TouchyElixirItem.class.getName());

    public TouchyElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }

            float reach = ScaleTypes.REACH.getScaleData(entityLiving).getBaseScale();
            float defense = ScaleTypes.DEFENSE.getScaleData(entityLiving).getBaseScale();
            float hitboxHeight = ScaleTypes.HITBOX_HEIGHT.getScaleData(entityLiving).getBaseScale();
            float hitboxWidth = ScaleTypes.HITBOX_WIDTH.getScaleData(entityLiving).getBaseScale();
            float explosion = ScaleTypes.EXPLOSIONS.getScaleData(entityLiving).getBaseScale();

            if (defense > 0.2f) {
                ScaleTypes.DEFENSE.getScaleData(entityLiving).setTargetScale(reach - 0.1f);
            }
            if (reach < 6.0f) {
                ScaleTypes.REACH.getScaleData(entityLiving).setTargetScale(reach + 0.3f);
            }
            if (hitboxWidth < 5.0f) {
                ScaleTypes.HITBOX_WIDTH.getScaleData(entityLiving).setTargetScale(hitboxWidth + 0.25f);
            }
            if (hitboxHeight < 5.0f) {
                ScaleTypes.HITBOX_HEIGHT.getScaleData(entityLiving).setTargetScale(hitboxHeight + 0.25f);
            }
            if (explosion < 5.0f) {
                ScaleTypes.EXPLOSIONS.getScaleData(entityLiving).setTargetScale(explosion + 0.25f);
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
