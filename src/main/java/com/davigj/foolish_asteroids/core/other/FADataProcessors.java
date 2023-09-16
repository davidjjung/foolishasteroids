package com.davigj.foolish_asteroids.core.other;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.registry.FAItems;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.IDataProcessor;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedList;
import java.util.Queue;

import static com.davigj.foolish_asteroids.core.FoolishAsteroidsMod.MOD_ID;

public class FADataProcessors {
    public static final IDataProcessor<Queue<ItemStack>> TEETH_PROCESSOR = new IDataProcessor<>() {
        @Override
        public CompoundTag write(Queue<ItemStack> teethQueue) {
            CompoundTag compound = new CompoundTag();
            ListTag teethList = new ListTag();
            teethQueue.forEach(teethStack -> {
                CompoundTag teethTag = teethStack.save(new CompoundTag());
                teethList.add(teethTag);
            });
            compound.put("Teeth", teethList);
            return compound;
        }

        @Override
        public Queue<ItemStack> read(CompoundTag compound) {
            Queue<ItemStack> teethQueue = new LinkedList<>();
            ListTag teethList = compound.getList("Teeth", 10);
            teethList.forEach(teethNBT -> {
                CompoundTag teethTag = (CompoundTag) teethNBT;
                ItemStack teethStack = ItemStack.of(teethTag);
                teethQueue.offer(teethStack);
            });
            return teethQueue;
        }
    };

    public static final TrackedData<Queue<ItemStack>> TEETH_DATA = TrackedData.Builder.create(TEETH_PROCESSOR, () -> {
        LinkedList<ItemStack> initialTeeth = new LinkedList<>();
        initialTeeth.add(new ItemStack(FAItems.TOOTH.get()));  // Add the desired ItemStacks here
        initialTeeth.add(new ItemStack(FAItems.TOOTH.get()));
        initialTeeth.add(new ItemStack(FAItems.TOOTH.get()));
        return initialTeeth;
    }).build();

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
    public static final TrackedData<Integer> BONE_SERPENT_TEETH = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
    public static final TrackedData<Integer> THRASHER_TEETH = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();

    public static void registerTrackedData() {
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

        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "teeth_data"), TEETH_DATA);
    }


}
