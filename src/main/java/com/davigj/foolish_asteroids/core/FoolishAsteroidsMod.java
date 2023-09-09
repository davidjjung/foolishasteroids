package com.davigj.foolish_asteroids.core;

import com.davigj.foolish_asteroids.core.other.FoolishAsteroidsCompat;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsMobEffects;
import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsParticleTypes;
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
	public static final TrackedData<Boolean> SERAPHIC_ACTIVE = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().enablePersistence().build();
	public static final TrackedData<Boolean> RAD_POISONING = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().enablePersistence().build();
	public static final TrackedData<Boolean> AUTUMNAL = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> HIGHWAY_TO_HELL = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> ANTI_DRUNK = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> SERAPHIC_DIR = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> STORED_ELECTRONS = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> BLUSTER_RECHARGE = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> BLUSTER_HARVEST = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> TONGUES = TrackedData.Builder.create(DataProcessors.INT, () -> 4).enableSaving().enablePersistence().build();
	public static final TrackedData<Integer> TEETH = TrackedData.Builder.create(DataProcessors.INT, () -> 3).enableSaving().enablePersistence().build();


	public FoolishAsteroidsMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		FoolishAsteroidsParticleTypes.PARTICLE_TYPES.register(bus);
		FoolishAsteroidsMobEffects.EFFECTS.register(bus);
		FoolishAsteroidsMobEffects.POTIONS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "convo_index"), CONVO_INDEX);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "dialogue_index"), DIALOGUE_INDEX);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "hearsay_active"), HEARSAY_ACTIVE);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "highway_to_hell"), HIGHWAY_TO_HELL);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "anti_drunk"), ANTI_DRUNK);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "seraphic_active"), SERAPHIC_ACTIVE);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "seraphic_direction"), SERAPHIC_DIR);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "stored_electrons"), STORED_ELECTRONS);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "rad_poisoning"), RAD_POISONING);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "autumnal"), AUTUMNAL);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "bluster_recharge"), BLUSTER_RECHARGE);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "bluster_harvest"), BLUSTER_HARVEST);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "tongues"), TONGUES);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "teeth"), TEETH);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			FoolishAsteroidsCompat.registerCompat();
			FoolishAsteroidsMobEffects.registerBrewingRecipes();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {

		});
	}

	private void dataSetup(GatherDataEvent event) {

	}
}