package nl.cge.sbb.arch.control;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by chris on 10-05-17.
 */
public abstract class Mapper<E, T> {

    /**
     * @param e object to map
     * @return mapped object
     */
    public abstract T map (E e);

    public List<T> map(List<E> list) {
        return list.stream().map(e -> map(e)).collect(Collectors.toList());
    }
}
