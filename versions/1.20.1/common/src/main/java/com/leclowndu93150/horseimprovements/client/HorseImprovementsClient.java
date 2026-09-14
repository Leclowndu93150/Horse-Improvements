package com.leclowndu93150.horseimprovements.client;

import com.leclowndu93150.baguettelib.config.ConfigScreens;
import com.leclowndu93150.horseimprovements.Constants;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfigScreen;

public final class HorseImprovementsClient {

    private HorseImprovementsClient() {}

    public static void init() {
        ConfigScreens.register(Constants.MOD_ID, HorseImprovementsConfigScreen::create);
    }
}
