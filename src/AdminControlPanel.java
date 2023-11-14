import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;


/*
 * This class implements Singleton Pattern, making sure there is only one Admin Panel at a time.
 * This also adds all of the components from the other classes
 * To create the actual admin panel view
 */

public class AdminControlPanel extends ControlPanel {

    private static AdminControlPanel INSTANCE;

    private static JFrame frame;
    private JPanel treePanel;
    private JPanel addUserPanel;
    private JPanel openUserViewPanel;
    private JPanel showInfoPanel;

    private DefaultMutableTreeNode root;
    private Map<String, Observer> allUsers;

    /*
     * Implementation of the Singleton Pattern
     * Makes sure that only one Admin Control Panel can exist
     * at the same time
     */
    public static AdminControlPanel getInstance() {
        if (INSTANCE == null) {
            synchronized (Driver.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AdminControlPanel();
                }
            }
        }
        return INSTANCE;
    }

    private AdminControlPanel() {
        super();

        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(frame, treePanel, 0, 0, 1, 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, addUserPanel, 1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, openUserViewPanel, 1, 2, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, showInfoPanel, 1, 4, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        frame = new JFrame("Mini-Twitter");
        formatFrame();

        allUsers = new HashMap<String, Observer>();
        root = new Group("Root");
        allUsers.put(((AbstractUser) root).getUserName(), (Observer) this.root);

        treePanel = new TreePanel(root);
        addUserPanel = new AddUserButtons(treePanel, allUsers);
        openUserViewPanel = new OpenUserViewButton(treePanel, allUsers);
        showInfoPanel = new ShowInfoButtons(treePanel);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

}
