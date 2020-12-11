package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.Client;
import DAO.ClientDAO;

import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class LoginWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField signupLoginTxt;
	private JPasswordField signupPassTxt;
	private JTextField loginLoginTxt;
	private JPasswordField loginPassTxt;
	
	private JButton loginBtn;
	private JButton moveToSignupBtn;
	private JButton signUpBtn;
	private JButton moveToLoginBtn;
	
	private JLabel errorLbl;
	private JLabel signupErrorLbl;
	
	private ClientDAO cd;

	public LoginWindow() {
		cd = new ClientDAO();
		
		setTitle("Log In");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel logIn = new JPanel();
		contentPane.add(logIn, "LOGIN_PANEL");
		logIn.setLayout(new BorderLayout(0, 0));
		
		JPanel controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		logIn.add(controlPanel, BorderLayout.SOUTH);
		
		loginBtn = new JButton("Log In");
		loginBtn.setMnemonic(' ');
		controlPanel.add(loginBtn);
		loginBtn.addActionListener(this);
		
		moveToSignupBtn = new JButton("Sign Up");
		controlPanel.add(moveToSignupBtn);
		moveToSignupBtn.addActionListener(this);
		
		JPanel loginPanel = new JPanel();
		logIn.add(loginPanel, BorderLayout.CENTER);
		loginPanel.setLayout(null);
		
		JPanel signupInner_1 = new JPanel();
		signupInner_1.setBounds(43, 35, 200, 60);
		loginPanel.add(signupInner_1);
		signupInner_1.setLayout(new GridLayout(2, 2, 0, 20));
		
		JLabel loginLoginLbl = new JLabel("Login");
		loginLoginLbl.setHorizontalAlignment(SwingConstants.LEFT);
		signupInner_1.add(loginLoginLbl);
		
		loginLoginTxt = new JTextField();
		loginLoginTxt.setColumns(10);
		signupInner_1.add(loginLoginTxt);
		
		JLabel loginPassLbl = new JLabel("Password");
		loginPassLbl.setHorizontalAlignment(SwingConstants.LEFT);
		signupInner_1.add(loginPassLbl);
		
		loginPassTxt = new JPasswordField();
		signupInner_1.add(loginPassTxt);
		
		JLabel loginLbl = new JLabel("Log In");
		loginLbl.setHorizontalAlignment(SwingConstants.CENTER);
		loginLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginLbl.setBounds(103, 0, 80, 25);
		loginPanel.add(loginLbl);
		
		errorLbl = new JLabel("");
		errorLbl.setFont(new Font("Tahoma", Font.ITALIC, 8));
		errorLbl.setForeground(Color.RED);
		errorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorLbl.setBounds(43, 21, 200, 14);
		loginPanel.add(errorLbl);
		
		JPanel signUp = new JPanel();
		contentPane.add(signUp, "SIGNUP_PANEL");
		signUp.setLayout(new BorderLayout(0, 0));
		
		JPanel controlSignupPanel = new JPanel();
		FlowLayout fl_controlSignupPanel = (FlowLayout) controlSignupPanel.getLayout();
		fl_controlSignupPanel.setAlignment(FlowLayout.RIGHT);
		signUp.add(controlSignupPanel, BorderLayout.SOUTH);
		
		signUpBtn = new JButton("Sign Up");
		controlSignupPanel.add(signUpBtn);
		signUpBtn.addActionListener(this);
		
		moveToLoginBtn = new JButton("Log In");
		controlSignupPanel.add(moveToLoginBtn);
		moveToLoginBtn.addActionListener(this);
		
		JPanel signupPanel = new JPanel();
		signUp.add(signupPanel, BorderLayout.CENTER);
		signupPanel.setLayout(null);
		
		JPanel signupInner = new JPanel();
		signupInner.setBounds(43, 35, 200, 60);
		signupPanel.add(signupInner);
		signupInner.setLayout(new GridLayout(2, 2, 0, 20));
		
		JLabel signupLoginLbl = new JLabel("Login");
		signupLoginLbl.setHorizontalAlignment(SwingConstants.LEFT);
		signupInner.add(signupLoginLbl);
		
		signupLoginTxt = new JTextField();
		signupInner.add(signupLoginTxt);
		signupLoginTxt.setColumns(10);
		
		JLabel signupPassLbl = new JLabel("Password");
		signupPassLbl.setHorizontalAlignment(SwingConstants.LEFT);
		signupInner.add(signupPassLbl);
		
		signupPassTxt = new JPasswordField();
		signupInner.add(signupPassTxt);
		
		JLabel signupLbl = new JLabel("Sign Up");
		signupLbl.setHorizontalAlignment(SwingConstants.CENTER);
		signupLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		signupLbl.setBounds(103, 0, 80, 25);
		signupPanel.add(signupLbl);
		
		signupErrorLbl = new JLabel("");
		signupErrorLbl.setForeground(Color.RED);
		signupErrorLbl.setFont(new Font("Tahoma", Font.ITALIC, 11));
		signupErrorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		signupErrorLbl.setBounds(43, 21, 200, 14);
		signupPanel.add(signupErrorLbl);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(moveToSignupBtn)) {
			CardLayout cl = (CardLayout) contentPane.getLayout();
			cl.show(contentPane, "SIGNUP_PANEL");
		}else if(e.getSource().equals(moveToLoginBtn)) {
			CardLayout cl = (CardLayout) contentPane.getLayout();
			cl.show(contentPane, "LOGIN_PANEL");
		}else if(e.getSource().equals(loginBtn)) {
			Client c = cd.authorise(loginLoginTxt.getText(), loginPassTxt.getPassword());
			if(c != null) {
				
				new ClientWindow(c, cd);
				this.dispose();
			}else {
				errorLbl.setText("Wrong Login or Password, please try again");
				
			}
		}else if(e.getSource().equals(signUpBtn)) {
			if(cd.loginAvailable(signupLoginTxt.getText())) {
				Client c = new Client(cd.getNextID(), signupLoginTxt.getText(), new String(signupPassTxt.getPassword())); 
				cd.add(c);
				new ClientWindow(c, cd);
				
				this.dispose();
			}else {
				signupErrorLbl.setText("This login is already taken");
			}
		}
		
	}
}
