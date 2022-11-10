package com.adryd.cauldron.api.render.helper;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class OverlayRendererBase implements IOverlayRenderHandler {
    protected final List<RenderObject> renderObjects;
    @Nullable
    protected Vec3d lastCameraPos = null;

    protected boolean shouldUpdate = true;

    public OverlayRendererBase() {
        this.renderObjects = new ArrayList<>();
    }

    @Override
    // ShouldUpdate is called if the player moves 32 blocks from the last update to prevent floating point issues as the player
    // gets further from the last render point.
    //
    // Classes that extend overlayRendererBase can set shouldUpdate to true,
    // or they can override shouldUpdate and update when they deem necessary
    //
    // Renderers should update every few hundred blocks a player moves from the last update
    // to prevent floating point issues, especially if there is something that renders all over the world
    // you should probably only be drawing things in range of the player anyways :P
    public boolean shouldUpdate(Camera camera) {
        boolean shouldUpdate = this.shouldUpdate || Objects.isNull(lastCameraPos) || lastCameraPos.distanceTo(camera.getPos()) > 32;
        this.lastCameraPos = camera.getPos();
        return shouldUpdate;
    }

    @Override
    public void render(float tickDelta, Camera camera) {
        for (RenderObject renderObject : renderObjects) {
            renderObject.draw(tickDelta, camera);
        }
    }

    @Override
    public void setup() {
        for (RenderObject renderObject : this.renderObjects) {
            renderObject.setup();
        }
    }
}
