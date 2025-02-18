package org.sergedb.processing.core.events;

/**
 * KeyEvent represents a keyboard event, including the pressed key and its state.
 */
public record KeyEvent(char key, int keyCode, boolean isPressed) {
    /**
     * Constructs a KeyEvent.
     *
     * @param key       The character representation of the key.
     * @param keyCode   The key code of the key.
     * @param isPressed True if the key is pressed, false if released.
     */
    public KeyEvent {
    }

    @Override
    public String toString() {
        return "KeyEvent{" +
                "key=" + key +
                ", keyCode=" + keyCode +
                ", isPressed=" + isPressed +
                '}';
    }
}
