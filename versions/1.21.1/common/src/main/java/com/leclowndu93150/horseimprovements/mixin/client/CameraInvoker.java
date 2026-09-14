package com.leclowndu93150.horseimprovements.mixin.client;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraInvoker {

    @Invoker("move")
    void immersive_horse_riding$move(float forwards, float up, float left);
}
