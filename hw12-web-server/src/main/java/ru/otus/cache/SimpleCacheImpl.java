package ru.otus.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SimpleCacheImpl<K, V> implements SimpleCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<SimpleCacheListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        for (SimpleCacheListener<K, V> listener : listeners) {
            listener.notify(key, value, "PUT");
        }
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        if (!cache.containsKey(key)) {
            return;
        }
        for (SimpleCacheListener<K, V> listener : listeners) {
            listener.notify(key, cache.get(key), "REMOVE");
        }
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        for (SimpleCacheListener<K, V> listener : listeners) {
            listener.notify(key, cache.get(key), "GET");
        }
        return cache.get(key);
    }

    @Override
    public void addListener(SimpleCacheListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SimpleCacheListener<K, V> listener) {
        listeners.remove(listener);
    }
}
