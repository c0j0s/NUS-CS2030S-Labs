import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

class KeyableMap<V extends Keyable> implements Keyable {
    private final String key;
    private final Map<String, V> map;

    KeyableMap(String key, Map<String, V> m) {
        this.key = key;
        this.map = m;
    }

    KeyableMap(KeyableMap<V> k) {
        this(k.key, k.map);
    }

    KeyableMap(String key) {
        this(key, new HashMap<String, V>());
    }

    Optional<V> get(String key) {
        return Optional.<V>ofNullable(map.get(key));
    }

    KeyableMap<V> put(V item) {
        map.put(item.getKey(), item);
        return new KeyableMap<V>(this);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, V> e : map.entrySet()) {
            sb.append(e.getValue() + ", ");
        }
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return String.format("%s: {%s}", key, sb);
    }
}
