package ui;

import business.SystemController;

import javax.swing.*;
import java.awt.*;

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
