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
 * AbstractUser Panel for creating users and groups
 * checked
 */

public class AddUserPanel extends ControlPanel {

    private JPanel treePanel;
    private Map<String, Observer> allUsers;

    private JButton addUserButton;
    private JButton addGroupButton;
    private JTextField userId;
    private JTextField groupId;

    private static JFrame frame;

    /**
     * Create the panel.
     */
    public AddUserPanel(JPanel treePanel, Map<String, Observer> allUsers) {
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
        addComponent(this, userId, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupId, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userId = new JTextField("User ID");
        groupId = new JTextField("Group ID");

        addUserButton = new JButton("Add User");
        initializeAddUserButtonActionListener();

        addGroupButton = new JButton("Add Group");
        initializeAddGroupButtonActionListener();
    }

    /*
     * Action Listeners
     */

    /**
     * Initializes action listener for AddUserButton.  Adds User with the specified
     * user ID to the TreePanel if the user ID does not already exist.
     *
     * If a User is selected in the TreePanel, the new User will be added as
     * a sibling of the selected AbstractUser.  If a Group is selected in the TreePanel, the
     * new User will be added as a child of the selected AbstractUser.
     */
    private void initializeAddUserButtonActionListener() {
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // check if user ID already exists
                if (allUsers.containsKey(userId.getText())) {
                    JOptionPane.showMessageDialog(frame, 
                     "User already exists, Please choose a different username.", "Error"
                , JOptionPane.ERROR_MESSAGE );
                } else {
                    Observer child = new User(userId.getText());

                    allUsers.put(((AbstractUser) child).getID(), child);
                    ((TreePanel) treePanel).addSingleUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    /**
     * Initializes action listener for AddGroupButton.  Adds Group with the
     * specified user ID to the TreePanel if the user ID does not already exist.
     *
     * If a User is selected in the TreePanel, the new Group will be added
     * as a sibling of the selected AbstractUser.  If a Group is selected in the TreePanel,
     * the new Group will be added as a child of  the selected AbstractUser.
     */
    private void initializeAddGroupButtonActionListener() {
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // check if user ID already exists
                if (allUsers.containsKey(groupId.getText())) {
                    JOptionPane.showMessageDialog(frame, 
                     "User already exists, Please choose a different username.", "Error"
                , JOptionPane.ERROR_MESSAGE );
                } else {
                    Observer child = new Group(groupId.getText());

                    allUsers.put(((AbstractUser) child).getID(), child);
                    ((TreePanel) treePanel).addGroupUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

}



