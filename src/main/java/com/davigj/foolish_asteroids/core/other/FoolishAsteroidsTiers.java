package com.davigj.foolish_asteroids.core.other;

import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnitySoundEvents;
import com.teamabnormals.blueprint.core.api.BlueprintArmorMaterial;
import net.mehvahdjukaar.supplementaries.setup.ModRegistry;
import net.mehvahdjukaar.supplementaries.setup.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class FoolishAsteroidsTiers {
    public static final BlueprintArmorMaterial BUBBLE_BLOWER;

    public FoolishAsteroidsTiers() {
    }

    static {
        BUBBLE_BLOWER = new BlueprintArmorMaterial(new ResourceLocation(
                "foolish_asteroids", "bubble_blower"), 23, new int[]{0, 0, 5, 0}, 3,
                ModSounds.BUBBLE_POP, 0.0F, 0.0F, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) ModRegistry.SOAP.get()});
        });
    }
}