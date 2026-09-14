package com.leclowndu93150.horseimprovements.compat;

import com.leclowndu93150.horseimprovements.platform.Services;
import io.socol.betterthirdperson.BetterThirdPerson;

public class BetterThirdPersonCompat {
    private static final boolean LOADED = Services.PLATFORM.isModLoaded("betterthirdperson");

    public static boolean isActive() {
        return LOADED && BetterThirdPerson.getCameraManager().hasCustomCamera();
    }
}
