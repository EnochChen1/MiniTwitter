public class VisitorGroupTotals implements Visitor {

    /*
     * This is the overarching method, that goes through
     * all of the groups and individuals
     */
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
        return 0;
    }

    /*
     * This function finds out how many groups there are by going througn the
     * map of groups
     * Once done, it will return to main function, to continue
     * to other branches
     */
    @Override
    public int visitGroupUser(AbstractUser user) {
        int count = 0;

        for (AbstractUser u : ((Group) user).getGroups().values()) {
            if (u.getClass() == Group.class) {
                ++count;
            }
            count += visitUser(u);
        }

        return count;
    }

}
