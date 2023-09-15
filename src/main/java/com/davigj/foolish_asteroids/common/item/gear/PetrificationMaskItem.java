package com.davigj.foolish_asteroids.common.item.gear;

import com.davigj.foolish_asteroids.common.util.Constants;
import com.starfish_studios.naturalist.entity.Snake;
import com.starfish_studios.naturalist.registry.NaturalistEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

public class PetrificationMaskItem extends ArmorItem {
    private static final String NBT_SNAKES = "Snakes";

    public PetrificationMaskItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
        ItemStack itemstack1 = player.getItemBySlot(equipmentslot);

        float reachDistance = 5 * ScaleTypes.REACH.getScaleData(player).getBaseScale() * ScaleTypes.ENTITY_REACH.getScaleData(player).getBaseScale();
        Vec3 lookVector = player.getLookAngle();
        List<Entity> entities = level.getEntities((Entity) null, player.getBoundingBox().expandTowards(
                lookVector.x * reachDistance, lookVector.y * reachDistance, lookVector.z * reachDistance).inflate(1.0D));
        for (Entity targetEntity : entities) {
            if (targetEntity instanceof Snake) {
                return InteractionResultHolder.fail(itemstack);
            }
        }

        if (itemstack1.isEmpty()) {
            player.setItemSlot(equipmentslot, itemstack.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }
            // TODO: get stack snake size, if it doesn't exceed max then this fails when used in vicinity of snek

            itemstack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemstack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.isClientSide && itemstack.hasTag()) {
            assert itemstack.getTag() != null;
            if (itemstack.getTag().contains(NBT_SNAKES)) {
                ListTag snakesTag = itemstack.getTag().getList(NBT_SNAKES, 10);

                if (!snakesTag.isEmpty()) {
                    CompoundTag lastSnakeData = (CompoundTag) snakesTag.remove(snakesTag.size() - 1);

                    itemstack.getTag().put(NBT_SNAKES, snakesTag);
                    String entityTypeName = lastSnakeData.getString("EntityType");
                    EntityType<?> entityType = Registry.ENTITY_TYPE.get(new ResourceLocation(entityTypeName));

                    Entity entity = entityType.create(level);
                    if (entity instanceof Snake) {
                        entity.load(lastSnakeData); // Load the snake's data
                        entity.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                        level.addFreshEntity(entity);
                        return InteractionResult.SUCCESS;
                    }

                    return InteractionResult.PASS;
                }
            }
        }

        return super.useOn(context);
    }
}
