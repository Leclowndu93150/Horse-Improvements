package com.leclowndu93150.horseimprovements.platform;

import net.neoforged.fml.ModList;

public class NeoForgePlatformHelper implements PlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
