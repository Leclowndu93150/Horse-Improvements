package com.leclowndu93150.horseimprovements.mixin.client;

import com.leclowndu93150.horseimprovements.HorseRidingData;
import com.leclowndu93150.horseimprovements.client.RiderLook;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @WrapOperation(method = "updateCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;ZZF)V"))
    private void immersive_horse_riding$addHeadBob(Camera camera, Level level, Entity entity, boolean detached, boolean thirdPerson, float partialTick, Operation<Void> original) {
        original.call(camera, level, entity, detached, thirdPerson, partialTick);
        RiderLook.followCamera(camera);

        if (!HorseImprovementsConfig.cameraBobbing) return;
        if ((detached || thirdPerson) && !HorseImprovementsConfig.thirdPersonBobbing) return;
        if (entity == null) return;

        Entity vehicle = entity.getVehicle();
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

        ((CameraInvoker) camera).immersive_horse_riding$move(0.0F, bobOffset, 0.0F);
    }
}
