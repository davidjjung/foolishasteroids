package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.davigj.foolish_asteroids.core.other.FADataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PerspicaciousElixirItem extends ElixirItem {
    public PerspicaciousElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        MinecraftServer server = entityLiving.getLevel().getServer();
        int antiDrunk = TrackedDataManager.INSTANCE.getValue(entityLiving, FADataProcessors.ANTI_DRUNK);
        if (server != null) {
            if (antiDrunk < 3) {
                TrackedDataManager.INSTANCE.setValue(entityLiving, FADataProcessors.ANTI_DRUNK, antiDrunk + 1);
                TranslatableComponent message = new TranslatableComponent("message.perspicacious.glub");
                player.displayClientMessage(message, true);
            } else {
                TranslatableComponent message = new TranslatableComponent("message.perspicacious.blug");
                player.displayClientMessage(message, true);
            }
        }
    }
}
