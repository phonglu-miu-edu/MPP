package UI;

import java.awt.Window;

public interface IParentWindow {
    public void setVisible(boolean b);
    public Window getParentWindow();
    public void setParentWindow(Window w);
}
