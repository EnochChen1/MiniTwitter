import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import java.util.HashMap;
import java.util.Map;

public class OpenUserViewButton extends ControlPanel {

    private JButton openUserViewButton;
    private JPanel spacerPanel;

    private JPanel treePanel;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    private static JFrame frame;

    /**
     * Create the panel.  One User panel per User.
     */
    public OpenUserViewButton(JPanel treePanel, Map<String, Observer> allUsers) {
        super();

        this.treePanel = treePanel;
        this.allUsers = allUsers;
        initializeComponents();
        addComponents();
    }


    private void addComponents() {
        addComponent(this, openUserViewButton, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        openPanels = new HashMap<String, JPanel>();

        openUserViewButton = new JButton("Open User View");
        openUserViewButton.setBackground(Color.cyan);
        initializeOpenUserViewActionListener();
    }

    /**
     * Returns the selected AbstractUser in the TreePanel.
     */
    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    /**
     * Initializes action listener for OpenUserViewButton.  Opens UserViewPanel
     * of the selected User, and only one UserViewPanel can be open for a
     * User at any given time.  UserViewPanel cannot be opened for Group.
     */
    private void initializeOpenUserViewActionListener() {
        openUserViewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                // open user view UI on click, only open one window per AbstractUser
                if (!allUsers.containsKey(((AbstractUser) selectedNode).getUserName())) {
                    JOptionPane.showMessageDialog(frame, "No such user exists.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == Group.class) {
                            JOptionPane.showMessageDialog(frame, 
                            "User view does not exist for " + ((AbstractUser) selectedNode).getUserName() +", as it is a group.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                } else if (openPanels.containsKey(((AbstractUser) selectedNode).getUserName())) {
                            JOptionPane.showMessageDialog(frame, "User view is already open for "
                            +((AbstractUser) selectedNode).getUserName(),"Error", JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == User.class) {
                    UserViewPanel userView = new UserViewPanel(allUsers, openPanels, selectedNode);
                    openPanels.put(((AbstractUser) selectedNode).getUserName(), userView);
                }
            }
        });
    }

}
