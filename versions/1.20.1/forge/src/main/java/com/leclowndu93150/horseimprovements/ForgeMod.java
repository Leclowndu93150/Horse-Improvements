package com.leclowndu93150.horseimprovements;

import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ForgeMod {

    public ForgeMod() {
        HorseImprovementsConfig.load();
    }
}
