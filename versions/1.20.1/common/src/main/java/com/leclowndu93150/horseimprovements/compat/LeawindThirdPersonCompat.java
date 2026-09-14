package com.leclowndu93150.horseimprovements.compat;

import com.leclowndu93150.horseimprovements.platform.Services;

public class LeawindThirdPersonCompat {
    private static final boolean LOADED = Services.PLATFORM.isModLoaded("leawind_third_person");
    private static final boolean LEGACY = LOADED && isClassPresent("com.github.leawind.thirdperson.ThirdPersonStatus");

    public static boolean isActive() {
        if (!LOADED) return false;
        return LEGACY ? LeawindThirdPersonLegacy.isActive() : LeawindThirdPersonPerspective.isActive();
    }

    private static boolean isClassPresent(String className) {
        try {
            Class.forName(className, false, LeawindThirdPersonCompat.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
