public interface Visitor {

    public int visitUser(AbstractUser user);
    public int visitSingleUser(AbstractUser user);
    public int visitGroupUser(AbstractUser user);

}
