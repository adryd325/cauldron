package com.adryd.cauldron.api.render.helper;

import com.adryd.cauldron.impl.render.helpers.OverlayRenderInternals;

public class OverlayRenderManager {
    public static void addRenderer(IOverlayRenderHandler overlayRenderer) {
        OverlayRenderInternals.addRenderer(overlayRenderer);
    }
}
