package com.davigj.foolish_asteroids.common.item;

import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import net.mehvahdjukaar.supplementaries.common.items.BubbleBlower;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.mehvahdjukaar.supplementaries.setup.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.davigj.foolish_asteroids.common.util.Constants.MAX_SOAPINESS;

public class BubbleBootsItem extends ArmorItem {
    // TODO: dissociate soapiness from durability entirely. make it a separate nbt data that still determines the bar
    private static final String SOAPINESS = "Soapiness";


    public BubbleBootsItem(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builder) {
        super(materialIn, slot, builder);
        this.setDefaultSoapiness(this.getDefaultInstance());
    }

    public void onArmorTick(ItemStack stack, Level world, Player player) {
        CompoundTag tag = stack.getOrCreateTag();
        int soapiness = tag.getInt(SOAPINESS);
        BlockPos bubblePos = player.blockPosition().below();
        if (world.getBlockState(bubblePos).isAir() && player.tickCount % 2 == 0 && soapiness > 0) {
            if (!world.isClientSide) {
                world.setBlockAndUpdate(bubblePos, ModRegistry.BUBBLE_BLOCK.get().defaultBlockState());
                tag.putInt(SOAPINESS, soapiness - 1);
            }
        } else if (world.getBlockState(bubblePos).is(ModRegistry.SOAP_BLOCK.get()) && soapiness != MAX_SOAPINESS) {
            world.playSound((Player) player, player, ModSounds.BUBBLE_PLACE.get(), SoundSource.NEUTRAL, 1.0F, 3.0F);
            this.setDefaultSoapiness(stack);
        }
    }

    public int getBarWidth(ItemStack stack) {
        int soapiness = stack.getOrCreateTag().getInt(SOAPINESS);
        return Math.round(13.0F - (float) (MAX_SOAPINESS - soapiness) * 13.0F / MAX_SOAPINESS);
    }

    public boolean isBarVisible(ItemStack stack) {
        int soapiness = stack.getOrCreateTag().getInt(SOAPINESS);
        return soapiness > 0;
    }

    public int getBarColor(ItemStack stack) {
        return 15246564;
    }

    private void setDefaultSoapiness(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(SOAPINESS, MAX_SOAPINESS);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int soapiness = stack.getOrCreateTag().getInt(SOAPINESS);
        if (soapiness != 0) {
            tooltip.add(new TranslatableComponent("message.supplementaries.bubble_blower_tooltip", new Object[]{soapiness, MAX_SOAPINESS}));
        }
    }
}
