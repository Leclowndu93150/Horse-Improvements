package com.leclowndu93150.horseimprovements.mixin.client;

import com.leclowndu93150.horseimprovements.HorseRidingData;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    private Entity entity;

    @Shadow
    private boolean detached;

    @Shadow
    protected abstract void move(float f, float f1, float f2);

    @Inject(method = "alignWithEntity", at = @At("RETURN"))
    private void immersive_horse_riding$addHeadBob(float partialTick, CallbackInfo ci) {
        if (this.detached) return;
        if (this.entity == null) return;

        Entity vehicle = this.entity.getVehicle();
        if (!(vehicle instanceof AbstractHorse horse)) return;

        if (!horse.isSaddled()) return;
        if (horse.isJumping()) return;

        float currentSpeed = HorseRidingData.getCurrentSpeed();
        if (currentSpeed < 0.01f) return;

        int animTick = HorseRidingData.getAnimTick();
        float intensity = HorseImprovementsConfig.headBobIntensity;
        float frequency = HorseImprovementsConfig.headBobFrequency;

        float smoothTick = animTick + partialTick;
        float bobOffset = (float) -Math.cos(smoothTick * frequency) * intensity * currentSpeed;

        this.move(0.0F, bobOffset, 0.0F);
    }
}
