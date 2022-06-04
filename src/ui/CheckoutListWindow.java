package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import business.ControllerInterface;
import business.SystemController;
import entities.CheckoutRecord;
import entities.LibraryMember;

public class CheckoutListWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 2L;
    public static final CheckoutListWindow INSTANCE = new CheckoutListWindow();
    private List<CheckoutRecord> records = new ArrayList<>();
    private boolean isInitialized = false;
    ControllerInterface ci = new SystemController();
    private int maxID = 0;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private CustomTableModel model;

    private JScrollPane tablePane;
    private JTable table;
    public static Color DARK_BLUE = Color.blue.darker();
    public static Color LIGHT_BLUE = new Color(0xf2ffff);
    public static Color TABLE_HEADER_FOREGROUND = LIGHT_BLUE;
    public static Color TABLE_HEADER_BACKGROUND = DARK_BLUE;

    private final int TABLE_WIDTH = Math.round(0.75f*Util.SCREEN_WIDTH);
    private final int DEFAULT_TABLE_HEIGHT = Math.round(0.75f*Util.SCREEN_HEIGHT);
    public JPanel getMainPanel() {
        return mainPanel;
    }
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
        isInitialized = true;
    }
    
    public void setRecordsData(List<CheckoutRecord> records2) {
		// TODO Auto-generated method stub
		this.records = records2;
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
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        //textArea = new TextArea(8,20);
        //middlePanel.add(textArea);
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
        JButton listCheckout = new JButton("Checkout Record List");
        checkoutListListener(listCheckout);
        lowerPanel.add(listCheckout);
    }

    private void checkoutListListener(JButton butn) {
        butn.addActionListener(evt -> {
        	int index = table.getSelectedRow();
			if (index >= 0) {
				List<LibraryMember> data = ci.allMembers();
				List<CheckoutRecord> records = ci.getCheckoutByMemberId(data.get(index).getMemberId());
				LibrarySystem.hideAllWindows();
				AllBookIdsWindow.INSTANCE.init();
				//AllBookIdsWindow.INSTANCE.pack();
				//AllBookIdsWindow.INSTANCE.setSize(660,500);
				Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
				AllBookIdsWindow.INSTANCE.setVisible(true);
			}
            System.out.printf("call in MemberWindow");
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void generateMemberGrid() {
        List<LibraryMember> data = ci.allMembers();
        String[] columnNames = {"ID", "Firstname", "Lastname", "Telephone", "Address"};
        String[][] contents = new String[data.size()][5];
        int i = 0;
        for(LibraryMember m: data) {
            int mid = Integer.parseInt(m.getMemberId());
            if (mid > this.maxID) {
                this.maxID = mid;
            }
            System.out.printf(m.getMemberId() + ":" + m.getFirstName());
            contents[i][0] = m.getMemberId();
            contents[i][1] = m.getFirstName();
            contents[i][2] = m.getLastName();
            contents[i][3] = m.getTelephone();
            contents[i][4] = m.getAddress().toString();

            i++;
        }
        table = new JTable(contents, columnNames);
        tablePane = new JScrollPane();
        tablePane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        tablePane.getViewport().add(table);
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.printf("call in MemberWindow");
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    public boolean isInitialized(){
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }

}
