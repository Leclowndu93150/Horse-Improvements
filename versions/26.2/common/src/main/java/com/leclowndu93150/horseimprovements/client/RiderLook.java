package com.leclowndu93150.horseimprovements.client;

import com.leclowndu93150.horseimprovements.compat.CameraCompat;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.animal.equine.AbstractHorse;

public final class RiderLook {

    private RiderLook() {}

    public static void followCamera(Camera camera) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !shouldFollowCamera(player)) return;
        player.setYRot(camera.yRot());
        player.yRotO = camera.yRot();
        player.setXRot(camera.xRot());
        player.xRotO = camera.xRot();
    }

    private static boolean shouldFollowCamera(LocalPlayer player) {
        return HorseImprovementsConfig.lookWithCamera
                && HorseImprovementsConfig.turning
                && !HorseImprovementsConfig.cameraSteering
                && player.getVehicle() instanceof AbstractHorse
                && !isInteracting()
                && CameraCompat.isRotatingPlayer();
    }

    private static boolean isInteracting() {
        Options options = Minecraft.getInstance().options;
        return options.keyUse.isDown() || options.keyAttack.isDown();
    }
}
