package com.davigj.foolish_asteroids.common.util;

import net.minecraft.network.chat.TextColor;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final int DRINK_TIME = 24;
    public static final int MAX_SNAKES = 5;
    public static final int MAX_SOAPINESS = 450;
    public static final int STICKY_HAND_CHARGE_TICKS = 30;
    public static final int CURSED_STAFF_CHARGE_TICKS = 90;
    public static final int PLIERS_CHARGE_TICKS = 80;
    public static final List<TextColor> RAINBOW_COLORS = Arrays.asList(
            TextColor.fromRgb(0xFC2847),
            TextColor.fromRgb(0xFFA343),
            TextColor.fromRgb(0xFDFC74),
            TextColor.fromRgb(0x71BC78),
            TextColor.fromRgb(0x0F4C81),
            TextColor.fromRgb(0x7442C8),
            TextColor.fromRgb(0xFB7E1D)
    );
    public static final List<TextColor> SUNSET_COLORS = Arrays.asList(
            TextColor.fromRgb(0xF0AE81),
            TextColor.fromRgb(0xBF7781),
            TextColor.fromRgb(0x8F6D77),
            TextColor.fromRgb(0x615860),
            TextColor.fromRgb(0x364557)
    );
}
