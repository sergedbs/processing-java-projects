package org.sergedb.processing.core.managers;

import org.sergedb.processing.core.events.EventBus;
import org.sergedb.processing.core.events.WindowResizeEvent;
import processing.core.PApplet;
import org.sergedb.processing.core.utils.Constants;

/**
 * ViewportManager manages screen scaling, viewport adjustments,
 * and centralized scaling for the entire canvas.
 */
public class ViewportManager {
    private final PApplet app;
    private float scaleFactor = 1.0f;
    private boolean isScalingEnabled = true;
    private boolean isCanvasCentered = true;
    private final float baseAspectRatio;

    /**
     * Constructs a ViewportManager with the provided PApplet.
     * Automatically applies scaling.
     */
    public ViewportManager(PApplet app) {
        this.app = app;
        subscribeToResizeEvents();
        this.baseAspectRatio = (float) Constants.BASE_WIDTH / Constants.BASE_HEIGHT;
        adjustScale(Constants.INITIAL_WIDTH, Constants.INITIAL_HEIGHT);
    }

    public void subscribeToResizeEvents() {
    EventBus.getInstance().subscribe(WindowResizeEvent.class, event -> {
        adjustScale(event.width(), event.height());

    });
}

    /**
     * Adjusts the scale factor based on the current window size.
     */
    public void adjustScale(int windowWidth, int windowHeight) {
        System.out.println(windowHeight + " " + windowWidth);
        float windowAspectRatio = (float) windowWidth / windowHeight;
        scaleFactor = (windowAspectRatio > baseAspectRatio)
                ? (float) windowHeight / Constants.BASE_HEIGHT
                : (float) windowWidth / Constants.BASE_WIDTH;

        System.out.println(scaleFactor);
    }

    /**
     * Applies the current scale globally if scaling is enabled.
     */
    public void applyScale() {
        if (isScalingEnabled) {
            app.scale(scaleFactor);
        }
    }

    /**
     * Centers the canvas if centering is enabled.
     */
    public void centerCanvas() {
        if (isCanvasCentered) {
            app.translate((float) app.width / 2, (float) app.height / 2);
        }
    }

    /**
     * Toggles global scaling on or off.
     */
    public void setScalingEnabled(boolean enabled) {
        isScalingEnabled = enabled;
    }

    /**
     * Toggles canvas centering on or off.
     */
    public void setCanvasCentered(boolean centered) {
        isCanvasCentered = centered;
    }

    /**
     * Scales a given value according to the current scale factor.
     */
    public float scale(float value) {
        return isScalingEnabled ? value * scaleFactor : value;
    }

    /**
     * Returns the current scale factor.
     */
    public float getScaleFactor() {
        return scaleFactor;
    }

    /**
     * Scales a value without applying the global scaling factor.
     */
    public float noScale(float value) {
        return isScalingEnabled ? (value / scaleFactor) : value;
    }

    public float noCenterX(float value) {
        return isCanvasCentered ? (value - app.width / 2f) : value;
    }

    public float noCenterY(float value) {
        return isCanvasCentered ? (value - app.height / 2f) : value;
    }
}
