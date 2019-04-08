package epi.systemDesign;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {
    Map<String, TreeMap<Integer, String>> map = new HashMap<>();

    public void set(String key, String value, Integer ts) {
        map.computeIfAbsent(key, k -> new TreeMap<>())
                .put(ts, value);
    }

    public String get(String key, int timestamp) {
        return map.containsKey(key)
                ? map
                .computeIfAbsent(key, k -> new TreeMap<>())
                .floorEntry(timestamp)
                .getValue()
                : "";
    }

    public static void main(String[] args) {
        TimeMap kv = new TimeMap();

        String abc = "abc";

        System.out.println(abc.chars().count());
        kv.set("foo", "bar", 1);
        System.out.println(kv.get("foo", 1));
        System.out.println(kv.get("foo", 3)); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"
        kv.set("foo", "bar2", 4);
        System.out.println(kv.get("foo", 4)); // output "bar2"
        System.out.println(kv.get("fff", 5)); //output "bar2"
    }
}
