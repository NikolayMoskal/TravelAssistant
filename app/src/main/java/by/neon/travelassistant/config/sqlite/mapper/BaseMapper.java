package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * The class for mapping from object of type {@link T} to object of type {@link S} and back.
 *
 * @param <T> the source type for mapping.
 * @param <S> the destination type for mapping.
 */
public abstract class BaseMapper<T, S> {
    /**
     * Converts the object from type {@link T} to type {@link S}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    public abstract S from(T source);

    /**
     * Converts the object from type {@link S} to type {@link T}.
     *
     * @param source the object to convert.
     * @return the converted object.
     */
    public abstract T to(S source);

    /**
     * Converts the list of objects from type {@link T} to type {@link S}.
     *
     * @param source the list of objects to convert.
     * @return the converted objects.
     */
    public List<S> from(List<T> source) {
        List<S> list = new ArrayList<>(0);
        for (T item : source) {
            list.add(from(item));
        }
        return list;
    }

    /**
     * Converts the list of objects from type {@link S} to type {@link T}.
     *
     * @param source the list of objects to convert.
     * @return the converted objects.
     */
    public List<T> to(List<S> source) {
        List<T> list = new ArrayList<>(0);
        for (S item : source) {
            list.add(to(item));
        }
        return list;
    }
}
