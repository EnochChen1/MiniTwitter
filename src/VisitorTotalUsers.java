public class VisitorTotalUsers implements Visitor {

    @Override
    public int visitUser(AbstractUser user) {
        int count = 0;

        if (user.getClass() == User.class) {
            count += visitSingleUser(user);
        } else if (user.getClass() == Group.class) {
            count += visitGroupUser(user);
        }

        return count;
    }

    @Override
    public int visitSingleUser(AbstractUser user) {
        return 1;
    }

    @Override
    public int visitGroupUser(AbstractUser user) {
        int count = 0;

        for (AbstractUser u : ((Group) user).getGroups().values()) {
            count += visitUser(u);
        }

        return count;
    }

}
