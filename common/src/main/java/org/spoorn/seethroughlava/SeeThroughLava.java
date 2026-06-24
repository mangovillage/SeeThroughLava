package org.spoorn.seethroughlava;

import lombok.extern.log4j.Log4j2;
import org.spoorn.seethroughlava.config.ModConfig;

@Log4j2
public class SeeThroughLava {

    public static final String MODID = "seethroughlava";
    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }

        log.info("Hello from SeeThroughLava!");
        ModConfig.init();
        initialized = true;
    }
}
