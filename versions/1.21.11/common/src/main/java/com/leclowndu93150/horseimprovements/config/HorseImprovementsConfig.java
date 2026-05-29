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

    public static float turnSpeed = 5.0f;
    public static float acceleration = 0.05f;
    public static float deceleration = 0.08f;
    public static float headBobIntensity = 0.14f;
    public static float headBobFrequency = 0.5f;
    public static float horseHeadBobIntensity = 0.14f;
    public static float horseHeadBobFrequency = 0.5f;

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                turnSpeed = getFloat(json, "turnSpeed", turnSpeed);
                acceleration = getFloat(json, "acceleration", acceleration);
                deceleration = getFloat(json, "deceleration", deceleration);
                headBobIntensity = getFloat(json, "headBobIntensity", headBobIntensity);
                headBobFrequency = getFloat(json, "headBobFrequency", headBobFrequency);
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
        json.addProperty("turnSpeed", turnSpeed);
        json.addProperty("acceleration", acceleration);
        json.addProperty("deceleration", deceleration);
        json.addProperty("headBobIntensity", headBobIntensity);
        json.addProperty("headBobFrequency", headBobFrequency);
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
}
