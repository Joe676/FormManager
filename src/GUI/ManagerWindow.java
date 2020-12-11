package GUI;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import DAO.AnswerDAO;
import DAO.ClientDAO;
import DAO.Form;
import DAO.FormDAO;
import DAO.Service;
import DAO.ServiceDAO;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ManagerWindow extends JFrame implements ActionListener {
	private FormDAO fd = new FormDAO();
	private ServiceDAO sd = new ServiceDAO(fd);
	private ClientDAO cd = new ClientDAO();
	private AnswerDAO ad = new AnswerDAO(sd, fd, cd);
	
	public ManagerWindow() {
		setupWindow();
	}

	private static final long serialVersionUID = 1L;
	
	private JTable serviceTable;
	private JTable formTable;
	
	private DefaultTableModel serviceTableModel = new DefaultTableModel(
			new String[] {
				"ID", "Name", "More"
			}, 
			0
		) {
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
	                    case 0: return Long.class;
	                    default: return   String.class;
	                }  
	        }    
	};
	
	private DefaultTableModel formTableModel = new DefaultTableModel(
			new String[] {
					"ID", "Name", "More"
			}, 0
			) {
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
		                    case 0: return Long.class;
		                    default: return   String.class;
		                }  
		        }    
	};
	
	JButton addServiceBtn;
	JButton addFormBtn;
	
	private void setupWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Forms Manager");
		setSize(600, 400);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel sPanel = setupServices();
		JPanel fPanel = setupForms();
		JPanel answersPanel = setupAnswers();
		
		tabbedPane.addTab("Services", null, sPanel, null);
		tabbedPane.addTab("Forms", null, fPanel, null);
		tabbedPane.addTab("Answers", null, answersPanel, null);
		
		setVisible(true);
	}
	
	private JPanel setupAnswers() {
		return new AnswersPanel(sd, fd, cd, ad);
	}

	private JPanel setupServices() {
		JPanel servicesPanel = new JPanel();
		servicesPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		servicesPanel.add(scrollPane, BorderLayout.CENTER);
		
		serviceTable = new JTable();
		serviceTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		serviceTable.setModel(serviceTableModel);
		serviceTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		serviceTable.getColumnModel().getColumn(0).setMaxWidth(100);
		serviceTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		serviceTable.getColumnModel().getColumn(2).setPreferredWidth(35);
		serviceTable.getColumnModel().getColumn(2).setMinWidth(20);
		serviceTable.getColumnModel().getColumn(2).setMaxWidth(60);
		serviceTable.setRowSelectionAllowed(false);
		serviceTable.setFillsViewportHeight(true);
		serviceTable.getTableHeader().setReorderingAllowed(false);
		serviceTable.getColumn("More").setCellRenderer(new ButtonRenderer());
		serviceTable.setAutoCreateRowSorter(true); 
		
		
		
		populateServiceTable();
		serviceTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			      
			      if(column == 2) {
			    	  openServiceWindow((Long)serviceTableModel.getValueAt(row, 0));
			      }
			}
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
			});
		
		
		scrollPane.setViewportView(serviceTable);
		
		addServiceBtn = new JButton("+");
		addServiceBtn.setToolTipText("Add new service");
		addServiceBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		addServiceBtn.addActionListener(this);;
		servicesPanel.add(addServiceBtn, BorderLayout.SOUTH);
		
		return servicesPanel;
	}
	
	void populateServiceTable() {
		while(serviceTableModel.getRowCount()>0)serviceTableModel.removeRow(0);
		for(Service service: sd.getAll())
		serviceTableModel.addRow(new Object[] {service.getID(), service.getName(), "..."});
		
	}

	private JPanel setupForms() {
		JPanel formsPanel = new JPanel();
		formsPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		formsPanel.add(scrollPane, BorderLayout.CENTER);
		
		formTable = new JTable();
		formTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		formTable.setModel(formTableModel);
		formTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		formTable.getColumnModel().getColumn(0).setMaxWidth(100);
		formTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		formTable.getColumnModel().getColumn(2).setPreferredWidth(35);
		formTable.getColumnModel().getColumn(2).setMinWidth(20);
		formTable.getColumnModel().getColumn(2).setMaxWidth(60);
		formTable.setFillsViewportHeight(true);
		formTable.getTableHeader().setReorderingAllowed(false);
		formTable.getColumn("More").setCellRenderer(new ButtonRenderer());
		formTable.setAutoCreateRowSorter(true);
		
		formTable.addMouseListener(new MouseListener() {
			
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
			    	  openFormWindow((Long)formTableModel.getValueAt(row, 0));
			      }
			}
			});
		scrollPane.setViewportView(formTable);
		
		populateFormTable();
		
		addFormBtn = new JButton("+");
		addFormBtn.setToolTipText("Add new form");
		addFormBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		addFormBtn.addActionListener(this);
		formsPanel.add(addFormBtn, BorderLayout.SOUTH);
		
		return formsPanel;
	}
	
	public void populateFormTable() {
		while(formTableModel.getRowCount()>0)formTableModel.removeRow(0);
		for(Form form: fd.getAll())
			formTableModel.addRow(new Object[] {form.getID(), form.getName(), "..."});
	}

	public void openServiceWindow(long id) {
			/*ServiceWindow window = */new ServiceWindow(id, sd, fd, this);
	}

	public void openFormWindow(long id) {
			/*FormWindow window = */new FormWindow(id, fd, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addServiceBtn)) {
			openServiceWindow(-1);
		}else if(e.getSource().equals(addFormBtn)){
			openFormWindow(-1);
		}else {
			System.out.println(e);
		}
	}
}
