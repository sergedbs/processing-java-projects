package org.sergedb.processing.core.utils;

import org.sergedb.processing.core.managers.InputManager;
import org.sergedb.processing.core.utils.Hotkey;

/**
 * HotkeyConfig registers the default hotkeys with proper state tracking.
 */
public class HotkeyConfig {

    /**
     * Registers the default hotkeys with InputManager using real-time conditions.
     */
    public static void registerDefaultHotkeys(InputManager inputManager) {
        inputManager.addHotkey(Hotkey.CONTROL_SHIFT_R, () ->
                inputManager.isControlPressed() &&
                inputManager.isShiftPressed() &&
                inputManager.isKeyActive('r'));

        inputManager.addHotkey(Hotkey.CONTROL_R, () ->
                inputManager.isControlPressed() &&
                inputManager.isKeyActive('r'));

        inputManager.addHotkey(Hotkey.CONTROL_F, () ->
                inputManager.isControlPressed() &&
                inputManager.isKeyActive('f'));

        inputManager.addHotkey(Hotkey.CONTROL_L, () ->
                inputManager.isControlPressed() &&
                inputManager.isKeyActive('l'));

        inputManager.addHotkey(Hotkey.CONTROL_G, () ->
                inputManager.isControlPressed() &&
                inputManager.isKeyActive('g'));

        inputManager.addHotkey(Hotkey.CONTROL_P, () ->
                inputManager.isControlPressed() &&
                inputManager.isKeyActive('p'));
    }
}
