package com.leclowndu93150.horseimprovements.mixin.client;

import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import io.socol.betterthirdperson.impl.PlayerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = PlayerAdapter.class, remap = false)
public class BetterThirdPersonPlayerAdapterMixin {

    @Inject(method = "setVehicleYaw", at = @At("HEAD"), cancellable = true)
    private void immersive_horse_riding$keepHorseYaw(float yaw, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (HorseImprovementsConfig.turning && player != null && player.getVehicle() instanceof AbstractHorse) {
            ci.cancel();
        }
    }
}
