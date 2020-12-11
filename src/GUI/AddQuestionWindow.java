package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.Field;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class AddQuestionWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField questionTxt;
	
	private JButton addBtn;
	private JButton cancelBtn;
	private JComboBox<String> typeCmbx;
	
	List<Field> fields;
	FormWindow f;
	/**
	 * Create the frame.
	 */
	public AddQuestionWindow(List<Field> fields, FormWindow formWindow) {
		this.fields = fields;
		this.f = formWindow;
		
		setTitle("Add Question");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel questionPanel = new JPanel();
		contentPane.add(questionPanel, BorderLayout.CENTER);
		questionPanel.setLayout(new GridLayout(0, 2, 5, 5));
		
		JLabel questionLabel = new JLabel("Question text:");
		questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		questionPanel.add(questionLabel);
		
		questionTxt = new JTextField("");
		questionPanel.add(questionTxt);
		questionTxt.setColumns(10);
		
		JLabel typeLabel = new JLabel("Question type: ");
		questionPanel.add(typeLabel);
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		typeCmbx = new JComboBox<String>();
		questionPanel.add(typeCmbx);
		typeCmbx.setModel(new DefaultComboBoxModel<String>(new String[] {"--------", "String", "Integer"}));
		
		JPanel controlPanel = new JPanel();
		contentPane.add(controlPanel, BorderLayout.SOUTH);
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		addBtn = new JButton("Add");
		controlPanel.add(addBtn);
		
		cancelBtn = new JButton("Cancel");
		controlPanel.add(cancelBtn);
		
		addBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cancelBtn)) {
			this.dispose();
		}else if(e.getSource().equals(addBtn)) {
			if(!typeCmbx.getSelectedItem().equals("--------")) {
				fields.add(new Field(questionTxt.getText(), typeCmbx.getSelectedItem().toString()));
				f.populateTable();
				this.dispose();
			}
			
		}
		
	}

}
