package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.SoundSubRegistryHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

public class FoolishAsteroidsSounds {
    private static final SoundSubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getSoundSubHelper();

    public static final RegistryObject<SoundEvent> MUSHROOM = HELPER.createSoundEvent("item.mycological_elixir.mushroom");
    public static final RegistryObject<SoundEvent> WEIRD_MUSHROOM = HELPER.createSoundEvent("item.estranged_elixir.weird_mushroom");
    public static final RegistryObject<SoundEvent> HUSH = HELPER.createSoundEvent("item.profound_elixir.hush");
    public static final RegistryObject<SoundEvent> EARTHBOUND = HELPER.createSoundEvent("item.earthbound_elixir.earthbound");
}
