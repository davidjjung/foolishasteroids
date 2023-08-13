package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.logging.Logger;

public class TenebrousElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(EndemicElixirItem.class.getName());

    public TenebrousElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
            ScaleData data = ScaleTypes.HEALTH.getScaleData(entityLiving);
            if (data.getBaseScale() > 0.1f) {
                for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
                    if (!living.getUUID().equals(player.getUUID())) {
                        living.addEffect(new MobEffectInstance(AMEffectRegistry.POWER_DOWN, 200, 0, false, false));
                        living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, false, false));
                        if (living instanceof Player) {
                            TranslatableComponent message = new TranslatableComponent("message.tenebrous.look_behind");
                            ((Player) living).displayClientMessage(message, true);
                        }
                    } else {
                        TranslatableComponent message = new TranslatableComponent("message.tenebrous.user");
                        ((Player) living).displayClientMessage(message, true);
                        data.setTargetScale(data.getBaseScale() - 0.1f);
                        living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, false));
                    }
                }
            } else {
                TranslatableComponent message = new TranslatableComponent("message.tenebrous.insufficient");
                entityLiving.hurt(DamageSource.OUT_OF_WORLD, 1.0f);
                ((Player) entityLiving).displayClientMessage(message, true);
            }
            entityLiving.hurt(DamageSource.OUT_OF_WORLD, 0.05f);

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
