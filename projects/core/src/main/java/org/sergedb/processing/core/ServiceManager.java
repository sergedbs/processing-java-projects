package org.sergedb.processing.core;

import org.sergedb.processing.core.managers.*;
import processing.core.PApplet;

/**
 * ServiceManager provides singleton instances of core services,
 * ensuring consistent lifecycle management across the application.
 */
public class ServiceManager {
    private static ServiceManager INSTANCE;

    private final SettingsManager settingsManager;
    private final ViewportManager viewportManager;
    private final InputManager inputManager;
    private final ScreenManager screenManager;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private ServiceManager(PApplet app) {
        settingsManager = new SettingsManager(app);
        viewportManager = new ViewportManager(app);
        inputManager = new InputManager(app);
        screenManager = new ScreenManager();
    }

    /**
     * Initializes the singleton instance with the required PApplet reference.
     *
     * @param app The PApplet instance.
     */
    public static void initialize(PApplet app) {
        if (INSTANCE == null) {
            INSTANCE = new ServiceManager(app);
        }
    }

    /**
     * Provides access to the singleton instance of ServiceManager.
     *
     * @return The shared instance of ServiceManager.
     */
    public static ServiceManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ServiceManager has not been initialized. Call initialize(app) first.");
        }
        return INSTANCE;
    }

    /**
     * Provides shared SettingsManager.
     *
     * @return The shared SettingsManager.
     */
    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    /**
     * Provides shared ScaleManager.
     *
     * @return The shared ScaleManager.
     */
    public ViewportManager getViewportManager() {
        return viewportManager;
    }

    /**
     * Provides shared InputManager.
     *
     * @return The shared InputManager.
     */
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * Provides shared SceneManager.
     *
     * @return The shared SceneManager.
     */
    public ScreenManager getScreenManager() {
        return screenManager;
    }

}
