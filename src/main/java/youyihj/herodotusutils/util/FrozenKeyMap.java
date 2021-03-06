package youyihj.herodotusutils.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FrozenKeyMap<K, V> implements Map<K, V> {

    private final Map<K, V> delegate;
    private final Set<K> frozenKeys;

    public FrozenKeyMap(Map<K, V> delegate) {
        this.delegate = delegate;
        this.frozenKeys = new HashSet<>();
    }

    public static <K, V> FrozenKeyMap<K, V> of(Map<K, V> map) {
        return new FrozenKeyMap<>(map);
    }

    public FrozenKeyMap<K, V> putFrozenEntry(K key, V value) {
        delegate.put(key, value);
        frozenKeys.add(key);
        return this;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return delegate.get(key);
    }

    @Override
    public V put(K key, V value) {
        if (!frozenKeys.contains(key)) {
            return delegate.put(key, value);
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        if (!frozenKeys.contains(key)) {
            return delegate.remove(key);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            if (!frozenKeys.contains(entry.getKey())) {
                delegate.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Set<K> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<V> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }
}
