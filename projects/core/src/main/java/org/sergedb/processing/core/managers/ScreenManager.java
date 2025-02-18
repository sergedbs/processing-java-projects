package org.sergedb.processing.core.managers;

import processing.core.PApplet;
import java.util.Stack;

/**
 * ScreenManager handles screen transitions and management.
 * Supports both single-screen mode and screen stacking.
 */
public class ScreenManager {
    private final Stack<Screen> screenStack = new Stack<>();

    /** Pushes a new screen onto the stack and initializes it. */
    public void pushScreen(Screen screen) {
        screenStack.push(screen);
        screen.onInitialize();
    }

    /** Removes the current screen from the stack and disposes it. */
    public void popScreen() {
        if (!screenStack.isEmpty()) {
            screenStack.pop().onDispose();
        }
    }

    /** Replaces the current screen with a new one. */
    public void setActiveScreen(Screen screen) {
        if (!screenStack.isEmpty()) {
            screenStack.pop().onDispose();
        }
        pushScreen(screen);
    }

    /** Updates the current active screen. */
    public void update() {
        if (!screenStack.isEmpty()) {
            screenStack.peek().onUpdate();
        }
    }

    /** Renders the current active screen. */
    public void render() {
        if (!screenStack.isEmpty()) {
            screenStack.peek().onRender();
        }
    }

    /** Checks if an active screen exists. */
    public boolean hasActiveScreen() {
        return !screenStack.isEmpty();
    }
}
