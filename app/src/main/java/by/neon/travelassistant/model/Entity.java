package by.neon.travelassistant.model;

/**
 * The base model.
 */
public abstract class Entity {
    /**
     * The ID of this model.
     */
    private long id;

    /**
     * Gets the ID.
     *
     * @return the ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the ID.
     */
    public void setId(long id) {
        this.id = id;
    }
}
