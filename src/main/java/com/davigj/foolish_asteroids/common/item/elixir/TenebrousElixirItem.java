package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.util.FADamageSources;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class TenebrousElixirItem extends ElixirItem {
    public TenebrousElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        ScaleData data = ScaleTypes.HEALTH.getScaleData(entityLiving);
        if (data.getBaseScale() > 0.1f) {
            for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
                if (!living.getUUID().equals(player.getUUID())) {
                    living.addEffect(new MobEffectInstance(AMEffectRegistry.POWER_DOWN, 200, 0, false, false));
                    living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, false, false));
                    if (living instanceof Player) {
                        TranslatableComponent message = new TranslatableComponent("message.tenebrous.look_behind");
                        ((Player) living).displayClientMessage(message, true);
                    }
                } else {
                    TranslatableComponent message = new TranslatableComponent("message.tenebrous.user");
                    ((Player) living).displayClientMessage(message, true);
                    data.setTargetScale(data.getBaseScale() - 0.1f);
                    living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0, false, false));
                }
            }
        } else {
            TranslatableComponent message = new TranslatableComponent("message.tenebrous.insufficient");
            entityLiving.hurt(FADamageSources.DARKNESS, 1.0f);
            ((Player) entityLiving).displayClientMessage(message, true);
        }
        entityLiving.hurt(FADamageSources.DARKNESS, 0.05f);
    }
}
