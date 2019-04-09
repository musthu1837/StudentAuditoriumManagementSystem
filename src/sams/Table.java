package sams;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class Table extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JTable table;
	public String userName;
	public int userId;	
	public Table(JTable temp,int userId,String userName,String title) {
		setTitle(title);
		this.userId = userId;
		this.userName = userName;
		setBounds(250, 100, 757, 461);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(e.getWindow(), "Are you sure want to exit from SAMS?", "Alert!", dialogButton);	
				if(dialogResult == JOptionPane.YES_OPTION){					
					e.getWindow().dispose();
				}		        
		    }
		});				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 721, 370);
		contentPanel.add(scrollPane);
		
		table = temp;
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ShowManagerFrame s = new ShowManagerFrame(userId,userName);
				s.setVisible(true);
			}
		});
		btnNewButton.setBounds(308, 380, 137, 42);
		contentPanel.add(btnNewButton);
	}
}
