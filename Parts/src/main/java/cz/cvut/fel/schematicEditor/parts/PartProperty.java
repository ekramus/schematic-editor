package cz.cvut.fel.schematicEditor.parts;

/**
 * This class is holder of one property. PartProperty consists of property key and property value. PartProperty key identifies
 * property value, it is mostly declared as {@link String}.
 *
 * @param <K> key generic type.
 * @param <V> value generic type.
 * @author Urban Kravjansky
 */
public class PartProperty<K extends Comparable<K>, V> implements Comparable<PartProperty<K, V>> {
    /**
     * Key parameter.
     */
    private K      key;
    /**
     * Value parameter.
     */
    private V      value;
    /**
     * Description of property.
     */
    private String description;

    /**
     * Constructor for {@link PartProperty} class.
     *
     * @param key key of parameter.
     * @param value value assigned to given key.
     */
    public PartProperty(K key, V value) {
        setKey(key);
        setValue(value);
        setDescription("");
    }

    /**
     * Constructor for {@link PartProperty} class.
     *
     * @param key key of parameter.
     * @param value value assigned to given key.
     * @param description description of given property.
     */
    public PartProperty(K key, V value, String description) {
        setKey(key);
        setValue(value);
        setDescription(description);
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
     * @param property {@link PartProperty} instance, which is being compared to this.
     * @return value as defined in {@link Comparable}.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(PartProperty<K, V> property) {
        return getKey().compareTo(property.getKey());
    }

    /**
     * @param description the description to set
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
}
