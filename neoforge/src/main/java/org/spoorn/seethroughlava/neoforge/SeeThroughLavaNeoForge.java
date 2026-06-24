package org.spoorn.seethroughlava.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import org.spoorn.seethroughlava.SeeThroughLava;

@Mod(value = SeeThroughLava.MODID, dist = Dist.CLIENT)
public class SeeThroughLavaNeoForge {

    public SeeThroughLavaNeoForge() {
        SeeThroughLava.init();
    }
}
