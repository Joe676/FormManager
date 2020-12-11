package GUI;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import DAO.Field;
import javax.swing.JTextField;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

public class QuestionPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	Field field;
	String answer;
	
	JTextField answerTxt;
	List<JRadioButton> buttons;
	ButtonGroup group;
	private JPanel panel;
	private JPanel answerPart;
	private JPanel questionPart;
	private JLabel questionLbl;
	
	public static int height = 80;

	public QuestionPanel(Field field) {
		this.field = field;
		setLayout(new GridLayout(0, 1, 0, 0));
		setPreferredSize(new Dimension(400, height));
		setMaximumSize(new Dimension(1000, height));
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel);
		panel.setLayout(new GridLayout(1, 2, 15, 0));
		
		questionPart = new JPanel();
		panel.add(questionPart);
		questionPart.setLayout(null);
		
		questionLbl = new JLabel("<html>"+field.getName()+"<html>");
		questionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		questionLbl.setBounds(5, 0, 180, 78);
		questionLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		questionPart.add(questionLbl);
		
		answerPart = new JPanel();
		panel.add(answerPart);
		
		generateAnswer(field.getType());
	}

	private void generateAnswer(String type) {
		if(type.equals("String")) {
			answerTxt = new JTextField("");
			answerTxt.addActionListener(this);
			//answerTxt.setBounds(5, 24, 180, 30);
			//answerTxt.setSize(180, 30);
			answerPart.setLayout(new GridLayout(3, 1));
			answerPart.add(new JPanel());
			answerPart.add(answerTxt);
			answerPart.add(new JPanel());
		}else if(type.equals("Integer")) {
			buttons = new ArrayList<JRadioButton>();
			group = new ButtonGroup();
			answerPart.setLayout(new GridLayout(2, 5));
			for(int i = 0; i < 10; i++) {
				JRadioButton b = new JRadioButton(String.valueOf(i+1));
				buttons.add(b);
				group.add(b);
				if(i==9)b.setSelected(true);
				answerPart.add(b);
				b.addActionListener(this);
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(answerTxt)) {
			answer = answerTxt.getText();
		}else {
			for(JRadioButton btn: buttons) {
				if(btn.isSelected()) {
					answer = btn.getText();
				}
			}
		}
	}
	
	public String getAnswer() {
		if(field.getType().equals("Integer") && buttons!=null){
			for(JRadioButton btn: buttons) {
				if(btn.isSelected()) {
					return btn.getText();
				}
			}
		}else{
			return answerTxt.getText() == null? "" : answerTxt.getText();
		} 
		return answer;
	}

}
