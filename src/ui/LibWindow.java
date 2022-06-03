package ui;

import java.awt.Window;

public interface LibWindow {
	void init();
	boolean isInitialized();
	void isInitialized(boolean val);
	void setVisible(boolean b);

	//public Window getParentWindow();
	//public void setParentWindow(Window w);
}

