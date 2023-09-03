package com.davigj.foolish_asteroids.core.other;

import com.davigj.foolish_asteroids.core.registry.FoolishAsteroidsItems;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.neapolitan.common.entity.projectile.BananaPeel;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class FoolishAsteroidsCompat {

    public static void registerCompat() {
        registerCompostables();
        registerFlammables();
        registerDispenserBehaviors();
    }

    private static void registerCompostables() {
        DataUtil.registerCompostable(FoolishAsteroidsItems.BANANA_PEEL.get(), 0.6F);
        DataUtil.registerCompostable(FoolishAsteroidsItems.ZOMBIE_BRAIN.get(), 0.4F);
    }

    private static void registerFlammables() {
    }

    private static void registerDispenserBehaviors() {
    }
}
