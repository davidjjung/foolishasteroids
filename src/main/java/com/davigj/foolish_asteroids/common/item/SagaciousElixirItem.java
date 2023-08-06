package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Random;
import java.util.logging.Logger;

public class SagaciousElixirItem extends Item {


    private static final Logger LOGGER = Logger.getLogger(SagaciousElixirItem.class.getName());

    public SagaciousElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            MinecraftServer server = entityLiving.getLevel().getServer();
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }

            if (server != null) {
                ScaleType scaleType = ScaleTypes.STEP_HEIGHT;
                Random random = new Random();
                String type = "";
                int attribute = random.nextInt(6);
                switch(attribute) {
                    case 0:
                        scaleType = ScaleTypes.STEP_HEIGHT;
                        type = "Your capacity to step up has been amplified by a factor of ";
                        break;
                    case 1:
                        scaleType = ScaleTypes.DEFENSE;
                        type = "Your ability to ward off damage has been amplified by a factor of ";
                        break;
                    case 2:
                        scaleType = ScaleTypes.MOTION;
                        type = "Your swiftness has been amplified by a factor of ";
                        break;
                    case 3:
                        scaleType = ScaleTypes.MINING_SPEED;
                        type = "Your ability to mine has been amplified by a factor of ";
                        break;
                    case 4:
                        scaleType = ScaleTypes.VISIBILITY;
                        type = "Your visibility has been amplified by a factor of ";
                        break;
                    case 5:
                        scaleType = ScaleTypes.BLOCK_REACH;
                        type = "Your ability to place blocks has been amplified by a factor of ";
                        break;
                    case 6:
                        scaleType = ScaleTypes.ENTITY_REACH;
                        type = "Your capacity to reach others has been amplified by a factor of ";
                        break;
                }
                ScaleData data = scaleType.getScaleData(entityLiving);
                String result = type + String.format("%.2f", data.getBaseScale());
                TranslatableComponent message = new TranslatableComponent("message.sagacious_elixir.attribute", result);

                player.displayClientMessage(message, true);
            }
            entityLiving.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60, 0, false, false));


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
