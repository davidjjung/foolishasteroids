package com.davigj.foolish_asteroids.common.item.elixir;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Random;

public class SagaciousElixirItem extends ElixirItem {

    public SagaciousElixirItem(Properties properties) {
        super(properties);
    }
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = (Player)entityLiving;
        ScaleType scaleType = ScaleTypes.STEP_HEIGHT;
        Random random = new Random();
        String type = "";
        int attribute = random.nextInt(6);
        switch (attribute) {
            case 0 -> {
                type = "Your capacity to step up has been amplified by a factor of ";
            }
            case 1 -> {
                scaleType = ScaleTypes.DEFENSE;
                type = "Your ability to ward off damage has been amplified by a factor of ";
            }
            case 2 -> {
                scaleType = ScaleTypes.MOTION;
                type = "Your swiftness has been amplified by a factor of ";
            }
            case 3 -> {
                scaleType = ScaleTypes.MINING_SPEED;
                type = "Your ability to mine has been amplified by a factor of ";
            }
            case 4 -> {
                scaleType = ScaleTypes.VISIBILITY;
                type = "Your visibility has been amplified by a factor of ";
            }
            case 5 -> {
                scaleType = ScaleTypes.BLOCK_REACH;
                type = "Your ability to place blocks has been amplified by a factor of ";
            }
            case 6 -> {
                scaleType = ScaleTypes.ENTITY_REACH;
                type = "Your capacity to reach others has been amplified by a factor of ";
            }
        }
        ScaleData data = scaleType.getScaleData(entityLiving);
        String result = type + String.format("%.2f", data.getBaseScale());
        TranslatableComponent message = new TranslatableComponent("message.sagacious.attribute", result);

        player.displayClientMessage(message, true);
        entityLiving.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 60, 0, false, false));
    }
}
