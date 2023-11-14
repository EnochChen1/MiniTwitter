import java.util.HashMap;
import java.util.Map;
/*
 * Class for groups in mini twitter app
 * Can contain users and other groups
 * checked
 */
public class Group extends AbstractUser {

    private Map<String,AbstractUser> groupUsers;

    /**
     * Group icon is only distinguished from User after a AbstractUser
     * is added to the Group.
     */
    public Group(String id) {
        super(id);
        groupUsers = new HashMap<String,AbstractUser>();
    }

    public Map<String,AbstractUser> getGroupUsers() {
        return groupUsers;
    }

    /**
     * Adds {@link AbstractUser} to list of {@link AbstractUser}s in this {@link Group}
     * if not already present.
     */
    public AbstractUser addUserInGroup(AbstractUser user) {
        if (!this.contains(user.getID())) {
            this.groupUsers.put(user.getID(), user);
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
    public boolean contains(String id) {
        boolean contains = false;
        for (AbstractUser user : groupUsers.values()) {
            if (user.contains(id)) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Returns number of {@link User}s in the {@link Group}.
     */
    @Override
    public int getSingleUserCount() {
        int count = 0;
        for (AbstractUser user : this.groupUsers.values()) {
            count += user.getSingleUserCount();
        }
        return count;
    }

    /**
     * Returns number of {@link Group}s that are descendants of
     * this {@link Group}.  Group count does not include {@code this}
     * {@link Group}.
     */
    @Override
    public int getGroupUserCount() {
        int count = 0;
        for (AbstractUser user : this.groupUsers.values()) {
            if (user.getClass() == Group.class) {
                ++count;
                count += user.getGroupUserCount();
            }
        }
        return count;
    }

    /**
     * Returns total number of messages sent by members of this {@link Group}.
     */
    @Override
    public int getMessageCount() {
        int msgCount = 0;
        for (AbstractUser user : this.groupUsers.values()) {
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
        for (AbstractUser user : groupUsers.values()) {
            ((Observer) user).update(subject);
        }
    }

    /*
     * Visitor methods
     */

    @Override
    public void accept(Visitor visitor) {
        for (AbstractUser user : groupUsers.values()) {
            user.accept(visitor);
        }
        visitor.visitGroupUser(this);
    }

    /*
     * Private methods
     */

    /**
     * Returns true if this Group contains one or more
     * Group as a descendant.
     */
    private boolean containsGroupUser() {
        boolean containsGroup = false;
        for (AbstractUser user : this.groupUsers.values()) {
            if (user.getClass() == Group.class) {
                containsGroup = true;
            }
        }
        return containsGroup;
    }

}
