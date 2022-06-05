package ui;

import java.awt.Window;

public interface LibWindow {
	static final String MAIN_LABEL = "Library Management System";
	void init();
	boolean isInitialized();
	void isInitialized(boolean val);
	void setVisible(boolean b);

}

