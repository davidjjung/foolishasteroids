package com.davigj.foolish_asteroids.common.item;

import com.davigj.foolish_asteroids.common.util.Constants;
import com.starfish_studios.naturalist.entity.Snake;
import com.starfish_studios.naturalist.registry.NaturalistEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PetrificationMaskItem extends ArmorItem {
    private static final String NBT_SNAKES = "Snakes";

    public PetrificationMaskItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
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

                    if (entityType != null) {
                        Entity entity = entityType.create(level);
                        if (entity instanceof Snake) {
                            entity.load(lastSnakeData); // Load the snake's data
                            entity.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                            level.addFreshEntity(entity);
                        }
                    }

                    return InteractionResult.PASS;
                }
            }
        }

        return super.useOn(context);
    }
}
