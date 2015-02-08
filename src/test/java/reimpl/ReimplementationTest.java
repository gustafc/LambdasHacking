package reimpl;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class ReimplementationTest {

    Stream<Integer> oneTwoThreeFour() {
        return Stream.of(1, 2, 3, 4);
    }

    <T> void assertStreamIs(Stream<T> stream, T... expectedContent) {
        assertEquals(Arrays.asList(expectedContent), stream.collect(toList()));
    }

    @Test
    public void testMap() {
        Stream<String> mapped = Reimplementation.map(oneTwoThreeFour(), Object::toString);
        assertStreamIs(mapped, "1", "2", "3", "4");
    }

    @Test
    public void testFilter() {
        Stream<Integer> even = Reimplementation.filter(oneTwoThreeFour(), n -> n % 2 == 0);
        assertStreamIs(even, 2, 4);
    }

    @Test
    public void testAllMatch() {
        assertTrue("n<5", Reimplementation.allMatch(oneTwoThreeFour(), n -> n < 5));
        assertFalse("n<4", Reimplementation.allMatch(oneTwoThreeFour(), n -> n < 4));
    }


    @Test
    public void testAnyMatch() {
        assertTrue("n<5", Reimplementation.anyMatch(oneTwoThreeFour(), n -> n < 5));
        assertTrue("n<4", Reimplementation.anyMatch(oneTwoThreeFour(), n -> n < 4));
        assertTrue("n>1", Reimplementation.anyMatch(oneTwoThreeFour(), n -> n > 1));
        assertFalse("n=0", Reimplementation.anyMatch(oneTwoThreeFour(), n -> n == 0));
    }

    @Test
    public void testFlatMap() {
        Stream<Integer> mapped = Reimplementation.flatMap(oneTwoThreeFour(), n -> Collections.nCopies(n, n).stream());
        assertStreamIs(mapped,
                1,
                2, 2,
                3, 3, 3,
                4, 4, 4, 4);
    }
}