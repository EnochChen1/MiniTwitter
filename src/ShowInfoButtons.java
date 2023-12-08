import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;


public class ShowInfoButtons extends ControlPanel {

    private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messagesTotalButton;
    private JButton positivePercentageButton;
    private JButton userValidationButton;
    private JButton latestUserButton;


    private JPanel treePanel;
    private static JFrame frame;

    /**
     * Create the panel.
     */
    public ShowInfoButtons(JPanel treePanel) {
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
        addComponent(this, userValidationButton, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, latestUserButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

    
    }

    private void initializeComponents() {
        userTotalButton = new JButton("Show User Total");
        userTotalButton.setBackground(Color.cyan);
        initializeUserTotalButtonActionListener();

        groupTotalButton = new JButton("Show Group Total");
        groupTotalButton.setBackground(Color.cyan);
        initializeGroupTotalButtonActionListener();

        messagesTotalButton = new JButton("Show Messages Total");
        messagesTotalButton.setBackground(Color.cyan);
        initializeMessagesTotalButtonActionListener();

        positivePercentageButton = new JButton("Show Positive Percentage");
        positivePercentageButton.setBackground(Color.cyan);
        initializePositivePercentageButtonActionListener();

        userValidationButton = new JButton("User Validation");
        userValidationButton.setBackground(Color.cyan);
        initializeUserValidationButtonActionListener();

        latestUserButton = new JButton("Latest User");
        latestUserButton.setBackground(Color.cyan);
    }

    /**
     * Returns the selected AbstractUser in the TreePanel.
     */
    /*private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }
    */

    private DefaultMutableTreeNode getRootTreeNode() {
        DefaultMutableTreeNode rootTreeNode = ((TreePanel) treePanel).getRoot();
        return rootTreeNode;
    }


    /**
     * Displays total number of SingleUsers contained within Root.
     */
    private void initializeUserTotalButtonActionListener() {
        userTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode rootTreeNode = getRootTreeNode();

                VisitorTotalUsers visitor = new VisitorTotalUsers();
                ((AbstractUser) rootTreeNode).accept(visitor);
                String message = "Total number of users: "
                        + Integer.toString(visitor.visitUser(((AbstractUser) rootTreeNode)));

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) rootTreeNode).getUserName() + " information", JOptionPane.INFORMATION_MESSAGE );
            }

        });
    }

    /**
     * Displays total number of GroupUsers contained within Root.
     */
    private void initializeGroupTotalButtonActionListener() {
        groupTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode rootTreeNode = getRootTreeNode();

                VisitorGroupTotals visitor = new VisitorGroupTotals();
                ((AbstractUser) rootTreeNode).accept(visitor);
                String message = "Total number of groups within "
                        + ((AbstractUser) rootTreeNode).getUserName() + ": "
                        + Integer.toString(visitor.visitUser(((AbstractUser) rootTreeNode)));

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) rootTreeNode).getUserName() + " information", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

    /**
     * Displays the total number of messages sent in app.
     */
    private void initializeMessagesTotalButtonActionListener() {
        messagesTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode rootTreeNode = getRootTreeNode();

                VisitorTotalMessages visitor = new VisitorTotalMessages();
                ((AbstractUser) rootTreeNode).accept(visitor);
                String message = "Total number of messages sent: "
                        + Integer.toString(visitor.visitUser(((AbstractUser) rootTreeNode)));

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) rootTreeNode).getUserName() + " information", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

    /**
     * Displays percentage of positive messages sent in app.
     */
    private void initializePositivePercentageButtonActionListener() {
        positivePercentageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get AbstractUser selected in TreePanel
                DefaultMutableTreeNode rootTreeNode = getRootTreeNode();

                VisitorPositivePercentage positiveCountVisitor = new VisitorPositivePercentage();
                ((AbstractUser) rootTreeNode).accept(positiveCountVisitor);
                int positiveCount = positiveCountVisitor.visitUser(((AbstractUser) rootTreeNode));

                VisitorTotalMessages messageCountVisitor = new VisitorTotalMessages();
                ((AbstractUser) rootTreeNode).accept(messageCountVisitor);
                int messageCount = messageCountVisitor.visitUser(((AbstractUser) rootTreeNode));

                // calculate percentage, set percentage to 0.00 if no messages have yet been sent
                double percentage = 0;
                if (messageCount > 0) {
                    percentage = ((double) positiveCount / messageCount) * 100;
                }
                String percentageString = String.format("%.2f", percentage);

                String message = "Percentage of positive messages sent : "
                        + percentageString + "%";

                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) rootTreeNode).getUserName() + " information", JOptionPane.INFORMATION_MESSAGE );
            }
        });
    }

    private void initializeUserValidationButtonActionListener() {
        userValidationButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode rootTreeNode = getRootTreeNode();
                String message = 
                "Usernames must be unique and cannot contain spaces by default. Therefore, they are all valid.";
                JOptionPane.showMessageDialog(frame, message,
                ((AbstractUser) rootTreeNode).getUserName() + " information", JOptionPane.INFORMATION_MESSAGE );

            }
        });
    }

}
