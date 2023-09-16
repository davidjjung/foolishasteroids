package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class AtomizedElixirItem extends ElixirItem {

    public AtomizedElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player) entityLiving;
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        int electrons = manager.getValue(player, FADataProcessors.STORED_ELECTRONS);
        int stability = (int) ((player.experienceLevel * 0.3) + 2);
        // 0 lvls -> 2 electrons, 10 lvls -> 5 electrons, 20 lvls -> 8 electrons, etc.
        if (electrons < stability) {
            manager.setValue(player, FADataProcessors.STORED_ELECTRONS, electrons + 1);
        } else {
            Random random = new Random();
            TranslatableComponent message;
            if (random.nextInt(10) < 3) {
                manager.setValue(player, FADataProcessors.STORED_ELECTRONS, electrons + 1);
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0));
                message = new TranslatableComponent("message.atomized.risky");
            } else {
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
                manager.setValue(player, FADataProcessors.RAD_POISONING, true);
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                message = new TranslatableComponent("message.atomized.poisoned");
            }
            player.displayClientMessage(message, true);
        }
    }
}
