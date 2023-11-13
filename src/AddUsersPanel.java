import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
/*
 * User Panel for creating users and groups
 * checked
 */

public class AddUsersPanel extends ControlPanel {

    private JPanel treePanel;
    private Map<String, Observer> allUsers;

    private JButton addUserButton;
    private JButton addGroupButton;
    private JTextField userName;
    private JTextField groupName;

    private static JFrame frame;

    /**
     * Create the panel.
     */
    public AddUsersPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;

        initializeComponents();
        addComponents();
    }

    /*
     * Private methods
     */

    private void addComponents() {
        addComponent(this, userName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupName, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userName = new JTextField("Username");
        groupName = new JTextField("Group Name");

        addUserButton = new JButton("Add User");
        initializeAddUserButtonActionListener();

        addGroupButton = new JButton("Add Group");
        initializeAddGroupButtonActionListener();
    }

    /*
     * Action Listeners
     */

    /**
     * Initializes action listener for AddUserButton.  Adds SingleUser with the specified
     * user ID to the TreePanel if the user ID does not already exist.
     *
     * If a SingleUser is selected in the TreePanel, the new SingleUser will be added as
     * a sibling of the selected User.  If a GroupUser is selected in the TreePanel, the
     * new SingleUser will be added as a child of the selected User.
     */
    private void initializeAddUserButtonActionListener() {
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // check if user ID already exists
                if (allUsers.containsKey(userName.getText())) {
                    JOptionPane.showMessageDialog(frame, "User already exists choose a different name", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new User(userName.getText());

                    allUsers.put(((AbstractUser) child).getUserName(), child);
                    ((TreePanel) treePanel).addUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    /**
     * Initializes action listener for AddGroupButton.  Adds GroupUser with the
     * specified user ID to the TreePanel if the user ID does not already exist.
     *
     * If a SingleUser is selected in the TreePanel, the new GroupUser will be added
     * as a sibling of the selected User.  If a GroupUser is selected in the TreePanel,
     * the new GroupUser will be added as a child of  the selected User.
     */
    private void initializeAddGroupButtonActionListener() {
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // check if user ID already exists
                if (allUsers.containsKey(groupName.getText())) {
                    JOptionPane.showMessageDialog(frame, "User already exists choose a different name", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new Group(groupName.getText());

                    allUsers.put(((AbstractUser) child).getUserName(), child);
                    ((TreePanel) treePanel).addGroup((DefaultMutableTreeNode) child);
                }
            }   
        });
    }

}