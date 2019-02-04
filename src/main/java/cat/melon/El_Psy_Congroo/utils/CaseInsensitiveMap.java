package cat.melon.el_psy_congroo.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SotrForgotten
 */
public class CaseInsensitiveMap<V> implements Map<String, V> {
    private Map<String, V> map;
    
    public CaseInsensitiveMap() {
        map = new HashMap<String, V>();
    }
    
    public CaseInsensitiveMap(boolean concurrent) {
        map = concurrent ? new ConcurrentHashMap<String, V>() : new HashMap<String, V>();
    }
    
    protected static String toLowerCase(Object s) {
        return ((String) s).toLowerCase();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(toLowerCase(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(toLowerCase(key));
    }

    @Override
    public V put(String key, V value) {
        return map.put(toLowerCase(key), value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(toLowerCase(key));
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        for (Entry<? extends String, ? extends V> e : m.entrySet()) {
            map.put(toLowerCase(e.getKey()), e.getValue());
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, V>> entrySet() {
        return map.entrySet();
    }
    
}
