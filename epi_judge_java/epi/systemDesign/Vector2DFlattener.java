package epi.systemDesign;


import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector2DFlattener {
    IntStream intStream;
    PrimitiveIterator.OfInt iterator;

    public Vector2DFlattener(int[][] v) {
        this.intStream = Stream.of(v).flatMapToInt(Arrays::stream);
        this.iterator = this.intStream.iterator();
    }

    public int next() {
        return iterator.nextInt();
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }
}
