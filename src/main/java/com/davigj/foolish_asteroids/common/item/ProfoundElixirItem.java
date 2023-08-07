package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.ElixirConstants;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ProfoundElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(SagaciousElixirItem.class.getName());
    public static final Map<ServerPlayer, Long> chatDisableMap = new HashMap<>();
    private static final long silenceDuration = 15000L;

    public ProfoundElixirItem(Properties properties) {
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
                for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving.getBoundingBox().inflate(4.0D, 3.0D, 4.0D))) {
                    if (!living.getUUID().equals(player.getUUID())) {
                        if (living instanceof Player) {
                            chatDisableMap.put((ServerPlayer) living, System.currentTimeMillis() + silenceDuration);
                            TranslatableComponent message = new TranslatableComponent("message.profound.victim");
                            ((Player) living).displayClientMessage(message, true);
                        }
                    } else {
                        TranslatableComponent message = new TranslatableComponent("message.profound.used");
                        ((Player) living).displayClientMessage(message, true);
                    }
                }
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
