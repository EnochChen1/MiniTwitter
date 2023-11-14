import java.util.HashMap;
import java.util.Map;
/*
 * Class for groups in mini twitter app
 * Can contain users and other groups
 * checked
 */
public class Group extends AbstractUser {

    private Map<String,AbstractUser> groups;

    /**
     * Group icon is only distinguished from User after a AbstractUser
     * is added to the Group.
     */
    public Group(String userName) {
        super(userName);
        groups = new HashMap<String,AbstractUser>();
    }

    public Map<String,AbstractUser> getGroups() {
        return groups;
    }

    /**
     * Adds Username to group, if it is not in the group
     */
    public AbstractUser addUserInGroup(AbstractUser user) {
        if (!this.contains(user.getUserName())) {
            this.groups.put(user.getUserName(), user);
        }
        return this;
    }

    /*
     * Composite methods
     */

    /**
     * Checks if this {@link Group} contains specified AbstractUser ID.
     */
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

    /**
     * Returns number of users in the group
     */
    @Override
    public int getUserCount() {
        int count = 0;
        for (AbstractUser user : this.groups.values()) {
            count += user.getUserCount();
        }
        return count;
    }

    /**
     * returns number of groups in this group
     */
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

    /**
     * Returns total number of messages sent by users of this group.
     */
    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (AbstractUser user : this.groups.values()) {
            msgCount += user.getMessageCount();
        }
        return msgCount;
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
        for (AbstractUser user : groups.values()) {
            ((Observer) user).update(subject);
        }
    }

    /*
     * Visitor methods
     */

    @Override
    public void accept(Visitor visitor) {
        for (AbstractUser user : groups.values()) {
            user.accept(visitor);
        }
        visitor.visitGroupUser(this);
    }

}
