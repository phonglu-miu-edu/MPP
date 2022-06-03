package ui;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.JFrame;

public class MemberWindow extends JFrame implements LibWindow {
    private boolean isInitialized = false;
    ControllerInterface ci = new SystemController();
    public void init(){
        //
    }

    public boolean isInitialized(){
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }

}
