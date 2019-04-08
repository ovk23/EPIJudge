package epi.systemDesign;

import java.util.*;
import java.util.stream.IntStream;

class RunningMedian {
    private List<Integer> integerList;
    private List<Double> runningMedian;

    private Queue<Integer> minHeap;
    private Queue<Integer> maxHeap;

    /**
     * initialize your data structure here.
     */
    RunningMedian() {
        this.integerList = new ArrayList<>();
        this.runningMedian = new ArrayList<>();

        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    void addNum(int num) {
        integerList.add(num);

        this.minHeap.add(num);
        this.maxHeap.add(this.minHeap.remove());

        if (this.maxHeap.size() > this.minHeap.size()) {
            this.minHeap.add(this.maxHeap.remove());
        }

        this.runningMedian.add(this.maxHeap.size() == this.minHeap.size()
                ? 0.5 * (this.maxHeap.peek() + this.minHeap.peek())
                : this.minHeap.peek().doubleValue()
        );
    }

    double findMedian() {
        return this.runningMedian.get(this.runningMedian.size() - 1);
    }
}

public class OnlineMedian {
    public static void main(String[] args) {
        RunningMedian runningMedian = new RunningMedian();

        Random r = new Random();

        IntStream
                .range(1, 20)
                .forEach(i -> {
                    runningMedian.addNum(r.nextInt());
                    System.out.println(runningMedian.findMedian());
                });
    }
}