package com.adryd.cauldron.api.render;

import com.adryd.cauldron.impl.render.WorldRenderInternals;

public class WorldRenderManager {
    public static void addWorldRenderer(IWorldRenderHandler worldRenderHandler) {
        WorldRenderInternals.addWorldRenderer(worldRenderHandler);
    }

    public static void removeWorldRenderer(IWorldRenderHandler worldRenderHandler) {
        WorldRenderInternals.removeWorldRenderer(worldRenderHandler);
    }
}
