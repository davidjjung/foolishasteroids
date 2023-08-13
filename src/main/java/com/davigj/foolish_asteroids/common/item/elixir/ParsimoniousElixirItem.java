package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

public class ParsimoniousElixirItem extends ElixirItem {
    public ParsimoniousElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        MinecraftServer server = entityLiving.getLevel().getServer();
        ScaleType scaleHeight = ScaleTypes.HEIGHT;
        ScaleType scaleWidth = ScaleTypes.WIDTH;
        ScaleType scaleHealth = ScaleTypes.HEALTH;
        ScaleType scaleDefense = ScaleTypes.DEFENSE;

        float height = scaleHeight.getScaleData(entityLiving).getBaseScale();
        float width = scaleWidth.getScaleData(entityLiving).getBaseScale();
        float defense = scaleDefense.getScaleData(entityLiving).getBaseScale();
        boolean noEntities = true;
        int entities = 0;
        int entitiesTreated = 0;
        if (height < 5.0 && width < 5.0 && server != null) {
            for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving
                    .getBoundingBox().inflate(5.0D, 3.0D, 5.0D))) {
                if (!living.getUUID().equals(player.getUUID())) {
                    entities++;
                    entitiesTreated++;
                    noEntities = false;
                    if (entitiesTreated > 4) {
                        break;
                    }
                    float otherHeight = scaleHeight.getScaleData(living).getBaseScale();
                    float otherWidth = scaleWidth.getScaleData(living).getBaseScale();
                    float health = scaleHealth.getScaleData(living).getBaseScale();

                    if (entities > 0) {
                        float heightDecrement = 0.3f / entities;
                        float widthDecrement = 0.3f / entities;
                        float healthDecrement = 0.3f / entities;

                        if (otherHeight > 0.1 && otherWidth > 0.1 && health > 0.1) {
                            scaleHeight.getScaleData(living).setTargetScale(otherHeight / (1 + heightDecrement));
                            scaleWidth.getScaleData(living).setTargetScale(otherWidth / (1 + widthDecrement));
                            scaleHealth.getScaleData(living).setTargetScale(health / (1 + healthDecrement));
                        } else {
                            living.hurt(DamageSource.MAGIC, 1.0F);
                        }
                    }
                }
            }
        }
        TranslatableComponent message;
        if (noEntities) {
            message = new TranslatableComponent("message.parsimonious.no_entities");
        } else {
            scaleHeight.getScaleData(entityLiving).setTargetScale(height + 0.05f);
            scaleWidth.getScaleData(entityLiving).setTargetScale(width + 0.05f);
            entityLiving.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, (int) (entitiesTreated / 2), false, false));
            message = new TranslatableComponent("message.parsimonious.user");
        }
        player.displayClientMessage(message, true);
    }
}
