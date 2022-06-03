package ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import ui.LoginWindow;
import ui.Util;
import business.SystemController;

public class Main {
	public static void main(String[] args) {
	      EventQueue.invokeLater(() ->
	         {
	            LibrarySystem.INSTANCE.setTitle("Library Management System");
	            LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				if(SystemController.currentAuth == null) {
					Util.showLoginForm();
				} else {
					Util.showMainScreen(SystemController.currentAuth);
				}
	         });
	}

}
