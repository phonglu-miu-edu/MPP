package ui;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.SystemController;

public class AddMemberWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 3L;
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();

    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    public JPanel getMainPanel() {
        return mainPanel;
    }
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;

    private JTextField firstname;
    private JTextField lastname;
    private CustomTextField phone;
    private JTextField street;
    private JTextField city;
    private JTextField state;
    private CustomTextField zip;

    private JButton addBtn;

    private AddMemberWindow() {}

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
		defineLowerPanel();
        //defineLowerPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        setTitle(Util.MAIN_LABEL);
        isInitialized = true;
    }
    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel addBookLabel = new JLabel("Add member");
        Util.adjustLabelFont(addBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(addBookLabel);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel gridPanel = new JPanel();
        middlePanel.add(gridPanel);
        GridLayout gl = new GridLayout(7, 2);
        gl.setHgap(8);
        gl.setVgap(8);
        gridPanel.setLayout(gl);
        gridPanel.setBorder(new WindowBorder(GuiControl.WINDOW_BORDER));

        //add fields
        Util.makeLabel(gridPanel, "<html>Firstname<sup color=red>*<sup></html>");
        firstname = new JTextField(10);
        gridPanel.add(firstname);

        Util.makeLabel(gridPanel, "<html>Lastname<sup color=red>*<sup></html>");
        lastname = new JTextField(10);
        gridPanel.add(lastname);

        Util.makeLabel(gridPanel, "<html>telephone<sup color=red>*<sup></html>");
        phone = new CustomTextField(10);
        phone.setPlaceholder("###-###-####");
        gridPanel.add(phone);

        Util.makeLabel(gridPanel, "Street");
        street = new JTextField(10);
        gridPanel.add(street);

        Util.makeLabel(gridPanel, "city");
        city = new JTextField(10);
        gridPanel.add(city);

        Util.makeLabel(gridPanel, "state");
        state = new JTextField(10);
        gridPanel.add(state);

        Util.makeLabel(gridPanel, "zipcode");
        zip = new CustomTextField(10);
        zip.setPlaceholder("#####");
        gridPanel.add(zip);
    }
    
    public void resetData() {
    	firstname.setText("");
    	lastname.setText("");
    	phone.setPlaceholder("###-###-####");
        street.setText("");
    	city.setText("");
    	state.setText("");
    	zip.setPlaceholder("#####");
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
        addBtn = new JButton("Add");
		addNewMemberButtonListener(addBtn);
		lowerPanel.add(cancelBtn);
		lowerPanel.add(addBtn);
	}
	
	private void cancelButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			Util.centerFrameOnDesktop(MemberWindow.INSTANCE);
			MemberWindow.INSTANCE.setVisible(true);	
		});
	}
	
	private void addNewMemberButtonListener(JButton btn) {
		btn.addActionListener(evt -> {
            boolean flag1 = Util.validateMandatory(firstname);
            boolean flag2 = Util.validateMandatory(lastname);

            boolean flag3 = Util.validateNumberFormat(phone,"^([0-9]{3})-([0-9]{3})-([0-9]{4})$", true, "###-###-####");
            boolean flag4 = Util.validateNumberFormat(zip,"^[0-9]{5}$", false, "#####");
            if(flag1 && flag2 && flag3 && flag4) {
                boolean result = ci.addMember(firstname.getText(),lastname.getText(),phone.getText(),street.getText(), city.getText(),state.getText(), zip.getText());
                if(!result) {
                	JOptionPane.showMessageDialog(this, "Can not add a new member because of phone dupplication", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Can not add a new member because of phone dupplication");
                } else {
	                LibrarySystem.hideAllWindows();
	                Util.centerFrameOnDesktop(MemberWindow.INSTANCE);
	                MemberWindow.INSTANCE.generateMemberGrid();
	                MemberWindow.INSTANCE.setVisible(true);
                }
            }
		});
	}
	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   Util.centerFrameOnDesktop(MemberWindow.INSTANCE);
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
