package com.leclowndu93150.horseimprovements.platform;

import java.util.ServiceLoader;

public class Services {
    public static final PlatformHelper PLATFORM = ServiceLoader.load(PlatformHelper.class).findFirst().orElseThrow();
}
