import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class ShowInfoPanel extends ControlPanel {

    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messagesTotalButton;
    private JButton positivePercentageButton;

    private JPanel treePanel;
    private static JFrame frame;

    /**
     * Create the panel.
     */
    public ShowInfoPanel(JPanel treePanel) {
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

    /*
     * Action Listeners
     */

    /**
     * Initializes action listener for UserTotalButton.  Opens message
     * dialog box for the specified AbstractUser.
     *
     * Displays total number of SingleUsers contained within
     * the specified AbstractUser.
     */
    private void initializeUserTotalButtonActionListener() {
        userTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                UserTotalVisitor visitor = new UserTotalVisitor();
                ((AbstractUser) selectedNode).accept(visitor);
                String message = "Total number of individual users within "
                        + ((AbstractUser) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) selectedNode)));

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) selectedNode).getID() + " information", JOptionPane.INFORMATION_MESSAGE );
            }

        });
    }

    /**
     * Initializes action listener for GroupTotalButton.  Opens message
     * dialog box for the specified AbstractUser.
     *
     * Displays total number of GroupUsers contained within the
     * specified AbstractUser.  If a Group is selected, the total excludes
     * the selected AbstractUser.
     */
    private void initializeGroupTotalButtonActionListener() {
        groupTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                GroupTotalVisitor visitor = new GroupTotalVisitor();
                ((AbstractUser) selectedNode).accept(visitor);
                String message = "Total number of groups within "
                        + ((AbstractUser) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) selectedNode)));

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) selectedNode).getID() + " information", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

    /**
     * Initializes action listener for MessagesTotalButton.  Opens
     * message dialog box for the specified AbstractUser.
     *
     * Displays the total number of messages sent by the specified
     * AbstractUser.  If a Group is selected, the total represents the total
     * number of messages sent by all SingleUsers that are descendants
     * of the specified AbstractUser.
     */
    private void initializeMessagesTotalButtonActionListener() {
        messagesTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                MessagesTotalVisitor visitor = new MessagesTotalVisitor();
                ((AbstractUser) selectedNode).accept(visitor);
                String message = "Total number of messages sent by "
                        + ((AbstractUser) selectedNode).getID() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) selectedNode)));

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) selectedNode).getID() + " information", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

    /**
     * Initializes action listener for PositivePercentageButton.  Opens
     * message dialog box for the specified AbstractUser.
     *
     * Displays the percentage of positive messages sent by the specified
     * AbstractUser.  If a Group is selected, the total represents the total
     * number of positive messages sent by all SingleUsers that are descendants
     * of the specified AbstractUser.
     */
    private void initializePositivePercentageButtonActionListener() {
        positivePercentageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                PositiveTotalVisitor positiveCountVisitor = new PositiveTotalVisitor();
                ((AbstractUser) selectedNode).accept(positiveCountVisitor);
                int positiveCount = positiveCountVisitor.visitUser(((AbstractUser) selectedNode));

                MessagesTotalVisitor messageCountVisitor = new MessagesTotalVisitor();
                ((AbstractUser) selectedNode).accept(messageCountVisitor);
                int messageCount = messageCountVisitor.visitUser(((AbstractUser) selectedNode));

                // calculate percentage, set percentage to 0.00 if no messages have yet been sent
                double percentage = 0;
                if (messageCount > 0) {
                    percentage = ((double) positiveCount / messageCount) * 100;
                }
                String percentageString = String.format("%.2f", percentage);

                String message = "Percentage of positive messages sent by "
                        + ((AbstractUser) selectedNode).getID() + ": "
                        + percentageString + "%";

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) selectedNode).getID() + " information", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

}
