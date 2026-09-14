package com.leclowndu93150.horseimprovements.compat;

public class CameraCompat {

    public static boolean isThirdPersonModActive() {
        return ShoulderSurfingCompat.isActive() || LeawindThirdPersonCompat.isActive() || BetterThirdPersonCompat.isActive();
    }

    public static boolean isRotatingPlayer() {
        return ShoulderSurfingCompat.isRotatingPlayer() || LeawindThirdPersonCompat.isActive() || BetterThirdPersonCompat.isActive();
    }
}
