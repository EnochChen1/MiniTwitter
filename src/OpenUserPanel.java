import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class OpenUserPanel extends ControlPanel {

    private JButton openUserViewButton;
    private JPanel spacerPanel;

    private JPanel treePanel;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    private static JFrame frame;

    /**
     * Create the panel.  Assume can only open a UserViewPanel for SingleUser.
     */
    public OpenUserPanel(JPanel treePanel, Map<String, Observer> allUsers) {
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
        addComponent(this, openUserViewButton, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, spacerPanel, 1, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        openPanels = new HashMap<String, JPanel>();

        openUserViewButton = new JButton("Open User View");
        initializeOpenUserViewActionListener();

        // Empty spacer
        spacerPanel = new JPanel();
    }

    /**
     * Returns the selected User in the TreePanel.
     */
    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    /*
     * Action Listeners
     */

    /**
     * Initializes action listener for OpenUserViewButton.  Opens UserViewPanel
     * of the selected SingleUser, and only one UserViewPanel can be open for a
     * SingleUser at any given time.  UserViewPanel cannot be opened for GroupUser.
     */
    private void initializeOpenUserViewActionListener() {
        openUserViewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                // open user view UI on click, only open one window per User
                if (!allUsers.containsKey(((AbstractUser) selectedNode).getUserName())) {
                    JOptionPane.showMessageDialog(frame, "No such user exists", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == Group.class) {
                    JOptionPane.showMessageDialog(frame, "User view does not exist for a group", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (openPanels.containsKey(((AbstractUser) selectedNode).getUserName())) {
                    JOptionPane.showMessageDialog(frame, "User view is already open for"+((AbstractUser) selectedNode).getUserName(), "Error", JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == User.class) {
                    UserViewPanel userView = new UserViewPanel(allUsers, openPanels, selectedNode);
                    openPanels.put(((AbstractUser) selectedNode).getUserName(), userView);
                }
            }
        });
    }

}