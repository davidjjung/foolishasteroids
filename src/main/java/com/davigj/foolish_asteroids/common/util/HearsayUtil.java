package com.davigj.foolish_asteroids.common.util;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class HearsayUtil {

    public static TranslatableComponent getDialogueLine(Player player) {
        // retrieve index and simplify
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        return conversations[manager.getValue(player, FoolishAsteroidsMod.CONVO_INDEX)][manager.getValue(player, FoolishAsteroidsMod.DIALOGUE_INDEX)];
    }

    public static final TranslatableComponent[][] conversations = {
            {
                    new TranslatableComponent("message.hearsay.00"),
                    new TranslatableComponent("message.hearsay.01")
            },
            {
                    new TranslatableComponent("message.hearsay.10"),
                    new TranslatableComponent("message.hearsay.11"),
                    new TranslatableComponent("message.hearsay.12")
            },
            {
                    new TranslatableComponent("message.hearsay.20"),
                    new TranslatableComponent("message.hearsay.21"),
                    new TranslatableComponent("message.hearsay.22")
            }
    };
}