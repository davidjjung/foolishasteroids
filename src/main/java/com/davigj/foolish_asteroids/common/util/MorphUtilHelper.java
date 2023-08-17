package com.davigj.foolish_asteroids.common.util;

import de.budschie.bmorph.capabilities.IMorphCapability;
import de.budschie.bmorph.morph.MorphUtil;
import net.minecraft.world.entity.player.Player;

public class MorphUtilHelper {
    public static boolean isPlayerMorphed(Player player) {
        IMorphCapability morphCapability = MorphUtil.getCapOrNull(player);
        return morphCapability != null && morphCapability.getCurrentMorph().isPresent();
    }

    // the format for entity must fit the syntax "entity.modid.entity_name"
    public static boolean morphMatches(Player player, String entity) {
        IMorphCapability morphCapability = MorphUtil.getCapOrNull(player);
        if (morphCapability != null && morphCapability.getCurrentMorph().isPresent()) {
            return morphCapability.getCurrentMorph().get().getEntityType().toString().equals(entity);
        } else {
            return false;
        }
    }
    public static String playerCurrentMorph(Player player) {
        IMorphCapability morphCapability = MorphUtil.getCapOrNull(player);
        if (morphCapability != null && morphCapability.getCurrentMorph().isPresent()) {
            return morphCapability.getCurrentMorph().get().getEntityType().toString();
        } else {
            return "default";
        }
    }
}
