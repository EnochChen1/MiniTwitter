public interface Visitor {

    public int visitAbstractUser(AbstractUser user);
    public int visitUser(AbstractUser user);
    public int visitGroup(AbstractUser user);

}