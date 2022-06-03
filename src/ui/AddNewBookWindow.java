package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.SystemController;

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
	private TextArea textArea;
	
	private AddNewBookWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
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
	
	public void defineMiddlePanel() {
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
		JTextField id = new JTextField(10);
		id.setEnabled(false);
		gridPanel.add(id);

		makeLabel(gridPanel, "ISBN");
		JTextField isbn = new JTextField(10);
		gridPanel.add(isbn);

		makeLabel(gridPanel, "Title");
		JTextField title = new JTextField(10);
		gridPanel.add(title);

		makeLabel(gridPanel, "Checkout days");	    
		JRadioButton check1 = new JRadioButton("7");
		JRadioButton check2 = new JRadioButton("21");
		ButtonGroup bg=new ButtonGroup();    
		bg.add(check1);
		bg.add(check2);
		JPanel rButtons = new JPanel(); //uses FlowLayout by default
	    rButtons.add(check1);
	    rButtons.add(check2);
	    gridPanel.add(rButtons);
		
		makeLabel(gridPanel, "Author");
		String[] authors = {"A", "B", "C"};
		JComboBox combo =new JComboBox(authors);
		gridPanel.add(combo);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelButtonListener(cancelBtn);
		JButton addBtn = new JButton("Add");
		addNewBookButtonListener(addBtn);
		gridPanel.add(cancelBtn);
		gridPanel.add(addBtn);
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
			LibrarySystem.hideAllWindows();
			AddNewBookWindow.INSTANCE.init();
			AddNewBookWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AddNewBookWindow.INSTANCE);
			AddNewBookWindow.INSTANCE.setVisible(true);	
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
	}
	
	public void setData(String data) {
		textArea.setText(data);
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
