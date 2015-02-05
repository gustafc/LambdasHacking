package errorhandling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Instead of throwing exceptions, which complicates the control flow in functional programs (and Java's checked
 * exceptions means you <em>can't</em> throw any exception you like at any place you like!), this class provides an
 * alternative means of propagating errors. The idea is simply to represent the result of an operation as either the
 * value it produced, or the error that occurred, and allow the client code to not care about success or failure until
 * they need it.
 */
public class Try<T> {

    /**
     * Runs a block, and returns a <code>Try</code> instance wrapping the result. If {@link ThrowingSupplier#get()
     * block.get()} returns normally, the returned value is wrapped as a success; if it throws, the exception thrown is
     * wrapped as a failure.
     */
    public static <T> Try<T> executing(ThrowingSupplier<? extends T> block) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    /**
     * If {@code this} was a failure, returns an equivalent failure.
     * If {@code this} was successful, returns the current value mapped by the specified mapper.
     */
    public <U> Try<U> map(Function<? super T, ? extends U> mapper) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    /**
     * If {@code this} was a failure, returns an equivalent failure.
     * If {@code this} was successful, returns the value mapped by the specified mapper.
     */
    public <U> Try<U> flatMap(Function<? super T, Try<? extends U>> mapper) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    /**
     * If both {@code this} and {@code other} succeeded, join their value using the specified zipper.
     * If {@code this} failed, return a failure equivalent to  {@code this}.
     * If {@code this} succeeded but {@code other} failed, return a failure equivalent to {@code other}.
     */
    public <U, V> Try<V> zipWith(Try<U> other, BiFunction<? super T, ? super U, ? extends V> zipper) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    /**
     * Turns a "list of tries" into a "tried list". If all the elements in {@code tries} were successful, return a list
     * containing all the results in the same order. If one or more elements is a failure, return a failure equivalent
     * to the first failed element in {@code tries}.
     */
    public static <U> Try<List<U>> sequence(List<Try<U>> tries) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    // --- You should not need to edit the code below.

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Throwable;
    }

    private final T value;
    private final Throwable exception;

    public static <T> Try<T> forSuccess(T value) {
        return new Try<>(value, null);
    }

    public static <T> Try<T> forFailure(Throwable failure) {
        return new Try<>(null, failure);
    }

    /**
     * Should only be used by factory methods above.
     */
    private Try(T value, Throwable exception) {
        this.value = value;
        this.exception = exception;
        assert this.exception == null || this.value == null
                : "value should be null if exception isn't";
    }

    public boolean succeeded() {
        return !failed();
    }

    public boolean failed() {
        return exception != null;
    }

    public T get() {
        if (!succeeded()) throw new IllegalStateException("Getting value of a failed operation", exception);
        return value;
    }

    public Throwable getException() {
        if (!failed()) throw new IllegalStateException("Getting exception of a successful operation");
        return exception;
    }
}
