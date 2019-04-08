package epi.systemDesign;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class LogsStorage {
    static class LogSystem {
        Map<Integer, String> map = new HashMap<>();

        public enum Index {
            Year(4), Month(7), Day(10), Hour(13), Minute(16), Second(19);
            int idx;

            Index(int idx) {
                this.idx = idx;
            }
        }

        public void put(int id, String timestamp) {
            map.put(id, timestamp);
        }

        public List<Integer> retrieve(String s, String e, String gra) {
            Function<String, String> subStrFn = str ->
                    str.substring(0, Integer.valueOf(Index.valueOf(gra).idx));
            return map
                    .entrySet()
                    .stream()
                    .filter(ts ->
                            subStrFn.apply(ts.getValue())
                                    .compareTo(subStrFn.apply(s)) >= 0 &&
                                    subStrFn.apply(ts.getValue()).compareTo(subStrFn.apply(e)) <= 0
                    ).map(Map.Entry::getKey).collect(toList());
        }
    }

    public static void main(String[] args) {
        LogSystem system = new LogSystem();
        system.put(1, "2017:01:01:23:59:59");
        system.put(2, "2017:01:01:22:59:59");
        system.put(3, "2016:01:01:00:00:00");

        System.out.println(system.retrieve("2016:01:01:01:01:01", "2017:01:01:23:00:00", "Year")); // return [1,2,3], because you need to return all logs within 2016 and 2017.

    }
}
