import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
/*
 * UI for creating users and groups with buttons
 * 
 */

public class AddUserButtons extends ControlPanel {

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
    public AddUserButtons(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;

        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userName, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupName, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userName = new JTextField("");
        groupName = new JTextField("");

        addUserButton = new JButton("Add User");
        addUserButton.setBackground(Color.cyan);
        initializeAddUserButtonActionListener();

        addGroupButton = new JButton("Add Group");
        addGroupButton.setBackground(Color.cyan);
        initializeAddGroupButtonActionListener();
    }

    /**
     * ActionListener for AddUserButton, will not create User if name is already in allUsers
     *
     * If User is selected when adding a new User, 
     * new User is added in the same group as the selected User
     * If Group is selected when adding new User,
     * new User is added under that group
     */
    private void initializeAddUserButtonActionListener() {
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // check if user ID already exists
                if (allUsers.containsKey(userName.getText())) {
                    JOptionPane.showMessageDialog(frame, 
                     "Username already exists, Please choose a different username.", "Error"
                , JOptionPane.ERROR_MESSAGE );
                } else {
                    Observer child = new User(userName.getText());

                    allUsers.put(((AbstractUser) child).getUserName(), child);
                    ((TreePanel) treePanel).addUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    /**
     * ActionListener for AddGroupButton
     *
     * If User is selected, when adding a new Group, 
     * New Group will be under the same group as selected User
     * 
     * If Group is selected, New Group will be under that group.
     */
    private void initializeAddGroupButtonActionListener() {
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // check if user ID already exists
                if (allUsers.containsKey(groupName.getText())) {
                    JOptionPane.showMessageDialog(frame, 
                     "User already exists, Please choose a different username.", "Error"
                , JOptionPane.ERROR_MESSAGE );
                } else {
                    Observer child = new Group(groupName.getText());

                    allUsers.put(((AbstractUser) child).getUserName(), child);
                    ((TreePanel) treePanel).addGroup((DefaultMutableTreeNode) child);
                }
            }
        });
    }

}



