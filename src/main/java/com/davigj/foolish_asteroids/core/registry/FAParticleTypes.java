package com.davigj.foolish_asteroids.core.registry;

import com.davigj.foolish_asteroids.client.particle.CurseParticle;
import com.davigj.foolish_asteroids.client.particle.ElectronParticle;
import com.davigj.foolish_asteroids.core.FoolishAsteroidsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = FoolishAsteroidsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FAParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FoolishAsteroidsMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ELECTRON = PARTICLE_TYPES.register("electron", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CURSE = PARTICLE_TYPES.register("curse", () -> new SimpleParticleType(false));

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleTypes(ParticleFactoryRegisterEvent event) {
        ParticleEngine manager = Minecraft.getInstance().particleEngine;
        if (ELECTRON.isPresent()) {
            manager.register(ELECTRON.get(), ElectronParticle.Factory::new);
        }if (CURSE.isPresent()) {
            manager.register(CURSE.get(), CurseParticle.Factory::new);
        }
    }
}