package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.Form;
import DAO.FormDAO;
import DAO.Service;
import DAO.ServiceDAO;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ServiceWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ServiceDAO sd;
	private FormDAO fd;
	private Service service;
	private JPanel controlPanel;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JPanel formsPanel;
	private JPanel serviceDataPanel;
	private JTextField serviceNameTxt;
	private JLabel serviceNameLabel;
	private JButton addFormBtn;
	private JLabel serviceIDLabel;
	private JLabel serviceIDTxt;
	private JTable table;
	private JButton deleteBtn;
	
	private ManagerWindow m;
	
	private DefaultTableModel tableModel = new DefaultTableModel(
			new String[] {
					"ID", "Name", "Delete"
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
	
	public ServiceWindow(long id, ServiceDAO s, FormDAO f,  ManagerWindow managerWindow) {
		setTitle("Service");
		sd = s;
		fd = f;
		if(id>0)service = sd.get(id);
		else if(id==-1) {
			service = new Service(-1, "", null);
		}
		m = managerWindow;
		setupWindow();
	}
	
	private void setupWindow() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		controlPanel =new JPanel();
		contentPane.add(controlPanel, BorderLayout.SOUTH);
		setupControl();
		
		formsPanel = new JPanel();
		formsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(formsPanel, BorderLayout.CENTER);
		formsPanel.setLayout(new BorderLayout(0, 0));
		
		addFormBtn = new JButton("+");
		addFormBtn.setToolTipText("Add form");
		addFormBtn.addActionListener(this);
		formsPanel.add(addFormBtn, BorderLayout.SOUTH);
		
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(75);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
		if(service.getForms()!=null)populateTable();
		
		table.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      if(column == 2) {
			    	  service.getForms().remove(row);
			    	  populateTable();
			      }
			}
			});
		
		formsPanel.add(table, BorderLayout.CENTER);
		
		serviceDataPanel = new JPanel();
		contentPane.add(serviceDataPanel, BorderLayout.NORTH);
		serviceDataPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		serviceIDLabel = new JLabel("Service ID: ");
		serviceDataPanel.add(serviceIDLabel);
		
		serviceIDTxt = new JLabel(service.getID()>0?String.valueOf(service.getID()):"Will be attached at creation");
		serviceDataPanel.add(serviceIDTxt);
		
		serviceNameLabel = new JLabel("Service name: ");
		serviceDataPanel.add(serviceNameLabel);
		
		serviceNameTxt = new JTextField(service.getName());
		serviceDataPanel.add(serviceNameTxt);
		serviceNameTxt.setColumns(10);
		setVisible(true);
	}
	
	public void populateTable() {
		while(tableModel.getRowCount()>0)tableModel.removeRow(0);
		for(Form form: service.getForms())
			tableModel.addRow(new Object[] {form.getID(), form.getName(), "-"});
	}

	private void setupControl() {
		
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		if(service.getID()>0) {
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
			service.setName(serviceNameTxt.getText());
			if(service.getID()==-1) {
				service.setID(sd.getNextID());
				sd.add(service);
			}else {
				sd.update(service);
			}
			m.populateServiceTable();
			this.dispose();
		}else if(e.getSource().equals(deleteBtn)) {
			sd.delete(service);
			m.populateServiceTable();
			this.dispose();
		}else if(e.getSource().equals(addFormBtn)) {
			new AddFormWindow(fd, service.getForms(), this);
		}
	}
}
