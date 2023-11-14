import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*
 * This is the class for individual accounts in the minitwitter
 * They can tweet and follow other people
 * checked
 */
public class User extends AbstractUser implements Subject {

    private static final List<String> POSITIVE_WORDS = Arrays.asList("good", "great", "excellent", "awesome");

    private Map<String, Observer> followers;
    private Map<String, Subject> following;
    private List<String> newsFeed;

    private String latestMessage;
    private int positiveMessageCount;

    public User(String id) {
        super(id);
        followers = new HashMap<String, Observer>();
        followers.put(this.getID(), this);
        following = new HashMap<String, Subject>();
        newsFeed = new ArrayList<String>();
    }

    /**
     * Returns the User followers of this AbstractUser.
     */
    public Map<String, Observer> getFollowers() {
        return followers;
    }

    /**
     * Returns the SingleUsers this AbstractUser is following.
     */
    public Map<String, Subject> getFollowing() {
        return following;
    }

    /**
     * Returns the news feed of this AbstractUser.
     */
    public List<String> getNewsFeed() {
        return newsFeed;
    }

    /**
     * Sends specified message to the news feeds of the
     * followers of this AbstractUser, checks if message is positive,
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
     * Returns the most recent message sent by this AbstractUser.
     */
    public String getLatestMessage() {
        return this.latestMessage;
    }

    /**
     * Returns the number of positive messages sent by
     * this AbstractUser.
     */
    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }

    /*
     * Composite methods
     */

    /**
     * Returns true if specified user ID matches
     * the user ID of this AbstractUser.
     */
    @Override
    public boolean contains(String id) {
        return this.getID().equals(id);
    }

    /**
     * Returns the total number of GroupUsers contained
     * in this AbstractUser.
     */
    @Override
    public int getGroupUserCount() {
        return 0;
    }

    /**
     * Returns the total number of SingleUsers contained
     * in this AbstractUser.
     */
    @Override
    public int getSingleUserCount() {
        return 1;
    }

    /*
     * Observer methods
     */

    /**
     * Updates the news feed of this AbstractUser with the most recent
     * message sent by the specified subject AbstractUser.
     */
    @Override
    public void update(Subject subject) {
        newsFeed.add(0, (((User) subject).getID() + ": " + ((User) subject).getLatestMessage()));
    }

    /*
     * Subject methods
     */

    /**
     * Adds the specified observer AbstractUser as a follower of
     * this subject AbstractUser.
     */
    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }

    /**
     * Updates the observer Users that are followers
     * of this subject AbstractUser.
     */
    @Override
    public void notifyObservers() {
        for (Observer obs : followers.values()) {
            obs.update(this);
        }
    }

    /*
     * Visitor methods
     */

    @Override
    public void accept(Visitor visitor) {
        visitor.visitSingleUser(this);
    }

    /*
     * Private methods
     */

    /**
     * Adds specified {@link AbstractUser} as follower.
     */
    private void addFollower(Observer user) {
        this.getFollowers().put(((AbstractUser) user).getID(), user);
        ((User) user).addUserToFollow(this);
    }

    /**
     * Adds specified {@link AbstractUser} (not user group) to follow.
     */
    private void addUserToFollow(Subject toFollow){
        if (toFollow.getClass() == User.class) {
            getFollowing().put(((AbstractUser) toFollow).getID(), toFollow);
        }
    }

    /**
     * Returns true if the specified message is positive.
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
