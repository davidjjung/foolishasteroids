package com.davigj.foolish_asteroids.core.other.tags;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FoolishAsteroidsBlockTags {
    public static final TagKey<Block> GLASS_BLOCKS = blockTag("glass_blocks");
    public static final TagKey<Block> MUD_BRICKS = blockTag("mud_bricks");

    private static TagKey<Block> blockTag(String name) {
        return TagUtil.blockTag(FoolishAsteroidsMod.MOD_ID, name);
    }
}
