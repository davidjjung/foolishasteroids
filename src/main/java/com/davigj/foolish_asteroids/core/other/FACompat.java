package com.davigj.foolish_asteroids.core.other;

import com.davigj.foolish_asteroids.core.registry.FAItems;
import com.teamabnormals.blueprint.core.util.DataUtil;

public class FACompat {

    public static void registerCompat() {
        registerCompostables();
        registerFlammables();
        registerDispenserBehaviors();
    }

    private static void registerCompostables() {
        DataUtil.registerCompostable(FAItems.BANANA_PEEL.get(), 0.6F);
        DataUtil.registerCompostable(FAItems.ZOMBIE_BRAIN.get(), 0.4F);
    }

    private static void registerFlammables() {
    }

    private static void registerDispenserBehaviors() {
    }
}
