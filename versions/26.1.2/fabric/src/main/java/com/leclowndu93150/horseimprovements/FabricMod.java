package com.leclowndu93150.horseimprovements;

import com.leclowndu93150.horseimprovements.client.HorseImprovementsClient;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricMod implements ModInitializer {

    @Override
    public void onInitialize() {
        HorseImprovementsConfig.load();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            HorseImprovementsClient.init();
        }
    }
}
