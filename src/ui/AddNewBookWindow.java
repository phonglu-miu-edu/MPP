package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.TestData;
import entities.Author;
import entities.Book;

public class AddNewBookWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AddNewBookWindow INSTANCE = new AddNewBookWindow();
	public static int bookID = 0;
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	
	private JTextField id;
	private JTextField isbn;
	private JTextField title;
	private JRadioButton check1;
	private JRadioButton check2;
	private JList<String> authorList;
	
	private AddNewBookWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel(AddNewBookWindow.bookID);
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);	
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Add a new book");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel(int maxID) {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel gridPanel = new JPanel();
		middlePanel.add(gridPanel);
		GridLayout gl = new GridLayout(6, 2);
		gl.setHgap(8);
		gl.setVgap(8);
		gridPanel.setLayout(gl);
		gridPanel.setBorder(new WindowBorder(GuiControl.WINDOW_BORDER));

		//add fields
		makeLabel(gridPanel, "Book ID");
		id = new JTextField(10);
		id.setText(String.valueOf(maxID));
		id.setEnabled(false);
		gridPanel.add(id);

		makeLabel(gridPanel, "ISBN");
		isbn = new JTextField(10);
		gridPanel.add(isbn);

		makeLabel(gridPanel, "Title");
		title = new JTextField(10);
		gridPanel.add(title);

		makeLabel(gridPanel, "Checkout days");	    
		check1 = new JRadioButton("7");
		check2 = new JRadioButton("21");
		ButtonGroup bg=new ButtonGroup();    
		bg.add(check1);
		bg.add(check2);
		JPanel rButtons = new JPanel(); //uses FlowLayout by default
	    rButtons.add(check1);
	    rButtons.add(check2);
	    gridPanel.add(rButtons);
		
		makeLabel(gridPanel, "Author");
		TestData td = new TestData();
		List<Author> authors = td.allAuthors;
		DefaultListModel<String> l1 = new DefaultListModel<>();  
		l1.addElement(authors.get(1).getFirstName() + " " + authors.get(1).getLastName());
		l1.addElement(authors.get(2).getFirstName() + " " + authors.get(2).getLastName());
		l1.addElement(authors.get(3).getFirstName() + " " + authors.get(3).getLastName());
//		for(Author auth: authors) {
//			l1.addElement(auth.getFirstName() + " " + auth.getLastName());
//		}
		authorList = new JList<>(l1);
        gridPanel.add(authorList);
	}
	
	private void cancelButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);	
		});
	}
	
	private void addNewBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			TestData td = new TestData();
			List<Author> list = new ArrayList<>();
			for(Author au1: td.allAuthors) {
				for(String name: authorList.getSelectedValuesList()) {
					if (name.equals(au1.getFirstName() + " " + au1.getLastName())) {
						list.add(au1);
					}
				}
			}
			System.out.println(list.toString());
			Book book = new Book(id.getText(), isbn.getText(), title.getText(), (check1.isSelected()?7:21), list);
			ci.addNewBook(book);
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
		});
	}

	private void makeLabel(JPanel p, String s) {
		JLabel l = new JLabel(s);
		p.add(leftPaddedPanel(l));
	}
	private JPanel leftPaddedPanel(JLabel label) {
		JPanel paddedPanel = new JPanel();
		paddedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		paddedPanel.add(GuiControl.createHBrick(1));
		paddedPanel.add(label);
		paddedPanel.setBackground(GuiControl.SCREEN_BACKGROUND);
		return paddedPanel;
	}
	
	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
		JButton cancelBtn = new JButton("Cancel");
		cancelButtonListener(cancelBtn);
		JButton addBtn = new JButton("Add");
		addNewBookButtonListener(addBtn);
		lowerPanel.add(cancelBtn);
		lowerPanel.add(addBtn);
	}
	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   LibrarySystem.INSTANCE.setVisible(true);
	    });
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
