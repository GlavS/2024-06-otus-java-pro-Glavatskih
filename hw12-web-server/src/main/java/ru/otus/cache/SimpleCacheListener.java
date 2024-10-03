package ru.otus.cache;

public interface SimpleCacheListener<K, V> {
    void notify(K key, V value, String action);
}
