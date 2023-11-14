import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/*
 *  Tree for the mini twitter app
 *  checked
 */
public class TreePanel extends JPanel {

    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private JScrollPane scrollPane;

    public TreePanel(DefaultMutableTreeNode root) {
        super(new GridLayout(1,0));

        rootNode = root;
        initializeComponents();
        addComponents();
    }

    /**
     * Returns the JTree on this JPanel.
     */
    public JTree getTree() {
        return this.tree;
    }

    /**
     * Returns the root node of the TreeModel.
     */
    public DefaultMutableTreeNode getRoot() {
        return this.rootNode;
    }

    /**
     * Add {@link Group} to the currently selected {@link AbstractUser}.
     * If the currently selected {@link AbstractUser} is a {@link User},
     * the {@link Group} is added as a sibling.
     */
    public void addGroupUser(DefaultMutableTreeNode child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        // set parent as selected AbstractUser, set as root if no AbstractUser is selected
        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        // add to parent Group if selected node is a User
        if (parentNode.getUserObject().getClass() == User.class) {
            parentNode = (DefaultMutableTreeNode) parentNode.getParent();
        }
        addUser(parentNode, child, true);
    }

    /**
     * Add {@link User} to the currently selected {@link AbstractUser}.
     * If the currently selected {@link AbstractUser} is a {@link User},
     * the {@link User} is added as a sibling.
     */
    public void addSingleUser(DefaultMutableTreeNode child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        // set parent as selected AbstractUser, set as root if no AbstractUser is selected
        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }

        // add to parent Group if selected node is a User
        if (parentNode.getUserObject().getClass() == User.class) {
            parentNode = (DefaultMutableTreeNode) parentNode.getParent();
        }
        addUser(parentNode, child, true);
    }

    /*
     * Private methods
     */

    /**
     * Add specified child {@link AbstractUser} to specified parent {@link AbstractUser}.
     */
    private void addUser(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }

        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }

        if (parent.getClass() != Group.class) {
            parent = (DefaultMutableTreeNode) parent.getUserObject();
        }
        ((Group) parent).addUserInGroup((AbstractUser) child);
    }

    private void addComponents() {
        add(scrollPane);
    }

    private void initializeComponents() {
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());

        tree = new JTree(treeModel);
        formatTree();

        scrollPane = new JScrollPane(tree);
    }

    private void formatTree() {
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setSelectionRow(0);
    }

    /**
     * TreeModelListener for this TreeModel.
     */
    private class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }

}