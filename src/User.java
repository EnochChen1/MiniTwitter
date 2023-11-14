import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*
 * This is the class for individual accounts in the minitwitter
 * They can tweet and follow other people
 * 
 * The Observer design pattern is implemented here
 * The newsFeed gets updated immediately whenever the user
 * or whomever they followed posts a tweet
 */
public class User extends AbstractUser implements Subject {

    private static final List<String> POSITIVE_WORDS = Arrays.asList("good", "great", "excellent",
     "awesome", "nice", "amazing", "beautiful", "cool", "like", "love");

    private Map<String, Observer> followers;
    private Map<String, Subject> following;
    private List<String> newsFeed;

    private String latestMessage;
    private int positiveMessageCount;

    public User(String userName) {
        super(userName);
        followers = new HashMap<String, Observer>();
        followers.put(this.getUserName(), this);
        following = new HashMap<String, Subject>();
        newsFeed = new ArrayList<String>();
    }

    /**
     * Returns the User followers of this User.
     */
    public Map<String, Observer> getFollowers() {
        return followers;
    }

    /**
     * Returns the Users this User is following.
     */
    public Map<String, Subject> getFollowing() {
        return following;
    }

    /**
     * Returns the news feed of this User.
     */
    public List<String> getNewsFeed() {
        return newsFeed;
    }

    /**
     * Sends specified message to the news feeds of the
     * followers of this User, checks if message is positive,
     */
    public void sendMessage(String message) {
        this.latestMessage = message;
        this.setMessageCount(this.getMessageCount() + 1);

        if (isPositiveMessage(message)) {
            ++positiveMessageCount;
        }

        notifyObservers();
    }

    /**
     * Returns the most recent message sent by this User.
     */
    public String getLatestMessage() {
        return this.latestMessage;
    }

    /**
     * Returns the number of positive messages sent by
     * this User.
     */
    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }

    /**
     * Returns true if specified user ID matches
     * the user ID of this User.
     */
    @Override
    public boolean contains(String userName) {
        return this.getUserName().equals(userName);
    }

    /**
     * Returns the groups in this user, none
     */
    @Override
    public int getGroupCount() {
        return 0;
    }

    /**
     * Returns the total number of Users this is, one
     */
    @Override
    public int getUserCount() {
        return 1;
    }

  
    /**
     * Updates the news feed of this User with the most recent
     * message sent by a User this User follows.
     */
    @Override
    public void update(Subject subject) {
        newsFeed.add(0, "Posted by "+(((User) subject).getUserName() + ": " + ((User) subject).getLatestMessage()));
    }


    /**
     * Adds the specified observer User as a follower of
     * this subject User.
     */
    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }

    /**
     * Updates the observer Users that are followers
     * of this subject User.
     */
    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitSingleUser(this);
    }


    /**
     * Adds specified User as follower.
     * Adds Follower with their username and their observer
     */
    private void addFollower(Observer user) {
        this.getFollowers().put(((AbstractUser) user).getUserName(), user);
        ((User) user).addUserToFollow(this);
    }

    /**
     * Adds specified User (not user group) to follow and keep track of.
     */
    private void addUserToFollow(Subject toFollow){
        if (toFollow.getClass() == User.class) {
            getFollowing().put(((AbstractUser) toFollow).getUserName(), toFollow);
        }
    }

    /**
     * Returns true if the specified message is positive.
     * Needed for Positive Percentage
     */
    private boolean isPositiveMessage(String message) {
        boolean positive = false;
        message = message.toLowerCase();
        for (String word : POSITIVE_WORDS) {
            if (message.contains(word)) {
                positive = true;
            }
        }
        return positive;
    }

}
