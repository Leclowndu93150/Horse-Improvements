package com.leclowndu93150.horseimprovements;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class HorseImprovementsMixinCanceller implements MixinCanceller {

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return mixinClassName.equals("io.github.mortuusars.horseman.mixin.free_camera_when_mounted.AbstractHorseMixin")
                || mixinClassName.equals("io.github.mortuusars.horseman.mixin.rotate_horse_when_mounting.AbstractHorseMixin");
    }
}
