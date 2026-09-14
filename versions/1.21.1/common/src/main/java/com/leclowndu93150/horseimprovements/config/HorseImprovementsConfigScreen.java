package com.leclowndu93150.horseimprovements.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class HorseImprovementsConfigScreen {

    private HorseImprovementsConfigScreen() {}

    public static Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("immersive_horse_riding.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("immersive_horse_riding.config.category.movement"))
                        .option(toggle("smooth_acceleration", true, () -> HorseImprovementsConfig.smoothAcceleration, value -> HorseImprovementsConfig.smoothAcceleration = value))
                        .option(slider("acceleration", 0.05f, 0.01f, 0.5f, 0.01f, () -> HorseImprovementsConfig.acceleration, value -> HorseImprovementsConfig.acceleration = value))
                        .option(slider("deceleration", 0.08f, 0.01f, 0.5f, 0.01f, () -> HorseImprovementsConfig.deceleration, value -> HorseImprovementsConfig.deceleration = value))
                        .option(slider("reverse_speed", 0.25f, 0.0f, 1.0f, 0.05f, () -> HorseImprovementsConfig.reverseSpeed, value -> HorseImprovementsConfig.reverseSpeed = value))
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("immersive_horse_riding.config.category.turning"))
                        .option(toggle("turning", true, () -> HorseImprovementsConfig.turning, value -> HorseImprovementsConfig.turning = value))
                        .option(slider("turn_speed", 5.0f, 0.5f, 20.0f, 0.5f, () -> HorseImprovementsConfig.turnSpeed, value -> HorseImprovementsConfig.turnSpeed = value))
                        .option(toggle("camera_steering", false, () -> HorseImprovementsConfig.cameraSteering, value -> HorseImprovementsConfig.cameraSteering = value))
                        .option(toggle("look_with_camera", true, () -> HorseImprovementsConfig.lookWithCamera, value -> HorseImprovementsConfig.lookWithCamera = value))
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("immersive_horse_riding.config.category.bobbing"))
                        .option(toggle("camera_bobbing", true, () -> HorseImprovementsConfig.cameraBobbing, value -> HorseImprovementsConfig.cameraBobbing = value))
                        .option(toggle("third_person_bobbing", true, () -> HorseImprovementsConfig.thirdPersonBobbing, value -> HorseImprovementsConfig.thirdPersonBobbing = value))
                        .option(slider("head_bob_intensity", 0.14f, 0.0f, 0.5f, 0.01f, () -> HorseImprovementsConfig.headBobIntensity, value -> HorseImprovementsConfig.headBobIntensity = value))
                        .option(slider("head_bob_frequency", 0.5f, 0.05f, 1.5f, 0.05f, () -> HorseImprovementsConfig.headBobFrequency, value -> HorseImprovementsConfig.headBobFrequency = value))
                        .option(toggle("horse_head_bobbing", true, () -> HorseImprovementsConfig.horseHeadBobbing, value -> HorseImprovementsConfig.horseHeadBobbing = value))
                        .option(slider("horse_head_bob_intensity", 0.14f, 0.0f, 0.5f, 0.01f, () -> HorseImprovementsConfig.horseHeadBobIntensity, value -> HorseImprovementsConfig.horseHeadBobIntensity = value))
                        .option(slider("horse_head_bob_frequency", 0.5f, 0.05f, 1.5f, 0.05f, () -> HorseImprovementsConfig.horseHeadBobFrequency, value -> HorseImprovementsConfig.horseHeadBobFrequency = value))
                        .build())
                .save(HorseImprovementsConfig::save)
                .build()
                .generateScreen(parent);
    }

    private static Option<Boolean> toggle(String key, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable("immersive_horse_riding.config." + key))
                .description(OptionDescription.of(Component.translatable("immersive_horse_riding.config." + key + ".desc")))
                .binding(defaultValue, getter, setter)
                .controller(option -> BooleanControllerBuilder.create(option).coloured(true).onOffFormatter())
                .build();
    }

    private static Option<Float> slider(String key, float defaultValue, float min, float max, float step, Supplier<Float> getter, Consumer<Float> setter) {
        return Option.<Float>createBuilder()
                .name(Component.translatable("immersive_horse_riding.config." + key))
                .description(OptionDescription.of(Component.translatable("immersive_horse_riding.config." + key + ".desc")))
                .binding(defaultValue, getter, setter)
                .controller(option -> FloatSliderControllerBuilder.create(option).range(min, max).step(step))
                .build();
    }
}
