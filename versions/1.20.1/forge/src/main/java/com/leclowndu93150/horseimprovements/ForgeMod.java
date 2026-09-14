package com.leclowndu93150.horseimprovements;

import com.leclowndu93150.horseimprovements.client.HorseImprovementsClient;
import com.leclowndu93150.horseimprovements.config.HorseImprovementsConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class ForgeMod {

    public ForgeMod() {
        HorseImprovementsConfig.load();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            HorseImprovementsClient.init();
        }
    }
}
