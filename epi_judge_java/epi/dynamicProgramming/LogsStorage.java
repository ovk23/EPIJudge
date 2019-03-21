package epi.dynamicProgramming;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


public class LogsStorage {
    static class LogSystem {
        Map<String, TreeMap<Long, Integer>> logsDB;
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss");

        String startDate = "2000:01:01:00:00:00";

        public LogSystem() {
            logsDB = new HashMap<>();
        }

        public void put(int id, String timestamp) {
            String year = getYear(timestamp);
            Long seconds = getSeconds(timestamp);

            TreeMap<Long, Integer> newMap =  this.logsDB.containsKey(year)
                ?   this.logsDB.get(year)
                    :   new TreeMap<>();

            newMap.put(seconds, id);
            this.logsDB.put(year, newMap);
        }

        public List<Integer> retrieve(String s, String e, String gra) {
            String startYear = getYear(s);
            String endYear = getYear(e);

            Long startSeconds = getSeconds(s);
            Long endSeconds = getSeconds(e);

            if(startYear.equals(endYear)){
                Map<Long, Integer> map = this.logsDB.get(startYear);

                return map.entrySet().stream()
                        .filter(longIntegerEntry ->
                                longIntegerEntry.getKey() > startSeconds &&
                                        longIntegerEntry.getKey() < endSeconds)
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
            }else{
                ArrayList<Integer> ids = new ArrayList<>(this.logsDB.get(startYear).entrySet().stream()
                        .filter(entry -> entry.getKey() >= startSeconds)
                        .map(Map.Entry::getValue).collect(Collectors.toList()));

                ids.addAll(this.logsDB.get(endYear).entrySet().stream()
                        .filter(entry -> entry.getKey() <= endSeconds)
                        .map(Map.Entry::getValue).collect(Collectors.toList()));

                return ids;
            }
        }

        private String getYear(String s){
            return s.split(":")[0];
        }

        private Long getSeconds(String s){
            return LocalDateTime
                    .parse(s, formatter)
                    .toEpochSecond(ZoneOffset.UTC) - LocalDateTime
                    .parse(s, formatter)
                    .toEpochSecond(ZoneOffset.UTC);
        }
    }

    public static void main(String[] args) {
        LogSystem system = new LogSystem();
        system.put(1, "2017:01:01:23:59:59");
        system.put(2, "2017:01:01:22:59:59");
        system.put(3, "2016:01:01:00:00:00");

        System.out.println(system.retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year")); // return [1,2,3], because you need to return all logs within 2016 and 2017.

    }
}
