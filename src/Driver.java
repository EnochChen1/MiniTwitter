import java.awt.EventQueue;

import javax.swing.JFrame;

public class Driver extends JFrame {

    /**
     * Launch the app, makes sure any errors will show up on terminal
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminControlPanel frame = AdminControlPanel.getInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}