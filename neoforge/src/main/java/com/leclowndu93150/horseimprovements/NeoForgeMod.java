package com.leclowndu93150.horseimprovements;

import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class NeoForgeMod {

    public NeoForgeMod(IEventBus eventBus) {
        HorseImprovementsConfig.load();
    }
}
