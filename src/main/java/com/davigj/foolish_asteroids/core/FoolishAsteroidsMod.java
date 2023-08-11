package com.davigj.foolish_asteroids.core;

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

	public static final TrackedData<Integer> CONVO_INDEX = TrackedData.Builder.create(DataProcessors.INT, () -> (int) 1).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> DIALOGUE_INDEX = TrackedData.Builder.create(DataProcessors.INT, () -> (int) 1).enableSaving().enablePersistence().build();
	public static final TrackedData<Boolean> HEARSAY_ACTIVE = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> HIGHWAY_TO_HELL = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();


	public FoolishAsteroidsMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "convo_index"), CONVO_INDEX);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "dialogue_index"), DIALOGUE_INDEX);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "hearsay_active"), HEARSAY_ACTIVE);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "highway_to_hell"), HIGHWAY_TO_HELL);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void dataSetup(GatherDataEvent event) {

	}
}