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
import entities.LibraryMember;

public class AddMemberWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 3L;
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();

    public static int memberId = 0;
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
    private JTextField firstname;
    private JTextField lastname;
    private JTextField phone;
    private JTextField street;
    private JTextField city;
    private JTextField state;
    private JTextField zip;

    private AddMemberWindow() {}

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel(AddMemberWindow.memberId);
        //defineLowerPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        isInitialized = true;
        setTitle(this.MAIN_LABEL);
    }
    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel addBookLabel = new JLabel("Add member");
        Util.adjustLabelFont(addBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(addBookLabel);
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
        makeLabel(gridPanel, "Member ID");
        id = new JTextField(10);
        id.setText(String.valueOf(maxID));
        id.setEnabled(false);
        gridPanel.add(id);

        makeLabel(gridPanel, "Firstname");
        firstname = new JTextField(20);
        gridPanel.add(firstname);

        makeLabel(gridPanel, "Lastname");
        lastname = new JTextField(20);
        gridPanel.add(lastname);

        makeLabel(gridPanel, "telephone");
        phone = new JTextField(10);
        gridPanel.add(phone);

        makeLabel(gridPanel, "Street");
        street = new JTextField(30);
        gridPanel.add(street);

        makeLabel(gridPanel, "city");
        city = new JTextField(10);
        gridPanel.add(city);

        makeLabel(gridPanel, "state");
        state = new JTextField(15);
        gridPanel.add(state);

        makeLabel(gridPanel, "zipcode");
        zip = new JTextField(7);
        gridPanel.add(zip);


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

    public boolean isInitialized(){
        return isInitialized;
    }

    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }
}
