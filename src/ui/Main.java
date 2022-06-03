package ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import ui.LoginWindow;
import ui.Util;

public class Main {
	public static void main(String[] args) {
	      EventQueue.invokeLater(() ->
	         {
	            LibrarySystem.INSTANCE.setTitle("Library Management System");
	            LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            LibrarySystem.INSTANCE.init();
	            Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
	            LibrarySystem.INSTANCE.setVisible(true);
	         });
	}

	public static void startGUI() {
		LoginWindow loginForm = new LoginWindow();
		loginForm.setVisible(true);
	}
}
