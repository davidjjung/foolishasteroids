package com.davigj.foolish_asteroids.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CurseParticle extends TextureSheetParticle {

    private CurseParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double particleRedIn, double particleGreenIn, double particleBlueIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.quadSize *= 1.0F;
        this.lifetime = 120;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age >= this.lifetime) {
            this.remove();
        } else {
            float progress = (float) this.age / this.lifetime;

            // Randomize the initial angle and radius for each particle
            float initialAngle = (float) (Math.PI * 2 * Math.random());
            double circleRadius = 0.012 + (0.1 * Math.random()); // Vary the radius

            // Calculate angle based on progress and the initial angle
            float angle = initialAngle + progress * 2 * (float) Math.PI;

            // Calculate particle's position on the circle
            double offsetX = circleRadius * Math.cos(angle);
            double offsetY = circleRadius * Math.sin(angle) * 0.5; // Vary the Y offset
            double offsetZ = circleRadius * Math.sin(angle);
            this.move(offsetX, offsetY, offsetZ);

            // Increment age
            this.age++;
            // Calculate opacity based on progress
            float minAlpha = 0.2F; // Minimum alpha value
            float maxAlpha = 1.0F; // Maximum alpha value
            float alpha = maxAlpha - (maxAlpha - minAlpha) * progress;

            // Calculate color with varying opacity
            this.setAlpha(alpha);
        }
    }



    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            CurseParticle particle = new CurseParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setColor((float) xSpeed, (float) ySpeed, (float) zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}