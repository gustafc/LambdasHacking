package errorhandling;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class TryTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testSuccessfulExecution() {
        Try<String> result = Try.executing(() -> "Great success!");
        assertEquals("Great success!", result.get());
    }

    @Test
    public void testFailingExecution() {
        NoSuchElementException failure = new NoSuchElementException("Big trouble!");
        Try<Object> result = Try.executing(() -> {
            throw failure;
        });
        assertSame(failure, result.getException());
    }

    @Test
    public void testMapSuccessful() {
        Try<String> success = Try.forSuccess("banana").map(String::toUpperCase);
        assertEquals("BANANA", success.get());
    }

    @Test
    public void testMapFailed() {
        NoSuchElementException exception = new NoSuchElementException();
        Try<String> failure = Try.<String>forFailure(exception).map(String::toUpperCase);
        assertSame(exception, failure.getException());
    }

    @Test
    public void testFlatMapSuccessful() {
        Try<String> original = Try.forSuccess("banana");
        Try<String> success = original.flatMap(s -> Try.executing(() -> s.toUpperCase()));
        assertEquals("BANANA", success.get());
    }

    @Test
    public void testFlatMapFailed() {
        Try<String> original = Try.forSuccess("banana");
        Try<Integer> failure = original.flatMap(s -> Try.executing(() -> Integer.parseInt(s)));
        assertEquals(NumberFormatException.class, failure.getException().getClass());
    }

    @Test
    public void testZipWith() {
        Try<String> ok1 = Try.forSuccess("1");
        Try<String> ok2 = Try.forSuccess("2");
        Try<String> err1 = Try.forFailure(new NumberFormatException());
        Try<String> err2 = Try.forFailure(new NoSuchElementException());
        assertEquals("12", ok1.zipWith(ok2, String::concat).get());
        assertSame(err2.getException(), ok1.zipWith(err2, String::concat).getException());
        assertSame(err1.getException(), err1.zipWith(err2, String::concat).getException());
    }

    @Test
    public void testSequenceSuccessful(){
        Try<List<String>> sequence = Try.sequence(Stream.of("a", "b", "c")
                .map(Try::forSuccess)
                .collect(toList()));
        assertEquals(Arrays.asList("a", "b", "c"), sequence.get());
    }

    @Test
    public void testSequenceFailed(){
        Try<List<Integer>> sequence = Try.sequence(Stream.of("1", "2", "three")
                .map(s -> Try.executing(() -> Integer.parseInt(s)))
                .collect(toList()));
        assertEquals("For input string: \"three\"", sequence.getException().getMessage());
    }

    // -- Basic sanity checks below

    @Test
    public void testForSuccess() {
        Try<String> t = Try.forSuccess("Hello, try!");
        assertTrue(t.succeeded() && !t.failed());
        assertEquals("Hello, try!", t.get());
        expectedException.expect(IllegalStateException.class);
        t.getException();
    }

    @Test
    public void testForFailure() {
        NoSuchElementException failure = new NoSuchElementException();
        Try<String> t = Try.forFailure(failure);
        assertTrue(t.failed() && !t.succeeded());
        assertSame(failure, t.getException());
        expectedException.expect(IllegalStateException.class);
        t.get();
    }

}
