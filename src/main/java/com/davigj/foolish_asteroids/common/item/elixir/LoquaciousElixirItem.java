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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class LoquaciousElixirItem extends Item {

    private static final Logger LOGGER = Logger.getLogger(LoquaciousElixirItem.class.getName());

    public LoquaciousElixirItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            MinecraftServer server = entityLiving.getLevel().getServer();
            if (entityLiving instanceof ServerPlayer serverPlayerEntity) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
                serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
            }
            float atk = ScaleTypes.ATTACK.getScaleData(entityLiving).getBaseScale();

            if (atk < 4.0) {
                ScaleTypes.ATTACK.getScaleData(entityLiving).setTargetScale(atk + 0.2f);
                atk = ScaleTypes.ATTACK.getScaleData(entityLiving).getBaseScale();
            }
            if (server != null) {
                Random random = new Random();
                int prompt = random.nextInt(5);
                String announcement = "";
                switch (prompt) {
                    case 0 -> {
                        announcement = "Good day for a swell battle! ";
                    }
                    case 1 -> {
                        announcement = "This match will get red hot! ";
                    }
                    case 2 -> {
                        announcement = "Here's a real high-class bout! ";
                    }
                    case 3 -> {
                        announcement = "A great slam and then some! ";
                    }
                    case 4 -> {
                        announcement = "A brawl is surely brewing! ";
                    }
                }
                String result = announcement + player.getDisplayName().getString() + "'s attack is multiplied by a factor of " + String.format("%.2f", atk);
                TranslatableComponent message = new TranslatableComponent("message.loquacious.attribute", result);
                // Choose a random active player that isn't the player who consumed the elixir

                List<ServerPlayer> eligiblePlayers = new ArrayList<>(server.getPlayerList().getPlayers());
                eligiblePlayers.remove(player);

                if (!eligiblePlayers.isEmpty()) {
                    ServerPlayer randomPlayer = eligiblePlayers.get(new Random().nextInt(eligiblePlayers.size()));

                    // Display the message to the chosen player
                    randomPlayer.displayClientMessage(message, true);
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
