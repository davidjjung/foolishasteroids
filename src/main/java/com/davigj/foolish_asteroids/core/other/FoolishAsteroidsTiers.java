package com.davigj.foolish_asteroids.core.other;

import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.APItems;
import com.teamabnormals.blueprint.core.api.BlueprintArmorMaterial;
import com.teamabnormals.neapolitan.core.registry.NeapolitanSoundEvents;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.mehvahdjukaar.supplementaries.setup.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class FoolishAsteroidsTiers {
    public static final BlueprintArmorMaterial BUBBLE_BLOWER;
    public static final BlueprintArmorMaterial RETRO;
    public static final BlueprintArmorMaterial NOSTALGIC;
    public static final BlueprintArmorMaterial MOON;

    public FoolishAsteroidsTiers() {
    }

    static {
        BUBBLE_BLOWER = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "bubble_blower"), 0, new int[]{0, 0, 5, 0}, 0,
                ModSounds.BUBBLE_POP, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) ModRegistry.SOAP.get()});
        });
        RETRO = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "retro"), 0, new int[]{0, 0, 5, 0}, 5,
                ModSounds.SACK_BREAK, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) Items.WHITE_WOOL});
        });
        MOON = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "moon"), 0, new int[]{0, 0, 5, 0}, 5,
                NeapolitanSoundEvents.ITEM_ICE_CUBES_EAT, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) APBlocks.MOONSTONE.get()});
        });
        NOSTALGIC = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "nostalgic"), 0, new int[]{0, 0, 5, 0}, 0,
                ModSounds.SACK_BREAK, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) Items.PINK_DYE});
        });
    }
}