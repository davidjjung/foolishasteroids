package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.logging.Logger;

public class BenevolentElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(BenevolentElixirItem.class.getName());

    public BenevolentElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!(entityLiving instanceof Player player)) {
            return super.finishUsingItem(stack, world, entityLiving);
        }

        if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
        }

        MinecraftServer server = entityLiving.getLevel().getServer();
        ScaleType scaleHeight = ScaleTypes.HEIGHT;
        ScaleType scaleWidth = ScaleTypes.WIDTH;
        ScaleType scaleHealth = ScaleTypes.HEALTH;
        ScaleType scaleDefense = ScaleTypes.DEFENSE;

        float height = scaleHeight.getScaleData(entityLiving).getBaseScale();
        float width = scaleWidth.getScaleData(entityLiving).getBaseScale();
        float defense = scaleDefense.getScaleData(entityLiving).getBaseScale();
        boolean noEntities = true;
        int entities = 0;
        int entitiesTreated = 0;
        if (height > 0.05 && width > 0.05 && server != null) {
            for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving
                    .getBoundingBox().inflate(5.0D, 3.0D, 5.0D))) {
                if (!living.getUUID().equals(player.getUUID())) {
                    entities++;
                    entitiesTreated++;
                    noEntities = false;
                    if (entitiesTreated > 4) {
                        break;
                    }
                    float otherHeight = scaleHeight.getScaleData(living).getBaseScale();
                    float otherWidth = scaleWidth.getScaleData(living).getBaseScale();
                    float health = scaleHealth.getScaleData(living).getBaseScale();

                    if (entities > 0) {
                        float heightIncrement = 0.2f / entities;
                        float widthIncrement = 0.2f / entities;
                        float healthIncrement = 0.4f / entities;

                        if (otherHeight < 5.0) {
                            scaleHeight.getScaleData(living).setTargetScale(otherHeight + heightIncrement);
                        }
                        if (otherWidth < 5.0) {
                            scaleWidth.getScaleData(living).setTargetScale(otherWidth + widthIncrement);
                        }
                        if (health < 3.0) {
                            scaleHealth.getScaleData(living).setTargetScale(health + healthIncrement);
                        }
                    }
                }
            }
        } else {
            entityLiving.hurt(DamageSource.MAGIC, 1.0F);
        }
        TranslatableComponent message;
        if (noEntities) {
            message = new TranslatableComponent("message.benevolent.no_entities");
        } else {
            scaleHeight.getScaleData(entityLiving).setTargetScale(height / (1 + (entities * 0.025f)));
            scaleWidth.getScaleData(entityLiving).setTargetScale(width / (1 + (entities * 0.025f)));
            scaleDefense.getScaleData(entityLiving).setTargetScale(defense / (1 + (entities * 0.1f)));
            message = new TranslatableComponent("message.benevolent.user");
        }
        player.displayClientMessage(message, true);

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

    public int getUseDuration(ItemStack p_43001_) {
        return ElixirConstants.DRINK_TIME;
    }

    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }
    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }
}
