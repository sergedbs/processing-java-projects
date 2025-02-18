package org.sergedb.processing.core.managers;

import processing.core.PApplet;

/**
 * Screen interface for defining screens with lifecycle methods.
 */
public interface Screen {
    /** Called when the screen is initialized. */
    void onInitialize();

    /** Called once per tick to update the screen logic. */
    void onUpdate();

    /** Called once per frame to render the screen. */
    void onRender();

    /** Called when the screen is disposed. */
    void onDispose();
}
