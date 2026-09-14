package com.leclowndu93150.horseimprovements.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class HorseImprovementsConfig {
    private static final Path CONFIG_PATH = Path.of("config", "immersive_horse_riding.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static boolean smoothAcceleration = true;
    public static float acceleration = 0.05f;
    public static float deceleration = 0.08f;
    public static float reverseSpeed = 0.25f;
    public static boolean turning = true;
    public static float turnSpeed = 5.0f;
    public static boolean cameraSteering = false;
    public static boolean lookWithCamera = true;
    public static boolean cameraBobbing = true;
    public static boolean thirdPersonBobbing = true;
    public static float headBobIntensity = 0.14f;
    public static float headBobFrequency = 0.5f;
    public static boolean horseHeadBobbing = true;
    public static float horseHeadBobIntensity = 0.14f;
    public static float horseHeadBobFrequency = 0.5f;

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                smoothAcceleration = getBoolean(json, "smoothAcceleration", smoothAcceleration);
                acceleration = getFloat(json, "acceleration", acceleration);
                deceleration = getFloat(json, "deceleration", deceleration);
                reverseSpeed = getFloat(json, "reverseSpeed", reverseSpeed);
                turning = getBoolean(json, "turning", turning);
                turnSpeed = getFloat(json, "turnSpeed", turnSpeed);
                cameraSteering = getBoolean(json, "cameraSteering", cameraSteering);
                lookWithCamera = getBoolean(json, "lookWithCamera", lookWithCamera);
                cameraBobbing = getBoolean(json, "cameraBobbing", cameraBobbing);
                thirdPersonBobbing = getBoolean(json, "thirdPersonBobbing", thirdPersonBobbing);
                headBobIntensity = getFloat(json, "headBobIntensity", headBobIntensity);
                headBobFrequency = getFloat(json, "headBobFrequency", headBobFrequency);
                horseHeadBobbing = getBoolean(json, "horseHeadBobbing", horseHeadBobbing);
                horseHeadBobIntensity = getFloat(json, "horseHeadBobIntensity", horseHeadBobIntensity);
                horseHeadBobFrequency = getFloat(json, "horseHeadBobFrequency", horseHeadBobFrequency);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        save();
    }

    public static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("smoothAcceleration", smoothAcceleration);
        json.addProperty("acceleration", acceleration);
        json.addProperty("deceleration", deceleration);
        json.addProperty("reverseSpeed", reverseSpeed);
        json.addProperty("turning", turning);
        json.addProperty("turnSpeed", turnSpeed);
        json.addProperty("cameraSteering", cameraSteering);
        json.addProperty("lookWithCamera", lookWithCamera);
        json.addProperty("cameraBobbing", cameraBobbing);
        json.addProperty("thirdPersonBobbing", thirdPersonBobbing);
        json.addProperty("headBobIntensity", headBobIntensity);
        json.addProperty("headBobFrequency", headBobFrequency);
        json.addProperty("horseHeadBobbing", horseHeadBobbing);
        json.addProperty("horseHeadBobIntensity", horseHeadBobIntensity);
        json.addProperty("horseHeadBobFrequency", horseHeadBobFrequency);

        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(json, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static float getFloat(JsonObject json, String key, float defaultValue) {
        return json.has(key) ? json.get(key).getAsFloat() : defaultValue;
    }

    private static boolean getBoolean(JsonObject json, String key, boolean defaultValue) {
        return json.has(key) ? json.get(key).getAsBoolean() : defaultValue;
    }
}
