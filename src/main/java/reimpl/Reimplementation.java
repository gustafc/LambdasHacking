package reimpl;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Reimplementation {

    private Reimplementation(){
    }

    public static <T, U> Stream<U> map(Stream<T> stream, Function<? super T, ? extends U> mapper) {
        // TODO: Rewrite this method without using stream.map
        return stream.map(mapper);
    }

    public static <T> Stream<T> filter(Stream<T> stream, Predicate<? super T> condition) {
        // TODO: Rewrite this method without using stream.filter
        return stream.filter(condition);
    }

    public static <T> boolean allMatch(Stream<T> stream, Predicate<? super T> condition) {
        // TODO: Rewrite this method without using stream.allMatch
        return stream.allMatch(condition);
    }

    public static <T> boolean anyMatch(Stream<T> stream, Predicate<? super T> condition) {
        // TODO: Rewrite this method without using stream.anyMatch
        return stream.anyMatch(condition);
    }

    public static <T, U> Stream<U> flatMap(Stream<T> stream, Function<? super T, Stream<U>> mapper) {
        // TODO: Rewrite this method without using stream.flatMap
        return stream.flatMap(mapper);
    }

}
