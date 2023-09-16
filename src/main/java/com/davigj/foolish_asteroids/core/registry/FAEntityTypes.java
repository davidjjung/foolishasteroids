package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAEntityTypes {
    public static final EntitySubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getEntitySubHelper();
}