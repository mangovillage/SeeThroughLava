package org.spoorn.seethroughlava.fabric;

import net.fabricmc.api.ClientModInitializer;
import org.spoorn.seethroughlava.SeeThroughLava;

public class SeeThroughLavaFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SeeThroughLava.init();
    }
}
