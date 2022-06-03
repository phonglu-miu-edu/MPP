
import javax.swing.*;

import UI.LoginWindow;

public class StartGUI {
    private final String APP_NAME = "Library Management System";
    public StartGUI() {
        LoginWindow window = new LoginWindow();
        window.setTitle(this.APP_NAME);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new StartGUI();
    }
    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello Swing");
        frame.getContentPane().add(label);

        //frame.pack();
        frame.setVisible(true);
    }

}
