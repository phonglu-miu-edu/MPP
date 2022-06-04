package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import business.ControllerInterface;
import business.SystemController;
import entities.Author;
import entities.Book;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    private final float [] COL_WIDTH_PROPORTIONS =	{1.0f};
    private int maxID = 0;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;
    private CustomTableModel model;
	
	private JScrollPane tablePane;
	private JTable table;
	private String[] header;
	

	public static Color DARK_BLUE = Color.blue.darker();
	public static Color LIGHT_BLUE = new Color(0xf2ffff);
	public static Color TABLE_HEADER_FOREGROUND = LIGHT_BLUE;
	public static Color TABLE_HEADER_BACKGROUND = DARK_BLUE;	
	
	private final int TABLE_WIDTH = Math.round(0.75f*Util.SCREEN_WIDTH);
    private final int DEFAULT_TABLE_HEIGHT = Math.round(0.75f*Util.SCREEN_HEIGHT);
	private String[][] contents;
	
	//Singleton class
	private AllBookIdsWindow() {}
	
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
		JLabel AllIDsLabel = new JLabel("All Book IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		textArea = new TextArea(8, 20);
		//create and add table
		createTableAndTablePane();
		JPanel tablePanePanel = Util.createStandardTablePanePanel(table,tablePane);

		//populateTextArea();
		//middlePanel.add(textArea);
		JButton addBook = new JButton("Add Book");
		addBookButtonListener(addBook);
		middlePanel.add(tablePanePanel);
		middlePanel.add(addBook);
		
	}
	
	private void addBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AddNewBookWindow.bookID = maxID+1;
			AddNewBookWindow.INSTANCE.init();
			AddNewBookWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AddNewBookWindow.INSTANCE);
			AddNewBookWindow.INSTANCE.setVisible(true);	
		});
	}
	
	private void updateModel() {
		List<String[]> theData = new ArrayList<String[]>();
		ci.allBooks();
		header = new String[]{"List of Available Books"};
		updateModel(theData);
 	}
	
	public void updateModel(List<String[]> list){
		if(model == null) {
	        model = new CustomTableModel();
    	    
		}
		//header = new String[]{"ID", "Title", "ISBN", "Copies", "Authors"};
		model.setTableValues(list);	
	}
	
	private void createTableAndTablePane() {
		//updateModel();
		List<Book> data = ci.allBooks();
		String[] columnNames = {"ID", "Title", "ISBN", "Copies", "Authors", "", "", ""};
		String[][] contents = new String[data.size()][8];
		int i = 0;
		for(Book x: data) {
			if (Integer.parseInt(x.getId()) > this.maxID) {
				this.maxID = Integer.parseInt(x.getId());
			}
			String name = "";
			String com = "";
			for(Author au: x.getAuthors()) {
				name += com + au.getFirstName() + " " + au.getLastName();
				com = ", ";
			}
			contents[i][0] = x.getId();
			contents[i][1] = x.getTitle();
			contents[i][2] = x.getIsbn();
			contents[i][3] = String.valueOf(x.getNumCopies());
			contents[i][4] = name;
			contents[i][5] = "";
			contents[i][6] = "";
			contents[i][7] = "";
			i++;
		}
		table = new JTable(contents, columnNames);
		tablePane = new JScrollPane();
		tablePane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
		tablePane.getViewport().add(table);
		//updateTable();
	}
	
	public void defineLowerPanel() {
		
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
	}
	
	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}
	
	public void setData(String data) {
		textArea.setText(data);
	}
	
//	private void populateTextArea() {
//		//populate
//		List<String> ids = ci.allBookIds();
//		Collections.sort(ids);
//		StringBuilder sb = new StringBuilder();
//		for(String s: ids) {
//			sb.append(s + "\n");
//		}
//		textArea.setText(sb.toString());
//	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
}
