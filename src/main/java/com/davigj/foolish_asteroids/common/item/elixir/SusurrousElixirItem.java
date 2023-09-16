package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SusurrousElixirItem extends ElixirItem{
    public SusurrousElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        // TODO: Play a magical autumnal breeze sfx on consumption
        TrackedDataManager.INSTANCE.setValue(entityLiving, FADataProcessors.AUTUMNAL, true);
    }

}
