package ui;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.*;


public class MainWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 3258408422029144634L;
    private boolean isInitialized = false;
    private final String MAIN_LABEL = "Library Management System";
    private String[] links;
    private JList<String> linkList;
    private JPanel cards;

    public MainWindow(){
        init();
    }

    public void init() {
        setSize(GuiControl.SCREEN_WIDTH, GuiControl.SCREEN_HEIGHT);
        Util.centerFrameOnDesktop(this);
        setTitle(this.MAIN_LABEL);
    }

    private void defineLeftPanel() {}

    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }

}
