package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

import DAO.Field;
import DAO.Form;
import DAO.FormDAO;

public class FormWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField formNameTxt;
	private JTable table;
	private JPanel controlPanel;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JButton deleteBtn;
	private JButton addQuestionBtn;
	
	DefaultTableModel tableModel = new DefaultTableModel(
			new String[] {
				"Question", "Type", "Delete"
			}, 0
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
		};
	
	private Form form;
	private FormDAO fd;
	private ManagerWindow m;
	
	public FormWindow(long id, FormDAO f, ManagerWindow managerWindow) {
		fd = f;
		if(id>0) {
			form = fd.get(id);
		}else if(id == -1) {
			form = new Form(-1, "", null);
		}
		m = managerWindow;
		setupWindow();
	}
	
	private void setupWindow() {
		setTitle("Form");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel formDataPanel = new JPanel();
		contentPane.add(formDataPanel, BorderLayout.NORTH);
		formDataPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel formIDLabel = new JLabel("Form ID:");
		formDataPanel.add(formIDLabel);
		
		JLabel formIDTxt = new JLabel(form.getID()>0?String.valueOf(form.getID()):"Will be attached at creation");
		formDataPanel.add(formIDTxt);
		
		JLabel formNameLabel = new JLabel("Form Name:");
		formDataPanel.add(formNameLabel);
		
		formNameTxt = new JTextField(form.getName());
		formDataPanel.add(formNameTxt);
		formNameTxt.setColumns(10);
		
		JPanel questionsPanel = new JPanel();
		questionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(questionsPanel, BorderLayout.CENTER);
		questionsPanel.setLayout(new BorderLayout(0, 0));
		
		addQuestionBtn = new JButton("+");
		addQuestionBtn.setToolTipText("Add question");
		addQuestionBtn.addActionListener(this);
		questionsPanel.add(addQuestionBtn, BorderLayout.SOUTH);
		
		table = new JTable();
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(25);
		table.getColumnModel().getColumn(2).setMinWidth(15);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
		table.setFillsViewportHeight(true);
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      if(column == 2) {
			    	  form.getFields().remove(row);
			    	  populateTable();
			      }
			}
			});
		
		populateTable();
		questionsPanel.add(table, BorderLayout.CENTER);
		
		controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(controlPanel, BorderLayout.SOUTH);
		setupControl();
		setVisible(true);
	}
	
	public void populateTable() {
		while(tableModel.getRowCount()>0)tableModel.removeRow(0);
		for(Field field: form.getFields()) {
			tableModel.addRow(new Object[] {field.getName(), field.getType(), "-"});
		}
	}

	private void setupControl() {
		
		if(form.getID()>0) {
			deleteBtn = new JButton("Delete");
			controlPanel.add(deleteBtn);
			deleteBtn.addActionListener(this);
		}
		
		saveBtn = new JButton("Save");
		controlPanel.add(saveBtn);
		
		cancelBtn = new JButton("Cancel");
		controlPanel.add(cancelBtn);
		
		saveBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cancelBtn)) {
			this.dispose();
		}else if(e.getSource().equals(saveBtn)) {
			form.setName(formNameTxt.getText());
			if(form.getID()==-1) {
				form.setID(fd.getNextID());
				fd.add(form);
			}else {
				fd.update(form);
			}
			m.populateFormTable();
			this.dispose();
		}else if(e.getSource().equals(deleteBtn)) {
			fd.delete(form);
			m.populateFormTable();
			this.dispose();
		}else if(e.getSource().equals(addQuestionBtn)) {
			new AddQuestionWindow(form.getFields(), this);
		}
	}
	

}
