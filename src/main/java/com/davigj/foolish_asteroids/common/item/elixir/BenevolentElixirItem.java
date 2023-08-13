package com.davigj.foolish_asteroids.common.item.elixir;

import com.davigj.foolish_asteroids.core.util.FoolishAsteroidsDamageSources;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

public class BenevolentElixirItem extends ElixirItem {
    public BenevolentElixirItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
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
        if (height > 0.05 && width > 0.05 && server != null) {
            for (LivingEntity living : entityLiving.level.getEntitiesOfClass(LivingEntity.class, entityLiving
                    .getBoundingBox().inflate(5.0D, 3.0D, 5.0D))) {
                if (!living.getUUID().equals(((Player)entityLiving).getUUID())) {
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
                        float heightIncrement = 0.2f / entities;
                        float widthIncrement = 0.2f / entities;
                        float healthIncrement = 0.4f / entities;

                        if (otherHeight < 5.0) {
                            scaleHeight.getScaleData(living).setTargetScale(otherHeight + heightIncrement);
                        }
                        if (otherWidth < 5.0) {
                            scaleWidth.getScaleData(living).setTargetScale(otherWidth + widthIncrement);
                        }
                        if (health < 3.0) {
                            scaleHealth.getScaleData(living).setTargetScale(health + healthIncrement);
                        }
                    }
                }
            }
        } else {
            entityLiving.hurt(FoolishAsteroidsDamageSources.BENEVOLENT, 1.0F);
        }
        TranslatableComponent message;
        if (noEntities) {
            message = new TranslatableComponent("message.benevolent.no_entities");
        } else {
            scaleHeight.getScaleData(entityLiving).setTargetScale(height / (1 + (entities * 0.025f)));
            scaleWidth.getScaleData(entityLiving).setTargetScale(width / (1 + (entities * 0.025f)));
            scaleDefense.getScaleData(entityLiving).setTargetScale(defense / (1 + (entities * 0.1f)));
            message = new TranslatableComponent("message.benevolent.user");
        }
        ((Player)entityLiving).displayClientMessage(message, true);
    }

}
