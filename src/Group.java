import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    private List<User> members;
    private List<Group> subGroups;

    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.subGroups = new ArrayList<>();
    }

}
