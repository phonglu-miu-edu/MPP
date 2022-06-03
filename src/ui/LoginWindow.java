package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.User;
import models.LoginException;

public class LoginWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 3258408422029144633L;
	private boolean isInitialized = false;

	ControllerInterface ci = new SystemController();
	private Window parent;
	private final String MAIN_LABEL = "Library Management System";
	private final String SUBMIT_BUTN = "Login";
	private final String CANCEL_BUTN = "Cancel";

	private final String USER_ID = "Username";
	private final String PASSWORD = "Password";

	private JTextField userField;
	private JPasswordField pwdField;

	private JLabel messageBar;

	//JPanels

	JPanel mainPanel;
	JPanel upper, middle, lower;

	public LoginWindow() {
		init();
		defineMainPanel();
		getContentPane().add(mainPanel);
	}
	public void init() {
		setSize(Math.round(.7f*GuiControl.SCREEN_WIDTH),
				Math.round(.4f*GuiControl.SCREEN_HEIGHT));
		GuiControl.centerFrameOnDesktop(this);
		setTitle(this.MAIN_LABEL);
	}

	private void defineMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(GuiControl.FILLER_COLOR);
		mainPanel.setBorder(new WindowBorder(GuiControl.WINDOW_BORDER));
		defineUpperPanel();
		defineMiddlePanel();
		defineLowerPanel();
		mainPanel.add(upper,BorderLayout.NORTH);
		mainPanel.add(middle,BorderLayout.CENTER);
		mainPanel.add(lower,BorderLayout.SOUTH);
	}
	//label
	public void defineUpperPanel(){
		upper = new JPanel();
		upper.setBackground(GuiControl.FILLER_COLOR);
		upper.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel mainLabel = new JLabel("");
		Font f = GuiControl.makeVeryLargeFont(mainLabel.getFont());
		f = GuiControl.makeBoldFont(f);
		mainLabel.setFont(f);
		upper.add(mainLabel);
	}
	//table
	public void defineMiddlePanel(){
		middle = new JPanel();
		middle.setBackground(GuiControl.FILLER_COLOR);
		middle.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel gridPanel = new JPanel();
		gridPanel.setBackground(GuiControl.SCREEN_BACKGROUND);
		middle.add(gridPanel);
		GridLayout gl = new GridLayout(2,2);
		gl.setHgap(8);
		gl.setVgap(8);
		gridPanel.setLayout(gl);
		gridPanel.setBorder(new WindowBorder(GuiControl.WINDOW_BORDER));

		//add fields
		makeLabel(gridPanel, USER_ID);
		userField = new JTextField(10);
		gridPanel.add(userField);

		makeLabel(gridPanel,PASSWORD);
		pwdField = new JPasswordField(10);
		gridPanel.add(pwdField);
	}
	//buttons
	public void defineLowerPanel(){
		//submit button
		JButton submitButton = new JButton(SUBMIT_BUTN);
		//submitButton.addActionListener(new SubmitListener());
		this.addLoginButtonListener(submitButton);

		//cancel button
		//JButton cancelButton = new JButton(CANCEL_BUTN);
		//cancelButton.addActionListener(new CancelListener());

		//create lower panel
		//JButton [] buttons = {submitButton,cancelButton};
		JButton[] buttons = {submitButton};
		lower = GuiControl.createStandardButtonPanel(buttons);
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

	public void setParentWindow(Window parentWindow) {
		parent = parentWindow;
	}

	public Window getParentWindow() {
		return parent;
	}
	private void addLoginButtonListener(JButton btn) {
		btn.addActionListener(evt -> {
			try {
				String username = this.userField.getText().trim();
				char[] pwdChars = this.pwdField.getPassword();
				String password = new String(pwdChars).trim();

				User loginedUser = ci.login(username, password);
				System.out.println(SystemController.currentAuth);
				System.out.println(loginedUser.getId());
				setVisible(false);
				//changeScreen with auth
			} catch(LoginException ex) {
				System.out.println(ex.getMessage());
			}
		});
	}

	/*class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			//setVisible(false);
			String id = userField.getText();
			char[] pwdAsChars = pwdField.getPassword();
			String pwd = new String(pwdAsChars);
			System.out.println(pwd);

			//revise this
			boolean authenticated = true;

			//LoginControl control = new LoginControl();
			//boolean authenticated = control.authenticate(id,pwd);
			if(authenticated) {
				dispose();
			}
			else {
				String errMsg = "Login failed.";
				JOptionPane.showMessageDialog(LoginWindow.this,
						errMsg,
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}*/
	class CancelListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if(parent != null) {
				parent.setVisible(true);
			}
			LoginWindow.this.dispose();
		}
	}
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public static void main(String[] args) {
		(new LoginWindow()).setVisible(true);
	}
}
