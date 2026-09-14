package com.leclowndu93150.horseimprovements.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.phys.Vec2;

public class RawMovementInput {

    public static Vec2 get() {
        Options options = Minecraft.getInstance().options;
        return new Vec2(axis(options.keyLeft.isDown(), options.keyRight.isDown()), axis(options.keyUp.isDown(), options.keyDown.isDown()));
    }

    private static float axis(boolean positive, boolean negative) {
        if (positive == negative) return 0.0F;
        return positive ? 1.0F : -1.0F;
    }
}
