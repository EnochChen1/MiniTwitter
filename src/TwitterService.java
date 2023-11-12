import java.util.ArrayList;
import java.util.List;

public class TwitterService {
    private List<User> users;
    private Group rootGroup;

    public TwitterService() {
        this.users = new ArrayList<>();
        this.rootGroup = new Group("Root");
    }

    public void followUser(String followerUserID, String targetUserID) {
        User follower = followerUserID;
        User targetUser = getUserByID(targetUserID);

        if (follower != null && targetUser != null) {
            follower.followUser(targetUserID);
        }
    }
}
