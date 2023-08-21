package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolishAsteroidsEntityTypes {
    public static final EntitySubRegistryHelper HELPER = FoolishAsteroidsMod.REGISTRY_HELPER.getEntitySubHelper();
}