package reimpl;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Reimplementation {

    private Reimplementation(){
    }

    public static <T, U> Stream<U> map(Stream<T> stream, Function<? super T, ? extends U> mapper) {
        return stream.flatMap(mapper.andThen(Stream::of));
    }

    public static <T> Stream<T> filter(Stream<T> stream, Predicate<? super T> condition) {
        return stream.flatMap(e -> condition.test(e) ? Stream.of(e) : Stream.empty());
    }

    public static <T> boolean allMatch(Stream<T> stream, Predicate<? super T> condition) {
        return !stream.filter(condition.negate()).findFirst().isPresent();
    }

    public static <T> boolean anyMatch(Stream<T> stream, Predicate<? super T> condition) {
        return stream.filter(condition).findFirst().isPresent();
    }

    public static <T, U> Stream<U> flatMap(Stream<T> stream, Function<? super T, Stream<U>> mapper) {
        return stream.map(mapper).reduce(Stream.empty(), Stream::concat);
    }

}
