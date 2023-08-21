package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.common.effect.AloftEffect;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolishAsteroidsMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FoolishAsteroidsMod.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, FoolishAsteroidsMod.MOD_ID);

    public static final RegistryObject<MobEffect> ALOFT = EFFECTS.register("aloft", AloftEffect::new);

    public static final RegistryObject<Potion> ALOFT_NORMAL = POTIONS.register("aloft", () -> new Potion(new MobEffectInstance(ALOFT.get(), 300)));

    public static void registerBrewingRecipes() {
        DataUtil.addMix(Potions.AWKWARD, FoolishAsteroidsItems.SUNBIRD_FEATHER.get(), ALOFT_NORMAL.get());
    }
}