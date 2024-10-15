package com.github.anopensaucedev.particlecollisionfix;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Particle.class, priority = 800)
public abstract class ParticleCollisionFix {


    @Shadow
    public abstract Box getBoundingBox();


    @Shadow private boolean stopped;

    @Shadow public abstract void setBoundingBox(Box boundingBox);

    @Shadow protected abstract void repositionFromBoundingBox();

    @Shadow protected boolean onGround;

    @Shadow protected double velocityX;

    @Shadow protected double velocityZ;

    /**
     * @author AnOpenSauceDev
     * @reason Literally just Methane's particle optimization
     */
    @Overwrite
    public void move(double dx, double dy, double dz) {
        if (stopped) {
            return;
        }

        if (dx != 0.0 || dy != 0.0 || dz != 0.0) {
            this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
            this.repositionFromBoundingBox();
        }
        if (Math.abs(dy) >= (double)1.0E-5f && Math.abs(dy) < (double)1.0E-5f) {
            this.stopped = true;
        }

        if (dx != dx) {
            this.velocityX = 0.0;
        }
        if (dz != dz) {
            this.velocityZ = 0.0;
        }
    }


}
