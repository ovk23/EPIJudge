package epi.graph;

import java.util.*;

class MatchResult {
    public String winner;
    public String loser;

    public MatchResult(String w, String l) {
        winner = w;
        loser = l;
    }
}

public class MatchResults {

    Map<String, Set<String>> buildGraph(List<MatchResult> matchResults) {
        Map<String, Set<String>> graph = new HashMap<>();

        for (MatchResult result : matchResults) {
            Objects.requireNonNull(graph.putIfAbsent(result.winner, new HashSet<>()))
                    .add(result.loser);
        }

        return graph;
    }

    boolean isReachableDfs(Map<String, Set<String>> graph, String curr, String dest, Set<String> visited) {
        if (curr.equals(dest)) {
            return true;
        } else if (visited.contains(curr) || graph.get(curr) == null) return false;

        visited.add(curr);

        return graph.get(curr)
                .stream()
                .anyMatch(team -> isReachableDfs(graph, team, dest, visited));
    }

    public boolean canTeamABeatTeamB(List<MatchResult> matches,
                                     String teamA, String teamB) {
        return isReachableDfs(buildGraph(matches), teamA, teamB, new HashSet<>());
    }
}
