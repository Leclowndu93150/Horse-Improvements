package com.leclowndu93150.horseimprovements.mixin.client;

import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraft.client.model.animal.equine.AbstractEquineModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractEquineModel.class)
public class HorseModelMixin<T extends EquineRenderState> {

    @Shadow
    protected ModelPart headParts;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/EquineRenderState;)V",
            at = @At("RETURN"))
    private void immersive_horse_riding$adjustHeadBob(T state, CallbackInfo ci) {
        float animationSpeed = state.walkAnimationSpeed;
        float animationPos = state.walkAnimationPos;

        if (animationSpeed <= 0.2F) return;

        float standing = state.standAnimation;
        float eating = state.eatAnimation;
        if (standing > 0 || eating > 0) return;

        float vanillaBob = Mth.cos(animationPos * 0.8F) * 0.15F * animationSpeed;

        float customBob = Mth.cos(animationPos * HorseImprovementsConfig.horseHeadBobFrequency)
                * HorseImprovementsConfig.horseHeadBobIntensity
                * animationSpeed;

        this.headParts.xRot = this.headParts.xRot - vanillaBob + customBob;
    }
}
