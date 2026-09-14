package com.leclowndu93150.horseimprovements.mixin.client;

import com.leclowndu93150.horseimprovements.HorseRidingData;
import com.leclowndu93150.horseimprovements.client.RiderLook;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    private Entity entity;

    @Shadow
    private boolean detached;

    @Shadow
    protected abstract void move(float f, float f1, float f2);

    @WrapOperation(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;alignWithEntity(F)V"))
    private void immersive_horse_riding$addHeadBob(Camera camera, float partialTick, Operation<Void> original) {
        original.call(camera, partialTick);
        RiderLook.followCamera(camera);

        if (!HorseImprovementsConfig.cameraBobbing) return;
        if (this.detached && !HorseImprovementsConfig.thirdPersonBobbing) return;
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
