package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import business.ControllerInterface;
import business.SystemController;
import entities.CheckoutRecord;
import entities.CheckoutRecordEntry;

public class CheckoutListWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 2L;
    public static final CheckoutListWindow INSTANCE = new CheckoutListWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	
	private JScrollPane tablePane;
	private JTable table;
	DefaultTableModel model;
	
	private String memID = null;
	
	public void setMemberID(String id) {
		this.memID = id;
	}
	
	//Singleton class
	private CheckoutListWindow() {}
	
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
		this.setSize(660,500);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Checkout Record");
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
		String[] columnNames = {"ISBN", "Book Title", "Copy number", "Checkout Date", "Due Date"};
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
		model.setRowCount(0);
		if (this.memID != null) {
			//Add content
			List<CheckoutRecord> data = ci.getCheckoutByMemberId(this.memID);
			//Collections.sort(data, new Sortbyroll());
			for(CheckoutRecord x: data) {
				for(CheckoutRecordEntry y: x.getEntries()) {
					model.addRow(new Object[] {
							y.getBookCopy().getBook().getIsbn(),
							y.getBookCopy().getBook().getTitle(),
							y.getBookCopy().getBook().getNumCopies(),
							y.getCheckoutDate(),
							y.getDueDate()
					});
				}
			}
		}
		Util.resizeColumnWidth(table);
	}
	
	public void defineLowerPanel() {
		JButton backToMainButn = new JButton("<= Back to Main");
		backButtonListener(backToMainButn);
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);
		JButton print = new JButton("Print");
		printCheckoutButtonListener(print);
		lowerPanel.add(print);
		JButton backMem = new JButton("<= Back to Member List");
		backMemButtonListener(backMem);
		lowerPanel.add(backMem);
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
	
	private void backButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}
	
	private void backMemButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			MemberWindow.INSTANCE.clearTableSelect();
			MemberWindow.INSTANCE.setVisible(true);
		});
	}
	
	private void printCheckoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			ci.printCheckoutRecord(this.memID);
		});
	}

}
