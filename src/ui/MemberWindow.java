package ui;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;
import entities.LibraryMember;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;

public class MemberWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 2L;
    public static final MemberWindow INSTANCE = new MemberWindow();
    private boolean isInitialized = false;
    ControllerInterface ci = new SystemController();
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JScrollPane tablePane;
    private JTable table;
    DefaultTableModel model;
    
    public JPanel getMainPanel() {
        return mainPanel;
    }
    private MemberWindow() {}
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
        setTitle(Util.MAIN_LABEL);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel memberFormLabel = new JLabel("All Members");
        Util.adjustLabelFont(memberFormLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(memberFormLabel);
    }
    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
        middlePanel.setLayout(fl);
        String[] columnNames = {"ID", "Firstname", "Lastname", "Telephone", "Address"};
        model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
        table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        for(String column: columnNames) {
        	model.addColumn(column);
        }
        tablePane = new JScrollPane();
        tablePane.setPreferredSize(new Dimension(Util.TABLE_WIDTH, Util.DEFAULT_TABLE_HEIGHT));
        tablePane.getViewport().add(table);
        generateMemberGrid();
        JPanel tablePanePanel = Util.createStandardTablePanePanel(table, tablePane);
        middlePanel.add(tablePanePanel);
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);

        JButton addMember = new JButton("Add Member");
        addMemberButtonListener(addMember);
        lowerPanel.add(addMember);
        Auth loginedRole = SystemController.currentAuth;
        if(loginedRole.equals(Auth.BOTH) || loginedRole.equals(Auth.LIBRARIAN)) { 
			JButton checkout = new JButton("Checkout List");
			listCheckoutButtonListener(checkout);
			lowerPanel.add(checkout);
        }
    }

    public void generateMemberGrid() {
    	clearTableSelect();
    	model.setRowCount(0);
    	List<LibraryMember> data = ci.allMembers();
        for(LibraryMember m: data) {
        	model.addRow(new Object[] {m.getMemberId(), m.getFirstName(), m.getLastName(), m.getTelephone(), m.getAddress().toString()});
        }
        //Util.resizeColumnWidth(table);
    }
    
    public void clearTableSelect() {
    	table.clearSelection();
    }

    private void addMemberButtonListener(JButton btn) {
        btn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AddMemberWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AddMemberWindow.INSTANCE);
            AddMemberWindow.INSTANCE.resetData();
            AddMemberWindow.INSTANCE.setVisible(true);
        });
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }
	
	private void listCheckoutButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			int row = table.getSelectedRow();
			if (row >= 0) {
				String id = String.valueOf(table.getValueAt(row, 0));
				CheckoutListWindow.INSTANCE.setMemberID(id);
				LibrarySystem.hideAllWindows();
				Util.centerFrameOnDesktop(CheckoutListWindow.INSTANCE);
				CheckoutListWindow.INSTANCE.loadTableContent();
				CheckoutListWindow.INSTANCE.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Please select a member", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
	}

    public boolean isInitialized(){
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }

}
