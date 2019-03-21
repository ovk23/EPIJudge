package epi.dynamicProgramming;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.*;


class User implements PropertyChangeListener {

    private final String CHANGE_FIELD = "latestTweetId";
    int userId;
    List<Integer> tweets;
    int latestTweetId;
    private PropertyChangeSupport support;

    public User(int userId){
        this.userId = userId;
        tweets = new ArrayList<>();
        support = new PropertyChangeSupport(this);
    }

    public User(int userId, int tweetId){
        this.userId = userId;
        tweets = new ArrayList<>(tweetId);
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl){
        this.support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl){
        this.support.removePropertyChangeListener(pcl);
    }

    public void setTweet(int tweetId){
        this.support.firePropertyChange(CHANGE_FIELD, this.latestTweetId, tweetId);
        this.latestTweetId = tweetId;
        this.tweets.add(tweetId);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        int id = Integer.parseInt(evt.getNewValue().toString());
        this.tweets.add(id);
        this.latestTweetId = id;
    }

    public List<Integer> getTweets(){
        return this.tweets;
    }
}

class Twitter {
    Map<Integer, User> users;

    /** Initialize your data structure here. */
    public Twitter() {
        users = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        User user = this.users.containsKey(userId)
                ?   this.users.get(userId)
                :   new User(userId, tweetId);

        user.setTweet(tweetId);
        this.users.put(userId, user);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> tweets = this.users.get(userId).getTweets();
        int n = tweets.size();
        if(tweets != null){
            if(n <= 10) return tweets;
            else{
                List<Integer> top10 = new ArrayList<>();
                for(int i = n - 1; i >= n - 10; i--){
                    top10.add(tweets.get(i));
                }
                return top10;
            }
        }else return null;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        User follower, followee;

        if(this.users.containsKey(followerId)){
            follower = this.users.get(followerId);
        }else{
            follower = new User(followerId);
            this.users.put(followerId, follower);
        }

        if(this.users.containsKey(followeeId)){
            followee = this.users.get(followeeId);
        }else{
            followee = new User(followeeId);
            this.users.put(followeeId, followee);
        }

        followee.addPropertyChangeListener(follower);
        this.users.put(followeeId, followee);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        User follower = this.users.get(followerId);
        User followee = this.users.get(followeeId);
        if(follower != null && followee != null){
            followee.removePropertyChangeListener(follower);
        }
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();

        twitter.postTweet(1, 5);

        System.out.println(twitter.getNewsFeed(1).toString());

        twitter.follow(1, 2);

        twitter.postTweet(2, 6);

        System.out.println(twitter.getNewsFeed(1).toString());

        twitter.unfollow(1, 2);

        System.out.println(twitter.getNewsFeed(1).toString());
    }
}