package ui;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.nio.file.FileSystems;


public class LibrarySystem extends JFrame implements LibWindow {
	@Serial
	private static final long serialVersionUID = 1L;
	ControllerInterface ci = new SystemController();
	private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu menus;
    JMenuItem logout, viewCheckout, viewBook, viewMember, viewOverdue;
    String pathToImage;
    private boolean isInitialized = false;
    
    private static final LibWindow[] allWindows = {
    	LibrarySystem.INSTANCE,
		//LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,
		MemberWindow.INSTANCE,
		AllBookIdsWindow.INSTANCE,
		AddNewBookWindow.INSTANCE,
		CheckoutListWindow.INSTANCE,
		CheckoutWindow.INSTANCE,
		OverdueWindow.INSTANCE,
		AddMemberWindow.INSTANCE
	};
    	
	public static void hideAllWindows() {
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);
		}
	}
	
	public static void initAllWindows() {
		for(LibWindow frame: allWindows) {
			frame.init();
		}
	}

    private LibrarySystem() {}
    
    public void init() {
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
		
		createMenus();
		setSize(GuiControl.SCREEN_WIDTH, GuiControl.SCREEN_HEIGHT);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory + SEPARATOR + "src" + SEPARATOR + "ui" + SEPARATOR + "resources" + SEPARATOR + "library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);
    }
    
    private void addMenuItems() {
		menus = new JMenu("MENU");
		menuBar.add(menus);

		Auth loginedRole = SystemController.currentAuth;
		if(loginedRole == null) {
			hideAllWindows();
			Util.showLoginForm();
		} else {
			logout = new JMenuItem("LOGOUT");
			//login.addActionListener(new LoginListener());
			logout.addActionListener(evt -> {
				ci.logout();
			});

			if(loginedRole.equals(Auth.BOTH) || loginedRole.equals(Auth.LIBRARIAN)) {

				viewCheckout = new JMenuItem("CHECKOUT");
				viewCheckout.addActionListener(new CheckoutWindowListener());
				menus.add(viewCheckout);
			}

			if(loginedRole.equals(Auth.BOTH) || loginedRole.equals(Auth.ADMIN)) {

				viewBook = new JMenuItem("BOOK");
				viewBook.addActionListener(new AllBookIdsListener());

				viewMember = new JMenuItem("MEMBER");
				viewMember.addActionListener(new MemberWindowListener());

				menus.add(viewBook);
				menus.add(viewMember);
			}

			viewOverdue = new JMenuItem("OVERDUE");
			viewOverdue.addActionListener(new OverdueListener());

			menus.add(viewOverdue);
			menus.add(logout);
		}
    }

    class AllBookIdsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
		}
    }

	class MemberWindowListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			MemberWindow.INSTANCE.pack();
			MemberWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(MemberWindow.INSTANCE);
			MemberWindow.INSTANCE.setVisible(true);
		}
	}

	class CheckoutWindowListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CheckoutWindow.INSTANCE.setVisible(true);
		}
	}

	class OverdueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			OverdueWindow.INSTANCE.setVisible(true);
		}
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
}
