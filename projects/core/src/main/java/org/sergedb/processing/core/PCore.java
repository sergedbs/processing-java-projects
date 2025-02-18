package org.sergedb.processing.core;

import org.sergedb.processing.core.events.*;
import org.sergedb.processing.core.managers.*;
import org.sergedb.processing.core.utils.*;
import processing.core.PApplet;

/**
 * PCore is the base class for Processing projects.
 * Now separates onRender (every frame) from onUpdate (tick-based) logic.
 */
public abstract class PCore extends PApplet {
    protected SettingsManager settings;
    protected ViewportManager viewportManager;
    protected InputManager inputManager;
    protected ScreenManager screenManager;

    private static final int TICK_RATE = 20;
    private long lastTickTime;

    public static void launch(Class<? extends PCore> appClass) {
        PApplet.main(appClass);
    }

    @Override
    public void settings() {
        ServiceManager.initialize(this);
        settings = ServiceManager.getInstance().getSettingsManager()
                .setSize(Constants.INITIAL_WIDTH, Constants.INITIAL_HEIGHT)
                .setPixelDensity(displayDensity())
                .setSmooth();
    }

    @Override
    public void setup() {
        loadManagersFromService();
        subscribeToGlobalEvents();
        lastTickTime = millis();
        onInitialize();
    }

    @Override
    public void draw() {
        background(0);
        long currentTime = millis();
        if (currentTime - lastTickTime >= 1000 / TICK_RATE) {
            onUpdate();
            lastTickTime = currentTime;
        }
        viewportManager.centerCanvas();
        viewportManager.applyScale();
        onRender();
        settings.displayUtilities(viewportManager);
    }

    private void loadManagersFromService() {
        ServiceManager service = ServiceManager.getInstance();
        viewportManager = service.getViewportManager();
        inputManager = service.getInputManager();
        settings = service.getSettingsManager();
        screenManager = service.getScreenManager();
    }

    private void subscribeToGlobalEvents() {
        EventBus eventBus = EventBus.getInstance();
        eventBus.subscribe(ScreenChangeEvent.class, event -> screenManager.setActiveScreen(event.newScreen()));
    }

    public void setScreen(Screen screen) {
        EventBus.getInstance().publish(new ScreenChangeEvent(screen));
    }

    @Override
    public void windowResized() {
        EventBus.getInstance().publish(new WindowResizeEvent(width, height));
    }

    @Override
    public void keyPressed() {
        inputManager.registerKeyEvent(true);
        inputManager.updateHotkeyStates();
    }

    @Override
    public void keyReleased() {
        inputManager.registerKeyEvent(false);
    }

    @Override
    public void mousePressed() {
        inputManager.registerMouseEvent(true);
    }

    @Override
    public void mouseDragged() {
        inputManager.registerMouseEvent(true);
    }

    @Override
    public void mouseReleased() {
        inputManager.registerMouseEvent(false);
    }

    /** Frame-based rendering logic. */
    protected abstract void onRender();

    /** Tick-based update logic. */
    protected abstract void onUpdate();

    /** Initialization logic. */
    protected abstract void onInitialize();
}
