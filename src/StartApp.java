import javax.swing.JFrame;
import javax.swing.JLabel;

import business.SystemController;
import ui.LoginWindow;
import entities.Author;

public class StartApp {
    private final String APP_NAME = "Library Management System";
    public StartApp(){
        if(SystemController.currentAuth != null) {
            //
        } else {
            LoginWindow login = new LoginWindow();
            login.setTitle(this.APP_NAME);
            login.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new StartApp();
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
