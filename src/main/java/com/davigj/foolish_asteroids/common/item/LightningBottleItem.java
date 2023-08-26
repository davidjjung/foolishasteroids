package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LightningBottleItem extends Item {
    public LightningBottleItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        assert player != null;
        ItemStack stack = player.getItemInHand(context.getHand());

        if (world.isClientSide()) {
            return InteractionResult.PASS;
        }
        if (!world.isClientSide) {
            // Calculate the position to spawn the lightning bolt
            double x = player.getX() + player.getLookAngle().x * 5.0;
            double y = player.getY();
            double z = player.getZ() + player.getLookAngle().z * 5.0;

            // Spawn the lightning bolt
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
            lightning.moveTo(x, y, z);
            world.addFreshEntity(lightning);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);

            if (stack.isEmpty()) {
                player.setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            } else {
                if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                    player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                }
            }
        }
        player.getCooldowns().addCooldown(this, 2 * 20);
        return InteractionResult.SUCCESS;
    }
}
