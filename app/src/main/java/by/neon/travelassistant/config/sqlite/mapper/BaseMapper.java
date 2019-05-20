package by.neon.travelassistant.config.sqlite.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper<T, S> {
    public abstract S from(T source);

    public abstract T to(S source);

    public List<S> from(List<T> source) {
        List<S> list = new ArrayList<>(0);
        for (T item : source) {
            list.add(from(item));
        }
        return list;
    }

    public List<T> to(List<S> source) {
        List<T> list = new ArrayList<>(0);
        for (S item : source) {
            list.add(to(item));
        }
        return list;
    }
}
