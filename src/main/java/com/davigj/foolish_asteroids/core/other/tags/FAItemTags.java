package com.davigj.foolish_asteroids.core.other.tags;

import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FAItemTags {
    public static final TagKey<Item> ONION_PROOF = itemTag("onion_proof_helmets");

    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag(FoolishAsteroidsMod.MOD_ID, name);
    }
}
