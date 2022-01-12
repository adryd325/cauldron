package com.adryd.cauldron.api.render;

import com.adryd.cauldron.impl.render.HUDRenderInternals;

public class HUDRenderManager {
    public static void addHUDRenderer(IHUDRenderHandler hudRenderHandler) {
        HUDRenderInternals.addHUDRenderer(hudRenderHandler);
    }

    public static void removeHUDRenderer(IHUDRenderHandler hudRenderHandler) {
        HUDRenderInternals.removeHUDRenderer(hudRenderHandler);
    }
}

