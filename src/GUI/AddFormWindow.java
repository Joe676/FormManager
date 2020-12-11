package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.Form;
import DAO.FormDAO;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;

public class AddFormWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private FormDAO fd;
	private JComboBox<Form> forms;
	private JButton addBtn;
	private JButton finishBtn;
	private List<Form> out;
	private ServiceWindow s;
	
	public AddFormWindow(FormDAO f, List<Form> out, ServiceWindow serviceWindow) {
		fd = f;
		this.out = out;
		s = serviceWindow;
		
		setTitle("Add Forms");
		setSize(400, 100);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		FlowLayout fl_contentPane = new FlowLayout(FlowLayout.CENTER, 5, 5);
		contentPane.setLayout(fl_contentPane);
		
		forms = new JComboBox<Form>();
		for(Form form: fd.getAll()) {
			forms.addItem(form);
		}
		contentPane.add(forms);
		
		addBtn = new JButton("Add");
		contentPane.add(addBtn);
		
		finishBtn = new JButton("Finish");
		contentPane.add(finishBtn);
		
		addBtn.addActionListener(this);
		finishBtn.addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(finishBtn)) {
			this.dispose();
		}else if(e.getSource().equals(addBtn)) {
			boolean contains = false;
			for(Form f: out) {
				if(f.getID() == ((Form)forms.getSelectedItem()).getID())contains=true;
			}
			
			if(!contains) {
				out.add((Form) forms.getSelectedItem());
			}
			s.populateTable();
		}
		
	}

}
