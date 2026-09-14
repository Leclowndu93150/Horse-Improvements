package com.leclowndu93150.horseimprovements.compat;

import com.leclowndu93150.horseimprovements.platform.Services;
import com.xtracr.realcamera.RealCameraCore;
import net.minecraft.client.Camera;

public class RealCameraCompat {
    private static final boolean LOADED = Services.PLATFORM.isModLoaded("realcamera");

    public static void syncCameraPosition(Camera camera) {
        if (LOADED && RealCameraCore.isActive()) {
            RealCameraCore.setCameraPos(camera.getPosition());
        }
    }
}
