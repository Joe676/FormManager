package GUI;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import DAO.Answer;
import DAO.AnswerDAO;
import DAO.ClientDAO;
import DAO.Field;
import DAO.Form;
import DAO.FormDAO;
import DAO.Service;
import DAO.ServiceDAO;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class AnswersPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel optionsPanel;
	private JComboBox<String> chooseService;
	private JComboBox<String> chooseForm;
	private JComboBox<String> chooseAnswer;
	private JCheckBox histogramChckbx;
	private JButton showBtn;
	private JPanel visualsPanel;
	private JTable answerTable;
	
	
	private ServiceDAO sd;
	private FormDAO fd;
	//private ClientDAO cd;
	private AnswerDAO ad;
	
	private Service currentService;
	private Form currentForm;
	private Field question;
	private List<Answer> currentAnswer;
	private int answerIndex;
	private DefaultTableModel answerTableModel;
	
	public AnswersPanel(ServiceDAO s, FormDAO f, ClientDAO c, AnswerDAO a) {
		sd = s;
		fd = f;
		//cd = c;
		ad = a;
		setLayout(new BorderLayout(0, 0));
		
		answerTableModel = new DefaultTableModel(new String[] {"Answer", "Date", "User ID"}, 0) {

			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

			@Override
	        public Class<?> getColumnClass(int column) {  
	                switch (column) {  
	                    case 0: return (question!=null && question.getType().equals("Integer"))?Integer.class:String.class;
	                    case 2: return Long.class;
	                    default: return   String.class;
	                }  
	        }
			
		};
		
		answerTable = new JTable(answerTableModel);
		
		optionsPanel = new JPanel();
		add(optionsPanel, BorderLayout.NORTH);
		optionsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		chooseService = new JComboBox<String>();
		chooseService.setModel(new DefaultComboBoxModel<String>(new String[] {"---Choose Service---"}));
		chooseService.addActionListener(this);
		optionsPanel.add(chooseService);
		for(Service service: sd.getAll()) {
			chooseService.addItem(service.toString());
		}
		
		chooseForm = new JComboBox<String>();
		chooseForm.setModel(new DefaultComboBoxModel<String>(new String[] {"---Choose Form---"}));
		chooseForm.addActionListener(this);
		optionsPanel.add(chooseForm);
		
		chooseAnswer = new JComboBox<String>();
		chooseAnswer.setModel(new DefaultComboBoxModel<String>(new String[] {"---Choose Question---"}));
		chooseAnswer.addActionListener(this);
		optionsPanel.add(chooseAnswer);
		
		histogramChckbx = new JCheckBox("Histogram");
		histogramChckbx.setToolTipText("Shows histogram if question's data type is Integer");
		histogramChckbx.addActionListener(this);
		optionsPanel.add(histogramChckbx);
		
		showBtn = new JButton("Show");
		optionsPanel.add(showBtn);
		showBtn.addActionListener(this);
		
		visualsPanel = new JPanel();
		add(visualsPanel, BorderLayout.CENTER);
		visualsPanel.setLayout(new BorderLayout(0, 0));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(chooseService) && chooseService.getSelectedIndex()>=0) {
			String[] s = chooseService.getSelectedItem().toString().split(";");
			if(s.length==2) {
				currentService = sd.get(Long.parseLong(s[0]));
				chooseForm.removeAllItems();
				chooseForm.addItem("---Choose Form---");
				for(Form form: currentService.getForms()) {
					chooseForm.addItem(form.toString());
				}
				chooseForm.setSelectedIndex(0);
			}
		}else if(e.getSource().equals(chooseForm) && chooseForm.getSelectedIndex()>=0) {
			if(currentService==null)return;
			if(chooseForm.getSelectedItem()==null)return;
			String[] s = chooseForm.getSelectedItem().toString().split(";");
			if(s.length==2) {
				currentForm = fd.get(Long.parseLong(s[0]));
				chooseAnswer.removeAllItems();
				chooseAnswer.addItem("---Choose Question---");
				
				for(Field field: currentForm.getFields()) {
					chooseAnswer.addItem(field.getName());
				}
				chooseAnswer.setSelectedIndex(0);
			}
		}else if(e.getSource().equals(chooseAnswer) && chooseAnswer.getSelectedIndex()>0 ) {
			answerIndex = chooseAnswer.getSelectedIndex()-1;
			question = currentForm.getFields().get(chooseAnswer.getSelectedIndex()-1);
			if(question.getType().equals("String")) {
				histogramChckbx.setSelected(false);
				histogramChckbx.setEnabled(false);
			}else {
				histogramChckbx.setSelected(false);
				histogramChckbx.setEnabled(true);
			}
		}else if(e.getSource().equals(showBtn)) {
			
			currentAnswer = ad.filter(currentService, currentForm);
			if(currentAnswer!=null && !histogramChckbx.isSelected()) {
				visualsPanel.removeAll();
				while(answerTableModel.getRowCount()>0)answerTableModel.removeRow(0);
				for(Answer a: currentAnswer) {
					answerTableModel.addRow(new Object[] {a.getAnswers().size()>answerIndex? a.getAnswers().get(answerIndex):"", a.getDate(), a.getClient().getID()});
					
				}
				visualsPanel.add(answerTable, BorderLayout.CENTER);
			}else if(currentAnswer!=null) {
				visualsPanel.removeAll();
				List<Integer> hist= new ArrayList<Integer>();
				for(Answer a: currentAnswer) {
					hist.add(Integer.parseInt(a.getAnswers().get(answerIndex)));
				}
				
				visualsPanel.add(new Histogram(hist, this), BorderLayout.CENTER);
			}else {
				visualsPanel.removeAll();
			}
			
			visualsPanel.validate();
			visualsPanel.repaint();
		}
		
	}

}
