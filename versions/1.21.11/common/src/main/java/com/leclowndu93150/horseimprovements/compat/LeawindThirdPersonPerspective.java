package com.leclowndu93150.horseimprovements.compat;

import io.github.leawind.perspectiveapi.api.PerspectiveAPI;

public class LeawindThirdPersonPerspective {
    private static final String PERSPECTIVE_ID = "leawind_third_person.third_person";

    public static boolean isActive() {
        return PerspectiveAPI.isCurrent(PERSPECTIVE_ID);
    }
}
