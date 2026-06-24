package org.spoorn.seethroughlava.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spoorn.seethroughlava.config.LavaConfig;
import org.spoorn.seethroughlava.config.ModConfig;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Redirect(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isOnFire()Z"))
    private boolean shouldRenderFireOverlay(LocalPlayer player) {
        LavaConfig lavaConfig = ModConfig.get().lavaConfig;
        if ((lavaConfig.removeFireScreenEffectInLava && player.isInLava()) || lavaConfig.removeFireScreenEffectIfOnFire) {
            return false;
        }

        return player.isOnFire();
    }
}
