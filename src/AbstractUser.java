import javax.swing.tree.DefaultMutableTreeNode;

/*
 * This is the abstract class that implements Composite Design, checked
 */

public abstract class AbstractUser extends DefaultMutableTreeNode implements Observer {

    private String id;
    private int messageCount;

    public abstract boolean contains(String id);
    public abstract int getSingleUserCount();
    public abstract int getGroupUserCount();

    public AbstractUser(String id) {
        super(id);
        this.id = id;
        this.setMessageCount(0);
    }

    /**
     * Returns the user ID of this AbstractUser.
     */
    public String getID() {
        return id;
    }

    /**
     * Returns the total number of messages sent by this AbstractUser.
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * Sets the total number of messages sent by this AbstractUser
     * to the specified integer.
     */
    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    /*
     * Visitor methods
     */

    public abstract void accept(Visitor visitor);

}
