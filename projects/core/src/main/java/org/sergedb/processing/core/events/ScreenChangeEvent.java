package org.sergedb.processing.core.events;

import org.sergedb.processing.core.managers.Screen;

/**
 * ScreenChangeEvent is published to signal a change in the active screen.
 */
public record ScreenChangeEvent(Screen newScreen) {

    public ScreenChangeEvent {
    }
}
