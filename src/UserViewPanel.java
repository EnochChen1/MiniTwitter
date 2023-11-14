import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;

public class UserViewPanel extends ControlPanel {

    private static JFrame frame;
    private GridBagConstraints constraints;

    private JTextField toFollowTextField;

    private JTextArea tweetMessageTextArea;
    private JTextArea currentFollowingTextArea;
    private JTextArea newsFeedTextArea;

    private JScrollPane tweetMessageScrollPane;
    private JScrollPane currentFollowingScrollPane;
    private JScrollPane newsFeedScrollPane;

    private JButton followUserButton;
    private JButton postTweetButton;

    private Subject user;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    /**
     * Create the panel.
     */
    public UserViewPanel(Map<String, Observer> allUsers, Map<String, JPanel> allPanels, DefaultMutableTreeNode user) {
        super();

        this.user = (Subject) user;
        this.allUsers = allUsers;
        this.openPanels = allPanels;
        initializeComponents();
        addComponents();
    }


    private void addComponents() {
        addComponent(frame, toFollowTextField, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, followUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, currentFollowingTextArea, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, tweetMessageScrollPane, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, postTweetButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, newsFeedScrollPane, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        frame = new JFrame("User View");
        formatFrame();

        constraints = new GridBagConstraints();
        constraints.ipady = 100;

        toFollowTextField = new JTextField("");
        followUserButton = new JButton("Follow User");
        followUserButton.setBackground(Color.cyan);
        initializeFollowUserButtonActionListener();

        currentFollowingTextArea = new JTextArea("Current Following: ");
        formatTextArea(currentFollowingTextArea);
        currentFollowingScrollPane = new JScrollPane(currentFollowingTextArea);
        currentFollowingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetMessageTextArea = new JTextArea("");
        tweetMessageScrollPane = new JScrollPane(tweetMessageTextArea);
        tweetMessageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        postTweetButton = new JButton("Post Tweet");
        postTweetButton.setBackground(Color.cyan);
        initializePostTweetButtonActionListener();

        newsFeedTextArea = new JTextArea("News Feed: ");
        formatTextArea(newsFeedTextArea);
        newsFeedScrollPane = new JScrollPane(newsFeedTextArea);
        newsFeedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // current following and news feed lists reflect most recent state of UserViewPanel
        updateCurrentFollowingTextArea();

        // news feed is updated even while UserViewPanel is closed
        updateNewsFeedTextArea();
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(8);
        textArea.setEditable(false);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setTitle(((AbstractUser) user).getUserName());

        // allows UserViewPanel to be reopened after it has been closed
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                openPanels.remove(((AbstractUser) user).getUserName());
            }
        });
    }

    /**
     * Updates the news feed display.
     */
    private void updateNewsFeedTextArea() {
        String list = "News Feed: \n";

        for (String news : ((User) user).getNewsFeed()) {
            list += " - " + news + "\n";
        }

        // show most recent message at top of news feed
        newsFeedTextArea.setText(list);
        newsFeedTextArea.setCaretPosition(0);
    }

    /**
     * Updates the current following display.
     */
    private void updateCurrentFollowingTextArea() {
        String list = "Current Following: \n";
        for (String following : ((User) user).getFollowing().keySet()) {
            list += " - " + following + "\n";
        }
        currentFollowingTextArea.setText(list);
        currentFollowingTextArea.setCaretPosition(0);
    }

    /**
     * Initializes action listener for PostTweetButton.  Sends
     * specified message to the news feeds of the followers of
     * this User.
     */
    private void initializePostTweetButtonActionListener() {
        postTweetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((User) user).sendMessage(tweetMessageTextArea.getText());

                for (JPanel panel : openPanels.values()) {
                    ((UserViewPanel) panel).updateNewsFeedTextArea();
                }
            }
        });
    }

    /**
     * Initializes action listener for FollowUserButton.  Adds this
     * User as a follower of the specified User.  Cannot follow
     * a Group.
     */
    private void initializeFollowUserButtonActionListener() {
        followUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractUser toFollow = (AbstractUser) allUsers.get(toFollowTextField.getText());

                if (!allUsers.containsKey(toFollowTextField.getText())) {
                            JOptionPane.showMessageDialog(frame, "User does not exist.",
                "Error", JOptionPane.ERROR_MESSAGE );

                } else if (toFollow.getClass() == Group.class) {
                            JOptionPane.showMessageDialog(frame, "Cannot follow a group.",
                "Error", JOptionPane.ERROR_MESSAGE );
                            
                } else if (allUsers.containsKey(toFollowTextField.getText())) {
                    ((Subject) toFollow).attach((Observer) user);
                }

                // show current following as list
                updateCurrentFollowingTextArea();
            }
        });
    }

}

