import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/*
 * This is the abstract class that implements Composite Design
 */

public abstract class AbstractUser extends DefaultMutableTreeNode implements Observer{
    private String userName;
    private int messageCount;

    public abstract boolean contains(String userName);
    public abstract int getUserCount();
    public abstract int getGroupCount();


    public AbstractUser(String userName) {
        super(userName);
        this.userName = userName;
        this.setMessageCount(0);
    }

    public String getUserName() {
        return userName;
    }

    //needed to get total number of Tweet Messages in all user's news feed
    public int getMessageCount() {
        return this.messageCount;
    }

    //This is needed to set the message count for a new user to 0
    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public abstract void accept(Visitor visitor);

}
