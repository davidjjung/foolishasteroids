package com.davigj.foolish_asteroids.common.util;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.projectile.AbstractArrow;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PickupUtil {
    private static final Map<ResourceLocation, Supplier<EntityType<?>>> ITEM_ENTITY_TYPE_MAP = new HashMap<>();

    public static void registerItemType(ResourceLocation itemLocation, ResourceLocation entityTypeLocation) {
        EntityType<?> entityType = Registry.ENTITY_TYPE.get(entityTypeLocation);
        if (entityType != null) {
            ITEM_ENTITY_TYPE_MAP.put(itemLocation, () -> entityType);
        } else {
            // Handle error if entityTypeLocation is not valid
        }
    }

    // Get the entity type for a given resource location
    public static Supplier<EntityType<?>> getEntityTypeForItem(ResourceLocation itemLocation) {
        return ITEM_ENTITY_TYPE_MAP.get(itemLocation);
    }
    public static ResourceLocation getEntityLocation(EntityType<?> entityType) {
        return Registry.ENTITY_TYPE.getKey(entityType);
    }

    public static void registerProjectileTypes() {
        registerItemType(new ResourceLocation("minecraft", "arrow"), new ResourceLocation("minecraft", "arrow"));
        registerItemType(new ResourceLocation("minecraft", "tipped_arrow"), new ResourceLocation("minecraft", "arrow"));
        registerItemType(new ResourceLocation("minecraft", "spectral_arrow"), new ResourceLocation("minecraft", "spectral_arrow"));
        registerItemType(new ResourceLocation("minecraft", "snowball"), new ResourceLocation("minecraft", "snowball"));
        registerItemType(new ResourceLocation("neapolitan", "bananarrow"), new ResourceLocation("neapolitan", "bananarrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "pufferfish_arrow"), new ResourceLocation("nock_enough_arrows", "pufferfish_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "explosive_arrow"), new ResourceLocation("nock_enough_arrows", "explosive_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "slime_arrow"), new ResourceLocation("nock_enough_arrows", "slime_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "prismarine_arrow"), new ResourceLocation("nock_enough_arrows", "prismarine_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "hookshot_arrow"), new ResourceLocation("nock_enough_arrows", "hookshot_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "message_arrow"), new ResourceLocation("nock_enough_arrows", "message_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "teleportation_arrow"), new ResourceLocation("nock_enough_arrows", "teleportation_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "ink_arrow"), new ResourceLocation("nock_enough_arrows", "ink_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "torch_arrow"), new ResourceLocation("nock_enough_arrows", "torch_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "soul_torch_arrow"), new ResourceLocation("nock_enough_arrows", "soul_torch_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "redstone_torch_arrow"), new ResourceLocation("nock_enough_arrows", "redstone_torch_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "ethereal_arrow"), new ResourceLocation("nock_enough_arrows", "ethereal_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "dousing_arrow"), new ResourceLocation("nock_enough_arrows", "dousing_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "blossom_arrow"), new ResourceLocation("nock_enough_arrows", "blossom_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "growing_arrow"), new ResourceLocation("nock_enough_arrows", "growing_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "drill_arrow"), new ResourceLocation("nock_enough_arrows", "drill_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "split_arrow"), new ResourceLocation("nock_enough_arrows", "split_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "party_arrow"), new ResourceLocation("nock_enough_arrows", "party_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "repulsive_arrow"), new ResourceLocation("nock_enough_arrows", "repulsive_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "echoing_arrow"), new ResourceLocation("nock_enough_arrows", "echoing_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "seeker_arrow"), new ResourceLocation("nock_enough_arrows", "seeker_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "cupid_arrow"), new ResourceLocation("nock_enough_arrows", "cupid_arrow"));
        registerItemType(new ResourceLocation("nock_enough_arrows", "reinforced_arrow"), new ResourceLocation("nock_enough_arrows", "reinforced_arrow"));
        registerItemType(new ResourceLocation("savage_and_ravage", "mischief_arrow"), new ResourceLocation("savage_and_ravage", "mischief_arrow"));

        registerItemType(new ResourceLocation("minecraft", "splash_potion"), new ResourceLocation("minecraft", "potion"));
        registerItemType(new ResourceLocation("minecraft", "lingering_potion"), new ResourceLocation("minecraft", "potion"));
//        registerItemType(new ResourceLocation("supplementaries", "bomb"), new ResourceLocation("supplementaries", "bomb"));
    }
}

