package org.sergedb.processing.core.events;

import org.sergedb.processing.core.utils.Hotkey;

/**
 * HotkeyEvent represents a triggered hotkey event.
 */
public record HotkeyEvent(Hotkey hotkey) {
    /**
     * Constructs a HotkeyEvent.
     *
     * @param hotkey The hotkey that was triggered.
     */
    public HotkeyEvent {
    }

    @Override
    public String toString() {
        return "HotkeyEvent{" +
                "hotkey=" + hotkey +
                '}';
    }
}
