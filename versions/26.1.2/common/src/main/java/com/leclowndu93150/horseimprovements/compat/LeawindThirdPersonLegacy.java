package com.leclowndu93150.horseimprovements.compat;

import com.github.leawind.thirdperson.ThirdPerson;
import com.github.leawind.thirdperson.ThirdPersonStatus;

public class LeawindThirdPersonLegacy {

    public static boolean isActive() {
        return ThirdPerson.isAvailable() && ThirdPersonStatus.isRenderingInThirdPerson();
    }
}
