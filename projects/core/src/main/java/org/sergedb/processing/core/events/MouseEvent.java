package org.sergedb.processing.core.events;

/**
 * MouseEvent represents a mouse action event.
 */
public record MouseEvent(int x, int y, boolean isPressed) {
    /**
     * Constructs a MouseEvent.
     *
     * @param x         The x-coordinate of the mouse.
     * @param y         The y-coordinate of the mouse.
     * @param isPressed True if the mouse button is pressed, false otherwise.
     */
    public MouseEvent {
    }

    @Override
    public String toString() {
        return "MouseEvent{" +
                "x=" + x +
                ", y=" + y +
                ", isPressed=" + isPressed +
                '}';
    }
}
