package com.davigj.foolish_asteroids.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerMixin {
    private final LivingEntity mob;

    public CombatTrackerMixin(LivingEntity p_19285_) {
        this.mob = p_19285_;
    }

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void modifyDeathMessage(CallbackInfoReturnable<Component> callbackInfo) {
        CombatTracker combatTracker = (CombatTracker) (Object) this;
        LivingEntity maybeKiller = combatTracker.getKiller();
        if (maybeKiller instanceof Player killer) {
            // Check if the player has a diamond helmet (you can replace this condition)
            if (killer.getInventory().armor.get(3).getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
                // Modify the death message here
                String[] deathMessages = {
                        "death.assassinated",
                        "death.assassinated1",
                        "death.assassinated2"
                };
                Random random = new Random();
                int randomIndex = random.nextInt(deathMessages.length);
                String chosenMessageKey = deathMessages[randomIndex];
                System.out.println("For server posterity: killer was actually " + killer.getDisplayName().getString());
                Component modifiedMessage = new TranslatableComponent(chosenMessageKey, combatTracker.getMob().getDisplayName());
                callbackInfo.setReturnValue(modifiedMessage);
                return;
            }
        }
    }
}
