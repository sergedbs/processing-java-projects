package org.sergedb.processing.core.managers;

import org.sergedb.processing.core.events.*;
import org.sergedb.processing.core.utils.*;
import processing.core.PApplet;

import java.util.*;
import java.util.function.Supplier;

/**
 * InputManager handles keyboard and mouse inputs, manages hotkey states,
 * and integrates with EventBus for event-driven input handling.
 */
public class InputManager {
    private static final int KEY_CMD = 157;  // Command key (Mac)
    private static final int KEY_CTRL = 17;  // Control key (Windows)
    private static final int KEY_SHIFT = 16; // Shift key

    private boolean mousePressed = false;
    private int mouseX;
    private int mouseY;

    private final PApplet app;
    private final Set<Integer> activeKeys = new HashSet<>();
    private final EnumMap<Hotkey, Supplier<Boolean>> hotkeys = new EnumMap<>(Hotkey.class);

    public InputManager(PApplet app) {
        this.app = app;
        subscribeToEvents();
        HotkeyConfig.registerDefaultHotkeys(this);
    }

    private int getKeyIdentifier() {
        return (app.key == PApplet.CODED) ? app.keyCode : Character.toLowerCase((int) app.key);
    }

    /** Publishes a KeyEvent on key press. */
    public void registerKeyEvent(boolean pressed) {
        int keyIdentifier = getKeyIdentifier();
        if (pressed) {
            EventBus.getInstance().publish(new KeyEvent(app.key, keyIdentifier, true));
            activeKeys.add(keyIdentifier);
        } else {
            EventBus.getInstance().publish(new KeyEvent(app.key, keyIdentifier, false));
            activeKeys.remove(keyIdentifier);
        }
    }

    /** Updates hotkey states and publishes HotkeyEvents only on state changes. */
    public void updateHotkeyStates() {
        hotkeys.forEach((hotkey, condition) -> {
            boolean isActive = condition.get();
            if (isActive) {
                EventBus.getInstance().publish(new HotkeyEvent(hotkey));
            }
        });
    }

    /** Subscribes to global input-related events from EventBus. */
    private void subscribeToEvents() {
        EventBus.getInstance().subscribe(KeyEvent.class, event -> {
            if (event.isPressed()) activeKeys.add(event.keyCode());
            else activeKeys.remove(event.keyCode());
        });
    }

    /** Checks if a specific key is currently active. */
    public boolean isKeyActive(int keyCode) {
        return activeKeys.contains(keyCode);
    }

    /** Checks if Control (CMD/CTRL) is pressed. */
    public boolean isControlPressed() {
        return activeKeys.contains(KEY_CMD) || activeKeys.contains(KEY_CTRL);
    }

    /** Checks if Shift is pressed. */
    public boolean isShiftPressed() {
        return activeKeys.contains(KEY_SHIFT);
    }

    /** Adds a custom hotkey. */
    public void addHotkey(Hotkey hotkey, Supplier<Boolean> condition) {
        hotkeys.put(hotkey, condition);
    }

    /** Removes a custom hotkey. */
    public void removeHotkey(Hotkey hotkey) {
        hotkeys.remove(hotkey);
    }

    /** Publishes a MouseEvent on mouse press. */
    public void registerMouseEvent(boolean pressed) {
        mousePressed = pressed;
        mouseX = app.mouseX;
        mouseY = app.mouseY;
        EventBus.getInstance().publish(new MouseEvent(mouseX, mouseY, pressed));
    }
}
