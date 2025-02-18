package org.sergedb.processing.core.managers;

import org.sergedb.processing.core.events.*;
import org.sergedb.processing.core.utils.Constants;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.prefs.Preferences;

/**
 * SettingsManager manages screen settings, display utilities, and responds to hotkey events.
 * Now includes persistent preferences and advanced display options.
 */
public class SettingsManager {
    private final PApplet app;
    private final Preferences preferences = Preferences.userNodeForPackage(SettingsManager.class);
    private boolean showFPS;
    private boolean showCoordinates;
    private boolean isPaused;
    private boolean isResizable;

    /**
     * Initializes the SettingsManager, loads preferences, and subscribes to hotkey events.
     */
    public SettingsManager(PApplet app) {
        this.app = app;
        loadPreferences();
        subscribeToHotkeyEvents();
    }

    /** Loads user preferences. */
    private void loadPreferences() {
        showFPS = preferences.getBoolean("showFPS", false);
        showCoordinates = preferences.getBoolean("showCoordinates", false);
        isResizable = preferences.getBoolean("isResizable", false);
        isPaused = false;
    }

    /** Saves user preferences. */
    private void savePreferences() {
        preferences.putBoolean("showFPS", showFPS);
        preferences.putBoolean("showCoordinates", showCoordinates);
        preferences.putBoolean("isResizable", isResizable);
    }

    /** Subscribes to EventBus for HotkeyEvents to trigger settings actions. */
    private void subscribeToHotkeyEvents() {
        EventBus.getInstance().subscribe(HotkeyEvent.class, event -> {
            switch (event.hotkey()) {
                case CONTROL_P -> togglePause();
                case CONTROL_F -> toggleFPSDisplay();
                case CONTROL_L -> toggleCoordinateDisplay();
                case CONTROL_SHIFT_R -> resetWindowSize();
                case CONTROL_R -> toggleResizable();
            }
        });
    }

    public SettingsManager setSize(int width, int height) {
        app.size(width, height);
        return this;
    }

    public SettingsManager setPixelDensity(int density) {
        app.pixelDensity(density);
        return this;
    }

    public SettingsManager setSmooth() {
        app.smooth();
        return this;
    }

    public SettingsManager setFPSDisplay(boolean showFPS) {
        this.showFPS = showFPS;
        savePreferences();
        return this;
    }

    public SettingsManager setCoordinatesDisplay(boolean showCoordinates) {
        this.showCoordinates = showCoordinates;
        savePreferences();
        return this;
    }

    public void toggleFPSDisplay() {
        showFPS = !showFPS;
        savePreferences();
    }

    public void toggleCoordinateDisplay() {
        showCoordinates = !showCoordinates;
        savePreferences();
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void toggleResizable() {
        isResizable = !isResizable;
        app.getSurface().setResizable(isResizable);
        savePreferences();
    }

    public void resetWindowSize() {
        app.windowResize(Constants.INITIAL_WIDTH, Constants.INITIAL_HEIGHT);
        EventBus.getInstance().publish(new WindowResizeEvent(app.width, app.height));
    }

    /** Displays FPS and coordinates based on user settings. */
    public void displayUtilities(ViewportManager viewportManager) {
        app.pushMatrix();
        if (showFPS) displayFPS(viewportManager);
        if (showCoordinates) displayCoordinates(viewportManager);
        app.popMatrix();
    }

    private int dynamicColor(int bgColor) {
    int r = (bgColor >> 16) & 0xFF;
    int g = (bgColor >> 8) & 0xFF;
    int b = bgColor & 0xFF;

    // Calculate luminance (perceived brightness)
    double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b; // Standard luminance formula

    // Return text color based on luminance threshold
    return (luminance > 128) ? 0 : 255; // If bright, use black text; otherwise, white

    }

    private void displayFPS(ViewportManager viewportManager) {
        int bgColor = app.get(10, 10);

        String statusText = isPaused ? "PAUSED" : "FPS: " + Math.round(app.frameRate);
        app.textAlign(PConstants.LEFT, PConstants.TOP);
        app.textSize(24);
        app.fill(dynamicColor(bgColor));
        app.text(statusText, (int) viewportManager.noScale(viewportManager.noCenterX(10)), (int) viewportManager.noScale(viewportManager.noCenterY(10)));
    }

    private void displayCoordinates(ViewportManager viewportManager) {
        float adjustedMouseX = viewportManager.noScale(viewportManager.noCenterX(app.mouseX));
        float adjustedMouseY = viewportManager.noScale(viewportManager.noCenterY(app.mouseY));
        float rectX = adjustedMouseX + ((adjustedMouseX > 0) ? -30 : 40);

        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.textSize(12);
        app.fill(0, 0, 0, 200);

        app.text("X: " + (float) (Math.round(adjustedMouseX * 10)) / 10, rectX, adjustedMouseY - 10);
        app.text("Y: " + (float) Math.round(adjustedMouseY * 10) / 10, rectX, adjustedMouseY + 5);
    }
}