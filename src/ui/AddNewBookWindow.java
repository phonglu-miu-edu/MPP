package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.TestData;
import entities.Author;

public class AddNewBookWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AddNewBookWindow INSTANCE = new AddNewBookWindow();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	
	private CustomTextField isbn;
	private CustomTextField title;
	private JRadioButton check1;
	private JRadioButton check2;
	private JList<Author> authorList;
	DefaultListModel<Author> modelAuthor;
	ButtonGroup buttonGroup;
	
	private AddNewBookWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineLowerPanel();
		defineMiddlePanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);	
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		setTitle(Util.MAIN_LABEL);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("Add a new book");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel gridPanel = new JPanel();
		middlePanel.add(gridPanel);
		GridLayout gl = new GridLayout(4, 2);
		gl.setHgap(8);
		gl.setVgap(8);
		gridPanel.setLayout(gl);
		gridPanel.setBorder(new WindowBorder(GuiControl.WINDOW_BORDER));

		Util.makeLabel(gridPanel, "<html>ISBN<sup color=red>*<sup></html>");
		//isbn = new JTextField(10);
		isbn = new CustomTextField(10);
		gridPanel.add(isbn);

		Util.makeLabel(gridPanel, "<html>Title<sup color=red>*<sup></html>");
		//title = new JTextField(10);
		title = new CustomTextField(10);
		gridPanel.add(title);

		Util.makeLabel(gridPanel, "Checkout days");
		check1 = new JRadioButton("7");
		check2 = new JRadioButton("21");
		buttonGroup =new ButtonGroup();    
		buttonGroup.add(check1);
		buttonGroup.add(check2);
		JPanel rButtons = new JPanel(); //uses FlowLayout by default
	    rButtons.add(check1);
	    rButtons.add(check2);
	    gridPanel.add(rButtons);
		
		Util.makeLabel(gridPanel, "Author");

		TestData td = new TestData();
		List<Author> authors = td.allAuthors;
		DefaultListModel<Author> modelAuthor = new DefaultListModel<>();
		//Sorry, just get 3 authors
		modelAuthor.addElement(authors.get(0));
		modelAuthor.addElement(authors.get(1));
		modelAuthor.addElement(authors.get(2));
//		for(Author auth: authors) {
//			lmodelAuthor1.addElement(auth);
//		}
		authorList = new JList<>();
		authorList.setModel(modelAuthor);
        gridPanel.add(authorList);
	}
	
	private void cancelButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);	
		});
	}
	
	private void addNewBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			boolean flag1 = true;//Util.validateNumberFormat(isbn, "^([0-9]{2})-([0-9]{5}))$", true, "##-#####");
			boolean flag2 = Util.validateMandatory(title);
			if(flag1 && flag2) {
				List<Author> authors = authorList.getSelectedValuesList();
				ci.addNewBook(isbn.getText(), title.getText(), (check1.isSelected()?7:21), authors);
				LibrarySystem.hideAllWindows();
				AllBookIdsWindow.INSTANCE.loadTableContent();
				AllBookIdsWindow.INSTANCE.setVisible(true);
			}
		});
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
	
	public void resetFrame() {
		isbn.setText("");
		title.setText("");
		buttonGroup.clearSelection();
		authorList.clearSelection();
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
