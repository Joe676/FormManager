package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.Answer;
import DAO.AnswerDAO;
import DAO.Client;
import DAO.ClientDAO;
import DAO.Field;
import DAO.Form;
import DAO.FormDAO;
import DAO.Service;
import DAO.ServiceDAO;

import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;

public class ClientWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JComboBox<Object> serviceCmbx;
	private JComboBox<Object> formCmbx;
	private JButton showBtn;
	private JButton submitBtn;
	private JScrollPane form; 
	private JPanel questionsPanel;
	private JButton updateAccountBtn;
	private JLabel updateInfo;
	
	private JTabbedPane tabbedPane;
	private JPanel answersTab;
	private JPanel accountTab;
	
	private JPanel formPanel;
	
	private Client user;
	private Service currentService;
	private Form currentForm;
	
	private List<QuestionPanel> questions;
	
	private ClientDAO cd;
	private ServiceDAO sd;
	private FormDAO fd;
	private AnswerDAO ad;
	private JTextField updateLoginTxt;
	private JPasswordField updatePasswordTxt;
	
	public ClientWindow(Client c, ClientDAO cd) {
		user = c;
		this.cd = cd;
		fd = new FormDAO();
		sd = new ServiceDAO(fd);
		ad = new AnswerDAO(sd, fd, cd);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel formsTab = new JPanel();
		tabbedPane.addTab("Forms", null, formsTab, null);
		formsTab.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		formsTab.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 3, 5, 0));
		
		serviceCmbx = new JComboBox<Object>();
		serviceCmbx.setModel(new DefaultComboBoxModel<Object>(new String[] {"---Choose service---"}));
		serviceCmbx.addActionListener(this);
		panel.add(serviceCmbx);
		for(Service service: sd.getAll()) {
			serviceCmbx.addItem(service);
		}
		
		
		formCmbx = new JComboBox<Object>();
		formCmbx.setModel(new DefaultComboBoxModel<Object>(new String[] {"---Choose form---"}));
		panel.add(formCmbx);
		
		showBtn = new JButton("Show form");
		panel.add(showBtn);
		showBtn.addActionListener(this);
		
		formPanel = new JPanel();
		formsTab.add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel submitPanel = new JPanel();
		FlowLayout fl_submitPanel = (FlowLayout) submitPanel.getLayout();
		fl_submitPanel.setAlignment(FlowLayout.RIGHT);
		formPanel.add(submitPanel, BorderLayout.SOUTH);
		
		submitBtn = new JButton("Submit");
		submitPanel.add(submitBtn);
		submitBtn.addActionListener(this);
		
		questionsPanel = new JPanel();
		//form.setViewportView(questionsPanel);
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		
		
		setupAnswers();
		setupAccount();

		setVisible(true);
	}

	private void setupAnswers() {
		answersTab = new AnswersPanel(sd, fd, cd, ad);
		tabbedPane.addTab("Answers", null, answersTab, null);
	}
	
	private void setupAccount() {
		accountTab = new JPanel();
		tabbedPane.addTab("Account", null, accountTab, null);
		accountTab.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		accountTab.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel accountPanel = new JPanel();
		accountPanel.setBounds(100, 74, 370, 166);
		panel.add(accountPanel);
		accountPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JPanel loginLblPanel = new JPanel();
		loginLblPanel.setLayout(null);
		accountPanel.add(loginLblPanel);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(32, 34, 123, 25);
		loginLblPanel.add(lblNewLabel);
		
		JPanel loginTxtPanel = new JPanel();
		loginTxtPanel.setLayout(null);
		accountPanel.add(loginTxtPanel);
		
		updateLoginTxt = new JTextField(user.getName());
		updateLoginTxt.setBounds(32, 31, 120, 20);
		loginTxtPanel.add(updateLoginTxt);
		updateLoginTxt.setColumns(10);
		
		JPanel passwordLblPanel = new JPanel();
		passwordLblPanel.setLayout(null);
		accountPanel.add(passwordLblPanel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(39, 26, 107, 31);
		passwordLblPanel.add(lblNewLabel_1);
		
		JPanel passwordTxtPanel = new JPanel();
		passwordTxtPanel.setLayout(null);
		accountPanel.add(passwordTxtPanel);
		
		updatePasswordTxt = new JPasswordField();
		updatePasswordTxt.setBounds(32, 31, 120, 20);
		passwordTxtPanel.add(updatePasswordTxt);
		
		updateInfo = new JLabel("");
		updateInfo.setFont(new Font("Tahoma", Font.ITALIC, 11));
		updateInfo.setForeground(Color.RED);
		updateInfo.setHorizontalAlignment(SwingConstants.CENTER);
		updateInfo.setBounds(100, 49, 370, 14);
		panel.add(updateInfo);
		
		JPanel accountControlPanel = new JPanel();
		FlowLayout fl_accountControlPanel = (FlowLayout) accountControlPanel.getLayout();
		fl_accountControlPanel.setAlignment(FlowLayout.RIGHT);
		accountTab.add(accountControlPanel, BorderLayout.SOUTH);
		
		updateAccountBtn = new JButton("Update");
		accountControlPanel.add(updateAccountBtn);
		updateAccountBtn.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(serviceCmbx)) {
			String[] s = serviceCmbx.getSelectedItem().toString().split(";");
			if(s.length==2) {
				formCmbx.removeAllItems();
				formCmbx.addItem("---Choose form---");
				currentService = sd.get(Long.parseLong(s[0]));
				for(Form form: currentService.getForms()) {
					formCmbx.addItem(form.toString());
				}
			}
		}else if(e.getSource().equals(showBtn)) {
			if(currentService!=null) {
				String[] s = formCmbx.getSelectedItem().toString().split(";");
				if(s.length==2) {
					currentForm = fd.get(Long.parseLong(s[0]));
					generateForm();
				}
			}
		}else if(e.getSource().equals(submitBtn)) {
			Submit();
		}else if(e.getSource().equals(updateAccountBtn)) {
			if(cd.authorise(user.getName(), updatePasswordTxt.getPassword())!=null) {
				user.setName(updateLoginTxt.getText());
				cd.update(user);
				updateInfo.setText("Update successful");
			}else {
				updateInfo.setText("Wrong password, try again later");
			}
			
		}
	}

	private void Submit() {
		if(questions==null || currentService==null || currentForm==null) {
			return;
		}
		List<String> answers = new ArrayList<String>();
		for(QuestionPanel q: questions) {
			answers.add(q.getAnswer());
		}
		Answer a = new Answer(ad.getNextID(), currentService, currentForm, user, answers);
		ad.add(a);
		currentService = null;
		currentForm = null;
		serviceCmbx.setSelectedIndex(0);
		formCmbx.setSelectedIndex(0);
	}

	private void generateForm() {
		
		if(questions == null)questions = new ArrayList<QuestionPanel>();
		else questions.clear();
		questionsPanel.removeAll();
		questionsPanel.setPreferredSize(new Dimension(questionsPanel.getWidth(), QuestionPanel.height*currentForm.getFields().size()));
		for(Field question: currentForm.getFields()) {
			QuestionPanel q = new QuestionPanel(question); 
			questions.add(q);
			questionsPanel.add(q);
		}
		questionsPanel.validate();
		questionsPanel.repaint();
		
		if(form == null) {
			form = new JScrollPane(questionsPanel);
			form.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			form.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			formPanel.add(form, BorderLayout.CENTER);
		}
		form.validate();
		form.repaint();
		formPanel.validate();
		formPanel.repaint();
	}
}
