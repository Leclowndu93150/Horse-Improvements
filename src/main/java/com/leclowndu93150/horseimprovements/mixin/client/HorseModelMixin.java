package com.leclowndu93150.horseimprovements.mixin.client;

import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseModel.class)
public class HorseModelMixin<T extends AbstractHorse> {

    @Shadow
    protected ModelPart headParts;

    @Unique
    private float horseimprovements$savedLimbSwing;

    @Unique
    private float horseimprovements$savedLimbSwingAmount;

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/animal/horse/AbstractHorse;FFF)V",
            at = @At("HEAD"))
    private void horseimprovements$captureParams(T horse, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci) {
        this.horseimprovements$savedLimbSwing = limbSwing;
        this.horseimprovements$savedLimbSwingAmount = limbSwingAmount;
    }

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/animal/horse/AbstractHorse;FFF)V",
            at = @At("RETURN"))
    private void horseimprovements$adjustHeadBob(T horse, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci) {
        if (limbSwingAmount <= 0.2F) return;

        float standAnim = horse.getStandAnim(partialTick);
        float eatAnim = horse.getEatAnim(partialTick);
        if (standAnim > 0 || eatAnim > 0) return;

        float vanillaBob = Mth.cos(horseimprovements$savedLimbSwing * 0.8F) * 0.15F * horseimprovements$savedLimbSwingAmount;

        float customBob = Mth.cos(horseimprovements$savedLimbSwing * HorseImprovementsConfig.horseHeadBobFrequency)
                * HorseImprovementsConfig.horseHeadBobIntensity
                * horseimprovements$savedLimbSwingAmount;

        this.headParts.xRot = this.headParts.xRot - vanillaBob + customBob;
    }
}
