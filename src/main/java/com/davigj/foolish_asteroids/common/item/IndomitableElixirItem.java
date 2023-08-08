package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.teamabnormals.upgrade_aquatic.core.registry.UAMobEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID)
public class IndomitableElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(IndomitableElixirItem.class.getName());
    private static final int RAINBOW_DURATION = 160;
    public static final Map<UUID, Integer> rainbowTimers = new HashMap<>();

    public IndomitableElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }

            // Be invincible for eight seconds, have Speed II for eight seconds
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, RAINBOW_DURATION, 4));
            player.addEffect(new MobEffectInstance(UAMobEffects.REPELLENCE.get(), RAINBOW_DURATION, 4));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, RAINBOW_DURATION, 1));

            UUID playerId = player.getUUID();
            rainbowTimers.put(playerId, RAINBOW_DURATION); // Start the timer

            RainbowUtil.setRainbowType(entityLiving, 1);

            if (stack.isEmpty()) {
                return new ItemStack(FoolishAsteroidsItems.FLASK.get());
            } else {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    ItemStack itemstack = new ItemStack(FoolishAsteroidsItems.FLASK.get());
                    if (!player.getInventory().add(itemstack)) {
                        player.drop(itemstack, false);
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
