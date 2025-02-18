package org.sergedb.processing.core.events;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * EventBus is a simple publish-subscribe system for event-driven communication.
 */
public class EventBus {
    private static final EventBus instance = new EventBus();
    private final Map<Class<?>, List<Consumer<?>>> listeners = new ConcurrentHashMap<>();

    /**
     * Returns the singleton instance of EventBus.
     */
    public static EventBus getInstance() {
        return instance;
    }

    /**
     * Subscribes a listener to a specific event type.
     */
    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, key -> new ArrayList<>()).add(listener);
    }

    /**
     * Publishes an event to all registered listeners.
     */
    public <T> void publish(T event) {
        List<Consumer<?>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<?> listener : eventListeners) {
                @SuppressWarnings("unchecked")
                Consumer<T> typedListener = (Consumer<T>) listener;
                typedListener.accept(event);
            }
        }
    }

    /**
     * Unsubscribes a listener from a specific event type.
     */
    public <T> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<?>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }
}
