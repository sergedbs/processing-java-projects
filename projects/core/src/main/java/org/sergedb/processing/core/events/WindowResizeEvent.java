package org.sergedb.processing.core.events;

/**
 * WindowResizeEvent is published when the window size changes
 * after the user releases the mouse.
 */
public record WindowResizeEvent(int width, int height) {

    @Override
    public String toString() {
        return "WindowResizeEvent{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
