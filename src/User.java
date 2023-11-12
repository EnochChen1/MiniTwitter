import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private List<String> followers;
    private List<String> followings;
    private List<Tweet> tweets;

    public User(String userName) {
        this.userName = userName;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.tweets = new ArrayList<>();
    }


    public void followUser(String following) {
        if (!followings.contains(following)) {
            followings.add(following);
        }
    }

    

}
