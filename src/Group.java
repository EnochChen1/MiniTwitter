import java.util.HashMap;
import java.util.Map;
/*
 * Class for groups in mini twitter app
 * Can contain users and other groups
 * checked
 */
public class Group extends AbstractUser {

    private Map<String,AbstractUser> groups;

    public Group(String userName) {
        super(userName);
        groups = new HashMap<String,AbstractUser>();
    }

    public Map<String,AbstractUser> getGroups() {
        return groups;
    }

    public AbstractUser addUserInGroup(AbstractUser user) {
        if (!this.contains(user.getUserName())) {
            this.groups.put(user.getUserName(), user);
        }
        return this;
    }

    @Override
    public boolean contains(String userName) {
        boolean contains = false;
        for (AbstractUser user : groups.values()) {
            if (user.contains(userName)) {
                contains = true;
            }
        }
        return contains;
    }

    @Override
    public int getUserCount() {
        int count = 0;
        for (AbstractUser user : this.groups.values()) {
            count += user.getUserCount();
        }
        return count;
    }

    @Override
    public int getGroupCount() {
        int count = 0;
        for (AbstractUser user : this.groups.values()) {
            if (user.getClass() == Group.class) {
                ++count;
                count += user.getGroupCount();
            }
        }
        return count;
    }


    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (AbstractUser user : this.groups.values()) {
            msgCount += user.getMessageCount();
        }
        return msgCount;
    }


    @Override
    public void update(Subject subject) {
        for (AbstractUser user : groups.values()) {
            ((Observer) user).update(subject);
        }
    }



    @Override
    public void accept(Visitor visitor) {
        for (AbstractUser user : groups.values()) {
            user.accept(visitor);
        }
        visitor.visitGroup(this);
    }

    private boolean containsGroupUser() {
        boolean containsGroup = false;
        for (AbstractUser user : this.groups.values()) {
            if (user.getClass() == Group.class) {
                containsGroup = true;
            }
        }
        return containsGroup;
    }

}