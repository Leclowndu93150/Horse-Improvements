package com.leclowndu93150.horseimprovements;

import com.leclowndu93150.horseimprovements.client.HorseImprovementsClient;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class NeoForgeMod {

    public NeoForgeMod(IEventBus eventBus) {
        HorseImprovementsConfig.load();
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            HorseImprovementsClient.init();
        }
    }
}
