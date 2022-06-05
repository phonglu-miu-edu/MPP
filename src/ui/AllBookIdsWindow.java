package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import business.ControllerInterface;
import business.SystemController;
import entities.Book;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	
	private JScrollPane tablePane;
	private JTable table;
	DefaultTableModel model;
	
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
		setTitle(Util.MAIN_LABEL);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Books");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table = new JTable();
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//Add columns
		String[] columnNames = {"ID", "Title", "ISBN", "Copies", "Authors"};
		for(String column: columnNames) {
			model.addColumn(column);
		}

		//load data for a table
		loadTableContent();
		
		tablePane = new JScrollPane();
		tablePane.setPreferredSize(new Dimension(Util.TABLE_WIDTH, Util.DEFAULT_TABLE_HEIGHT));
		tablePane.getViewport().add(table);
		
		middlePanel = Util.createStandardTablePanePanel(table,tablePane);
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		middlePanel.setLayout(fl);
	}
	
	public void loadTableContent() {
    	clearTableSelect();
		model.setRowCount(0);
		
		//Add content
		List<Book> data = ci.allBooks();
		//Collections.sort(data, new Sortbyroll());
		for(Book x: data) {
			model.addRow(new Object[] {x.getId(), x.getTitle(), x.getIsbn(), String.valueOf(x.getNumCopies()), x.getAuthorListName()});
		}
		
		Util.resizeColumnWidth(table);
	}
	
	public void defineLowerPanel() {
		JButton backToMainButn = new JButton("<= Back to Main");
		backButtonListener(backToMainButn);
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);
		JButton addBook = new JButton("Add Book");
		JButton copyBook = new JButton("Copy Book");
		addBookButtonListener(addBook);
		copyBookButtonListener(copyBook);
		lowerPanel.add(addBook);
		lowerPanel.add(copyBook);
	}
	
	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
	
//	class Sortbyroll implements Comparator<Book>
//	{
//	    // Used for sorting in ascending order of
//	    // roll number
//	    public int compare(Book a, Book b)
//	    {
//	        return Integer.parseInt(a.getId()) - Integer.parseInt(b.getId());
//	    }
//	}
    
    public void clearTableSelect() {
    	table.clearSelection();
    }
	
	private void backButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}
	
	private void copyBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			int row = table.getSelectedRow();
			if (row >= 0) {
				//get book isbn
				String isbn = String.valueOf(table.getValueAt(row, 2));
				ci.addCopy(isbn);
				//refresh data
				loadTableContent();
			} else {
				JOptionPane.showMessageDialog(this, "Please select a book", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	private void addBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			AddNewBookWindow.INSTANCE.resetFrame();
			AddNewBookWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AddNewBookWindow.INSTANCE);
			AddNewBookWindow.INSTANCE.setVisible(true);	
		});
	}
}
