package com.leclowndu93150.horseimprovements.platform;

import net.minecraftforge.fml.ModList;

public class ForgePlatformHelper implements PlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
