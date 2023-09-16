package com.davigj.foolish_asteroids.core.other;

import architectspalette.core.registry.APBlocks;
import com.starfish_studios.naturalist.registry.NaturalistSoundEvents;
import com.teamabnormals.blueprint.core.api.BlueprintArmorMaterial;
import com.teamabnormals.neapolitan.core.registry.NeapolitanSoundEvents;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.mehvahdjukaar.supplementaries.setup.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class FATiers {
    public static final BlueprintArmorMaterial BUBBLE_BLOWER;
    public static final BlueprintArmorMaterial RETRO;
    public static final BlueprintArmorMaterial NOSTALGIC;
    public static final BlueprintArmorMaterial MOON;
    public static final BlueprintArmorMaterial SNAKE;
    public static final BlueprintArmorMaterial BOUNDLESS;

    public FATiers() {
    }

    static {
        BUBBLE_BLOWER = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "bubble_blower"), 0, new int[]{0, 0, 0, 0}, 0,
                ModSounds.BUBBLE_POP, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) ModRegistry.SOAP.get()});
        });
        RETRO = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "retro"), 18, new int[]{0, 0, 0, 0}, 4,
                ModSounds.SACK_BREAK, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) Items.WHITE_WOOL});
        });
        MOON = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "moon"), 23, new int[]{1, 0, 0, 0}, 8,
                NeapolitanSoundEvents.ITEM_ICE_CUBES_EAT, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) APBlocks.MOONSTONE.get()});
        });
        BOUNDLESS = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "boundless"), 32, new int[]{2, 0, 0, 0}, 5,
                NeapolitanSoundEvents.ITEM_ICE_CUBES_EAT, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) APBlocks.MOONSTONE.get()});
        });
        NOSTALGIC = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "nostalgic"), 12, new int[]{0, 0, 0, 0}, 0,
                ModSounds.SACK_BREAK, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) Items.ROSE_BUSH});
        });
        SNAKE = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "snake"), 0, new int[]{0, 0, 0, 0}, 8,
                NaturalistSoundEvents.SNAKE_HISS, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) Items.CALCITE});
        });
    }
}