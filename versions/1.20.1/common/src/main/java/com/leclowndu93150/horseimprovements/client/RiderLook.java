package com.leclowndu93150.horseimprovements.client;

import com.leclowndu93150.horseimprovements.compat.CameraCompat;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public final class RiderLook {

    private RiderLook() {}

    public static void followCamera(Camera camera) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !shouldFollowCamera(player)) return;
        player.setYRot(camera.getYRot());
        player.yRotO = camera.getYRot();
        player.setXRot(camera.getXRot());
        player.xRotO = camera.getXRot();
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
