package cz.cvut.fel.schematicEditor.support;

/**
 * This class is holder of one property. Property consists of property key and property value. Property key identifies
 * property value, it is mostly declared as {@link String}.
 *
 * @param <K> key generic type.
 * @param <V> value generic type.
 * @author Urban Kravjansky
 */
public class Property<K extends Comparable<K>, V> implements Comparable<Property<K, V>> {
    /**
     * Key parameter.
     */
    private K key;
    /**
     * Value parameter.
     */
    private V value;

    /**
     * Constructor for {@link Property} class.
     *
     * @param key key of parameter.
     * @param value value assigned to given key.
     */
    public Property(K key, V value) {
        setKey(key);
        setValue(value);
    }

    /**
     * @param key the key to set
     */
    private void setKey(K key) {
        this.key = key;
    }

    /**
     * @return the key
     */
    public K getKey() {
        return this.key;
    }

    /**
     * @param value the value to set
     */
    private void setValue(V value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public V getValue() {
        return this.value;
    }

    /**
     * @param property {@link Property} instance, which is being compared to this.
     * @return value as defined in {@link Comparable}.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Property<K, V> property) {
        return getKey().compareTo(property.getKey());
    }
}
