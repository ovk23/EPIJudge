package epi.systemDesign;

import java.util.*;

class Tweet {
    public int id;
    public int time;

    public Tweet(int id, int time) {
        this.id = id;
        this.time = time;
    }
}

class User {
    private Set<Integer> followees_ = new HashSet<>();
    private List<Tweet> tweets_ = new ArrayList<>();

    public void addFollowee(int followeeId) {
        followees_.add(followeeId);
    }

    public void addTweet(int tweetId, int time) {
        tweets_.add(new Tweet(tweetId, time));
    }

    public void removeFollowee(int followeeId) {
        followees_.remove(followeeId);
    }

    public Set<Integer> getFollowees_() {
        return followees_;
    }

    public List<Tweet> getTweets() {
        return tweets_;
    }
}

public class Twitter {
    private int time_ = 0;
    private Map<Integer, User> users_ = new HashMap<>();
    private final int MAX_TWEETS = 10;

    public Twitter() {
    }

    public void postTweet(int userId, int tweetId) {
        users_.computeIfAbsent(userId, k -> new User());
        User user = users_.get(userId);
        user.addTweet(tweetId, time_++);
    }

    public List<Integer> getNewsFeed(int userId) {
        User user = users_.get(userId);

        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> queue = new PriorityQueue<>((t1, t2) -> t2.time - t1.time);

        if (user == null) {
            return result;
        } else {
            queue.addAll(user.getTweets());
            user.getFollowees_().forEach(id -> queue.addAll(users_.get(id).getTweets()));

            while (!queue.isEmpty() && result.size() <= MAX_TWEETS)
                result.add(queue.poll().id);

            return result;
        }
    }

    public void follow(int followerId, int followeeId) {
        users_.computeIfAbsent(followerId, k -> new User());
        users_.computeIfAbsent(followeeId, k -> new User());
        users_.get(followerId).addFollowee(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        Arrays.asList(followeeId, followerId)
                .forEach(id -> users_.computeIfAbsent(id, k -> new User()));
        users_.get(followerId).removeFollowee(followeeId);
    }
}