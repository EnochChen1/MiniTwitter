import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

/*
 * This is the abstract class that implements Composite Design
 * Core model needs a tree, which requires Composite Design Pattern
 * 
 * This also implements the Observer Design Pattern
 */

public abstract class AbstractUser extends DefaultMutableTreeNode implements Observer {

    private String userName;
    private int messageCount;
    private long creationTime;

    public abstract boolean contains(String userName);
    public abstract int getUserCount();
    public abstract int getGroupCount();

    public AbstractUser(String userName) {
        super(userName);
        this.userName = userName;
        this.setMessageCount(0);
        creationTime = System.currentTimeMillis();        
    }

    /**
     * Returns username of this AbstractUser.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns total number of messages sent by this AbstractUser.
     */
    public int getMessageCount() {
        return messageCount;
    }

    /*
     * Returns time user was created
     */

    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Sets total number of messages sent by this AbstractUser
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
