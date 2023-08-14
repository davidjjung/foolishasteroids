package com.davigj.foolish_asteroids.common.util;

import de.budschie.bmorph.capabilities.IMorphCapability;
import de.budschie.bmorph.morph.MorphUtil;
import net.minecraft.world.entity.player.Player;

public class MorphUtilHelper {
    public static boolean isPlayerMorphed(Player player) {
        IMorphCapability morphCapability = MorphUtil.getCapOrNull(player);
        return morphCapability != null && morphCapability.getCurrentMorph().isPresent();
    }
}
