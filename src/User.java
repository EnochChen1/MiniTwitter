import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class User extends AbstractUser implements Subject {
    private static final List<String> POSITIVE_WORDS = Arrays.asList("good","great", "excellent"
    , "nice", "awesome");

    private Map<String, Observer> followers;
    private Map<String, Subject> following;
    private List<String> newsFeed;
    private int positiveMessageCount;
    private String latestMessage;

    public User(String userName) {
        super(userName);
        followers = new HashMap<String, Observer>();
        following = new HashMap<String, Subject>();
        
        followers.put(this.getUserName(), this);

        newsFeed = new ArrayList<String>();
    }

    public Map<String, Observer> getFollowers() {
        return followers;
    }

    public Map<String, Subject> getFollowing() {
        return following;
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public int getPositiveMessageCount() {
        return positiveMessageCount;
    }

    public void Tweet(String message) {
        this.latestMessage = message;
        this.setMessageCount(this.getMessageCount()+1);
        if(isPositiveMessage(message)) {
            ++positiveMessageCount;
        }

        notifyObservers();
    }

    public String getLatestMessage() {
        return this.latestMessage;
    }

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

    @Override
    public void update(Subject subject) {
        newsFeed.add(0, (((User) subject).getUserName()
        +" : " + ((User) subject).getLatestMessage()));
    }

    @Override
    public boolean contains(String userName) {
        return this.getUserName().equals(userName);
    }

    @Override
    public int getUserCount() {
        return 1;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitUser(this);
    }

    @Override
    public void notifyObservers() {
        for(Observer obs : followers.values()) {
            obs.update(this);
        }
    }

    @Override
    public void attach(Observer observer) {
        addFollower(observer);
    }

    private void addFollower(Observer user) {
        this.getFollowers().put(((AbstractUser) user).getUserName(), user);
        ((User) user).addUsertoFollow(this);
    }

    private void addUsertoFollow(Subject toFollow){
        if (toFollow.getClass() == User.class) {
            getFollowing().put(((AbstractUser) toFollow).getUserName(), toFollow);
        }
    } 
    
}
