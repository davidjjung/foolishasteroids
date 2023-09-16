package com.davigj.foolish_asteroids.core;

import com.davigj.foolish_asteroids.core.other.FACompat;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.davigj.foolish_asteroids.core.registry.FAMobEffects;
import com.davigj.foolish_asteroids.core.registry.FAParticleTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(FoolishAsteroidsMod.MOD_ID)
public class FoolishAsteroidsMod {
	public static final String MOD_ID = "foolish_asteroids";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public FoolishAsteroidsMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		FAParticleTypes.PARTICLE_TYPES.register(bus);
		FAMobEffects.EFFECTS.register(bus);
		FAMobEffects.POTIONS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		FADataProcessors.registerTrackedData();
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FACompat.registerCompat();
			FAMobEffects.registerBrewingRecipes();
//			PickupUtil.registerProjectileTypes();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void dataSetup(GatherDataEvent event) {

	}
}