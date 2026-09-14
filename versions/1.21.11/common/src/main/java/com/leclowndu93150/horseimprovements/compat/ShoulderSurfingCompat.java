package com.leclowndu93150.horseimprovements.compat;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.leclowndu93150.horseimprovements.platform.Services;

public class ShoulderSurfingCompat {
    private static final boolean LOADED = Services.PLATFORM.isModLoaded("shouldersurfing");

    public static boolean isActive() {
        return LOADED && IShoulderSurfing.getInstance().isShoulderSurfing();
    }

    public static boolean isRotatingPlayer() {
        if (!isActive()) return false;
        IShoulderSurfing shoulderSurfing = IShoulderSurfing.getInstance();
        return shoulderSurfing.isCameraDecoupled() && !shoulderSurfing.isFreeLooking();
    }
}
