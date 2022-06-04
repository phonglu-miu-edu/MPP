package ui;
import business.ControllerInterface;
import business.SystemController;
import entities.Author;
import entities.Book;
import entities.LibraryMember;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public class MemberWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 2L;
    public static final MemberWindow INSTANCE = new MemberWindow();
    private boolean isInitialized = false;
    ControllerInterface ci = new SystemController();
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
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel memberFormLabel = new JLabel("Member");
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
    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);
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
