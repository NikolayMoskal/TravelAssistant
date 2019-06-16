package by.neon.travelassistant.activity;

/**
 * Represents a simple method that can take one argument of type {@link T} and returns a value of type {@link R}
 *
 * @param <T> the type of input argument of this method.
 * @param <R> the type of return value of this method.
 */
public interface IFunc<T, R> {
    /**
     * Runs a method. Method can be passed via lambda or via implementation of this interface.
     *
     * @param item the parameter.
     * @return a some value.
     */
    R run(T item);
}
