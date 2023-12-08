
public class VisitorLatestUpdatedUser implements Visitor {
    long lastUpdateTime = 0;
    String userName = "";

    @Override
    public int visitUser(AbstractUser user) {

        if (user.getClass() == User.class) {
        } else if (user.getClass() == Group.class) {
        }
        return 0;
    }

    @Override
    public int visitSingleUser(AbstractUser user) {
        return 0;
    }

    @Override
    public int visitGroupUser(AbstractUser user) {
        return 0;
    }

    public String visitUpdatedUser(AbstractUser user) {
        if (user.getClass() == User.class) {
                visitUpdatedSingleUser(user);
            }
         else if (user.getClass() == Group.class) {
            visitUpdatedGroupUser(user);
        }
        return userName;
    }

    public void visitUpdatedSingleUser(AbstractUser user) {
        long substitute = ((User )user).getLastUpdateTime();
            if(substitute > lastUpdateTime) {
                substitute = lastUpdateTime;
                userName = ((User )user).getUserName();
        }    
    }
    
    public void visitUpdatedGroupUser(AbstractUser user) {
        for (AbstractUser u : ((Group) user).getGroups().values()) {
            visitUpdatedUser(u);
        }
    }
    
}
