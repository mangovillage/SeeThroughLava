package org.spoorn.seethroughlava.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spoorn.seethroughlava.config.LavaConfig;
import org.spoorn.seethroughlava.config.ModConfig;
import org.spoorn.seethroughlava.config.WaterConfig;

/**
 * Note: Found these mixins aren't invoked if Sodium Extra mod is installed.
 * 
 * Set priority to 1001 to ensure these mixins apply after other mods.
 */
@Mixin(value = FogRenderer.class, priority = 1001)
public class FogRendererMixin {
    
    // Override fog at the TAIL to avoid conflicts with other mods such as Origins that did redirection which I used to do.
    @Inject(method = "setupFog", at = @At(value = "TAIL"))
    private void changeForInFluid(Camera camera, int renderDistanceChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir) {
        FogType fogType = camera.getFluidInCamera();
        FogData fogData = cir.getReturnValue();
        WaterConfig waterConfig = ModConfig.get().waterConfig;
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;

        if (fogType == FogType.WATER) {
            if (waterConfig.shouldCompletelySeeThroughWater) {
                setFogEnd(fogData, 150F);
            } else if (waterConfig.shouldOverrideWaterFogDensity) {
                // User configured factor to set fog end value
                float endVal = (float) waterConfig.waterSeeThroughFactor;
                endVal = endVal > 200 ? 200 : (endVal < 1 ? 1 : endVal);
                setFogEnd(fogData, endVal);
            }
        }

        if (fogType == FogType.LAVA) {
            if (lavaConfig.shouldCompletelySeeThroughLava) {
                fogData.environmentalStart = 0F;
                setFogEnd(fogData, 150F);
            } else if (lavaConfig.shouldOverrideLavaFogDensity) {
                // User configured factor to set fog end value
                float endVal = (float) lavaConfig.lavaSeeThroughFactor;
                endVal = endVal > 200 ? 200 : (endVal < 1 ? 1 : endVal);
                fogData.environmentalStart = 0F;
                setFogEnd(fogData, endVal);
            }
        }
    }

    private void setFogEnd(FogData fogData, float endVal) {
        fogData.environmentalEnd = endVal;
        fogData.skyEnd = endVal;
        fogData.cloudEnd = endVal;
    }
}
