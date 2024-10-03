package ru.otus.cache;

public interface SimpleCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(SimpleCacheListener<K, V> listener);

    void removeListener(SimpleCacheListener<K, V> listener);
}
