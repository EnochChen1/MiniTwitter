import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class InfoPanel extends ControlPanel {

    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messagesTotalButton;
    private JButton positivePercentageButton;

    private JPanel treePanel;
    private static JFrame frame;

    /**
     * Create the panel.
     */
    public InfoPanel(JPanel treePanel) {
        super();

        this.treePanel = treePanel;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userTotalButton, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupTotalButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, messagesTotalButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, positivePercentageButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userTotalButton = new JButton("Show User Total");
        initializeUserTotalButtonActionListener();

        groupTotalButton = new JButton("Show Group Total");
        initializeGroupTotalButtonActionListener();

        messagesTotalButton = new JButton("Show Messages Total");
        initializeMessagesTotalButtonActionListener();

        positivePercentageButton = new JButton("Show Positive Percentage");
        initializePositivePercentageButtonActionListener();
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
     * Initializes action listener for UserTotalButton.  Opens message
     * dialog box for the specified User.
     *
     * Displays total number of SingleUsers contained within
     * the specified User.
     */
    private void initializeUserTotalButtonActionListener() {
        userTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                VisitorTotalUsers visitor = new VisitorTotalUsers();
                ((AbstractUser) selectedNode).accept(visitor);
                String message = "Total number of individual users within "
                        + ((AbstractUser) selectedNode).getUserName() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) selectedNode)));

                        JOptionPane.showMessageDialog(frame, message, ((AbstractUser) selectedNode).getUserName()+" Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Initializes action listener for GroupTotalButton.  Opens message
     * dialog box for the specified User.
     *
     * Displays total number of GroupUsers contained within the
     * specified User.  If a GroupUser is selected, the total excludes
     * the selected User.
     */
    private void initializeGroupTotalButtonActionListener() {
        groupTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                VisitorTotalGroups visitor = new VisitorTotalGroups();
                ((AbstractUser) selectedNode).accept(visitor);
                String message = "Total number of groups within "
                        + ((AbstractUser) selectedNode).getUserName() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) selectedNode)));

                JOptionPane.showMessageDialog(frame, message, ((AbstractUser) selectedNode).getUserName()+" Information", JOptionPane.INFORMATION_MESSAGE);


            }
        });
    }

    /**
     * Initializes action listener for MessagesTotalButton.  Opens
     * message dialog box for the specified User.
     *
     * Displays the total number of messages sent by the specified
     * User.  If a GroupUser is selected, the total represents the total
     * number of messages sent by all SingleUsers that are descendants
     * of the specified User.
     */
    private void initializeMessagesTotalButtonActionListener() {
        messagesTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                VisitorTotalMessages visitor = new VisitorTotalMessages();
                ((AbstractUser) selectedNode).accept(visitor);
                String message = "Total number of messages sent by "
                        + ((AbstractUser) selectedNode).getUserName() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) selectedNode)));

                JOptionPane.showMessageDialog(frame, message, ((AbstractUser) selectedNode).getUserName()+" Information", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    /**
     * Initializes action listener for PositivePercentageButton.  Opens
     * message dialog box for the specified User.
     *
     * Displays the percentage of positive messages sent by the specified
     * User.  If a GroupUser is selected, the total represents the total
     * number of positive messages sent by all SingleUsers that are descendants
     * of the specified User.
     */
    private void initializePositivePercentageButtonActionListener() {
        positivePercentageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                VisitorTotalPositive positiveCountVisitor = new VisitorTotalPositive();
                ((AbstractUser) selectedNode).accept(positiveCountVisitor);
                int positiveCount = positiveCountVisitor.visitUser(((AbstractUser) selectedNode));

                VisitorTotalMessages messageCountVisitor = new VisitorTotalMessages();
                ((AbstractUser) selectedNode).accept(messageCountVisitor);
                int messageCount = messageCountVisitor.visitUser(((AbstractUser) selectedNode));

                // calculate percentage, set percentage to 0.00 if no messages have yet been sent
                double percentage = 0;
                if (messageCount > 0) {
                    percentage = ((double) positiveCount / messageCount) * 100;
                }
                String percentageString = String.format("%.2f", percentage);

                String message = "Percentage of positive messages sent by "
                        + ((AbstractUser) selectedNode).getUserName() + ": "
                        + percentageString + "%";

                JOptionPane.showMessageDialog(frame, message, ((AbstractUser) selectedNode).getUserName()+" Information", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

}