public class VisitorTotalUsers implements Visitor {

    @Override
    public int visitAbstractUser(AbstractUser user) {
        int count = 0;

        if(user.getClass() == User.class) {
            count += visitUser(user);
        }
        else if(user.getClass() == Group.class) {
            count += visitGroup(user);
        }
        return count;
    }

    @Override
    public int visitUser(AbstractUser user) {
        return 1;
    }

    @Override
    public int visitGroup(AbstractUser user) {
        int count = 0;
        for (AbstractUser u: ((Group) user).getGroups().values()) {
            count += visitAbstractUser(u);
        }
        return count;
    }
    
}