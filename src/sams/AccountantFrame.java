package sams;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class AccountantFrame extends JFrame {
	private DataBase db = new DataBase();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane title;
	private BufferedImage img1=null;
	private JButton logoutButton,expenditureButton,profileButton,rmExpenditureButton;
	private JLabel bgLabel;
	private String accountantName;
	private int id;
	int counter = 0;
	ArrayList<String> events=new ArrayList<String>();
	ArrayList<String> date=new ArrayList<String>();
	Vector<String> tableHeader;
	private JScrollPane scrollPane;
	private JTable showsTable;
	private JTextArea seatAvDisplay;
	boolean flag=false;
	int showId=0;
	public AccountantFrame frame;
	public AccountantFrame(int id,String _accountantName) {
		//home window settings
		this.id = id;
		accountantName = _accountantName;
		this.frame = this;
		setTitle("Accountant");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(e.getWindow(), "Are you sure want to exit from SAMS?", "Alert!", dialogButton);	
				if(dialogResult == JOptionPane.YES_OPTION){
					db.disconnect();
					e.getWindow().dispose();
				}		        
		    }
		});			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		setBounds(250, 100, 805, 596);
		setLayout(null);
		setResizable(false);
		
		//background image settings
		bgLabel = new JLabel("");
		bgLabel.setBounds(0, 0, 805, 582);
			
		//adding background image to bgLabel
		try {
			img1=ImageIO.read(new File("download.jpg"));
			Image dimg = img1.getScaledInstance(bgLabel.getWidth(), bgLabel.getHeight(), Image.SCALE_SMOOTH);
    		ImageIcon imageIcon = new ImageIcon(dimg);
    		bgLabel.setIcon(imageIcon);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//adding logout button
		logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		logoutButton.setForeground(Color.BLACK);
		logoutButton.setBounds(660, 15, 100, 31);
		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(bgLabel, "Are you sure want to exit?", "Alert!", dialogButton);	
				if(dialogResult == JOptionPane.YES_OPTION){
					Start.main(null);
					dispose(); 
				}
			}
			
		});
		getContentPane().add(logoutButton);		
		
		//adding profile button
		profileButton = new JButton("Profile");
		profileButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		profileButton.setForeground(Color.BLACK);
		profileButton.setBounds(550, 15, 100, 31);
		profileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AccountantDetails s = new AccountantDetails(id,accountantName);
				s.setVisible(true);
				dispose();
				
			}
			
		});
		getContentPane().add(profileButton);		
		
		// adding title to manager window
		title = new JTextPane();
		title.setForeground(Color.WHITE);
		title.setEditable(false);
		title.setFont(new Font("Georgia", Font.BOLD, 34));
		title.setText(" Welcome "+accountantName+"!");
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.ORANGE));
		title.setBounds(10, 11, 780, 50);
		title.setOpaque(false);
		getContentPane().add(title);				
		
		//adding shows table
		db.connect();		
		if(db.isShowTableExits()) {
			ResultSet rs = db.getShows();
		    try {
				if(!rs.next()) { 
					  events.add("No events Created, create events first !");
					  flag=true;
				}
				else {
			    	  String temp1=rs.getString(1);			    	  
			    	  String temp2=rs.getString(2);			    	  
			    	  temp2 = temp2.substring(0,temp2.length()-2);
			    	  String temp3 =rs.getString(3);
			    	  temp3 = temp3.substring(0,temp3.length()-2);
			    	  events.add(temp1);
			    	  date.add("Start: "+temp2+" | End: "+temp3);
			      while(rs.next()){
			    	  temp1=rs.getString(1);			    	  
			    	  temp2=rs.getString(2);			    	  
			    	  temp2 = temp2.substring(0,temp2.length()-2);
			    	  temp3 =rs.getString(3);
			    	  temp3 = temp3.substring(0,temp3.length()-2);
			    	  events.add(temp1);
			    	  date.add("Start: "+temp2+" | End: "+temp3);
			      }			      
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
	    	   events.add("No events Created, create events first !");
	    	   flag=true;
		}
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(20, 121, 750, 250);
		this.getContentPane().add(scrollPane);
		
		seatAvDisplay = new JTextArea();
		seatAvDisplay.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 17));
		seatAvDisplay.setForeground(Color.WHITE);
		seatAvDisplay.setOpaque(false);
		seatAvDisplay.setEditable(false);
		seatAvDisplay.setText("No Show Selected !");
		seatAvDisplay.setBounds(40, 400, 450, 362);
		seatAvDisplay.setBorder(null);
		this.getContentPane().add(seatAvDisplay);
		
		tableHeader = new Vector<String>();
		tableHeader.add("Show Name");
		tableHeader.add("Show Timmings");
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for(int i=0;i<events.size();i++){
	        Vector<Object> vector = new Vector<Object>();
			if(flag) {
				vector.add("No Events Exists !");
				vector.add("Create Events first !");
				 data.add(vector);
				 break;
			}
	        vector.add(events.get(i));
	        vector.add(date.get(i));
	        data.add(vector);
		}		
		DefaultTableModel listModel = new DefaultTableModel(data,tableHeader){    
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int tableHeader) {
		       return false;
		    }};		
		showsTable = new JTable(listModel);
		showsTable.setFont(new Font("Tahoma", Font.BOLD, 13));
		showsTable.setForeground(Color.BLACK);
		showsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(showsTable);
		
		showsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if(flag) return;
				int i=showsTable.getSelectedRow();
				if (i==-1) {
					seatAvDisplay.setText("No Shows Selected !");
				}
				else{
					i++;
					showId =i;					
					int row = showsTable.getSelectedRow();
					String cellValue = showsTable.getValueAt(row, 1)+"";				
					String showTime = cellValue.substring(7, 26);
					if(isShowCompleted(showTime)) {
						JOptionPane.showMessageDialog(frame, "Sorry Event completed!!!");						
					}					
					ResultSet rs = db.getShowDetails(showId);
				      int nprice = 0;
			    	  int bprice = 0;
			    	  int avnseats = 0;
			    	  int avbseats = 0;
				      try {
						while(rs.next()){
							  nprice=rs.getInt("nprice");
							  bprice=rs.getInt("bprice");
							  avnseats=rs.getInt("avnseats");
							  avbseats=rs.getInt("avbseats");
						  }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    /*  seatAvDisplay.setText("Available Seats :                  Seat Prices :\n\nNormal Seats : "+avnseats+"\nBalcony Seats : "+avbseats
				      +"\n\n\n\nSeat Prices :\n\nNormal Seat Price : "+nprice+"\nBalcony Seat Price : "+bprice);*/
				      seatAvDisplay.setText("   Available Seats :                           Seat Prices :\n\nNormal Seats : "+avnseats+"                  Normal Seat Price : "+nprice
				    		  +"\n\nBalcony Seats : "+avbseats+"                Balcony Seat Price : "+bprice);
				}
	        }
	    });		
				
		
		expenditureButton = new JButton("Add Expenditure");
		expenditureButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		expenditureButton.setForeground(Color.BLACK);
		expenditureButton.setBounds(500, 400, 280, 60);
		expenditureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(flag) {
					JOptionPane.showMessageDialog(contentPane,"No Event Selected !");
					return;
				}
				int i=showsTable.getSelectedRow();
				i++;
				if (i==0 ) {
					JOptionPane.showMessageDialog(contentPane, "Select an event first !");
					return;
				}
				db.createExpenditureTable();
				JTextField xField = new JTextField(10);
				JTextField xField1 = new JTextField(10);				
				xField1.setBounds(188, 132, 57, 27);

			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("Type"));
			      myPanel.add(xField);
			      myPanel.add(new JLabel("Amount"));
			      myPanel.add(xField1);

			      int result = JOptionPane.showConfirmDialog(contentPane, myPanel, 
			               "Expenditure Details", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION ) {
			    	  String ftype = xField.getText().trim();
			    	  String famount = xField1.getText();
			    	  Double amount = 0d;
						try {
							 amount = Double.parseDouble(famount);						
	
							try {
								if(amount<=0d) {
									throw new Exception();
								}
							}
							catch(Exception e) {		
								JOptionPane.showMessageDialog(myPanel, "1)Price cannot be negative and zero\n2)Should not be empty\n3)Must be number","Invalid price",JOptionPane.ERROR_MESSAGE);							
								return;							
							}
						}			    	 
						catch(Exception e) {						
							JOptionPane.showMessageDialog(myPanel, "1)Price cannot be negative and zero\n2)Should not be empty\n3)Must be number","Invalid price",JOptionPane.ERROR_MESSAGE);							
							return;							
						}									
						String SHOWNAME_REGEX = "^[a-zA-Z\\s]*$";
						Boolean b = ftype.matches(SHOWNAME_REGEX);				 
						if(!b || ftype.length()==0) {
							JOptionPane.showMessageDialog(myPanel, "1)Upto 20 characters includes only spaces and alphabets\n2)Should not be empty","Invalid Type",JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(ftype.length()>20) {
							JOptionPane.showMessageDialog(contentPane, "1)Upto 20 characters includes only spaces and alphabets\n2)Should not be empty","Invalid Type",JOptionPane.ERROR_MESSAGE);
							return;
						}
						db.insertIntoExpenditure(frame.id, i, ftype, amount);
						JOptionPane.showMessageDialog(contentPane, "Expenditure added successfully");
			      }				
			}
			
		});		
		contentPane.add(expenditureButton);

		rmExpenditureButton = new JButton("Remove Expenditure");
		rmExpenditureButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		rmExpenditureButton.setForeground(Color.BLACK);
		rmExpenditureButton.setBounds(500, 480, 280, 60);
		rmExpenditureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(flag) {
					JOptionPane.showMessageDialog(contentPane,"No Event Selected !");
					return;
				}
				int i=showsTable.getSelectedRow();
				i++;
				if (i==0 ) {
					JOptionPane.showMessageDialog(contentPane, "Select an event first !");
					return;
				}
				db.createExpenditureTable();
				JComboBox<Object> sps =  new JComboBox<Object>(db.getExpendituresRemove(i));				
			      JPanel myPanel = new JPanel();
			      myPanel.setLayout(new GridLayout(1,2));
			      myPanel.add(new JLabel("Id-Type"));
			      myPanel.add(sps);
			      
			      int result = JOptionPane.showConfirmDialog(frame, myPanel, 
			               "Delete Expenditures", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  try {
			    		  String sp= sps.getItemAt(sps.getSelectedIndex())+"";
				    	  int id = Integer.parseInt(sp.split("-")[0]);				    	  
				    	  boolean alg = db.removeExpenditure(id);
				    	  if(alg) {
				    		  JOptionPane.showMessageDialog(contentPane, "Expenditure deleted successfully");
				    	  }
			    	  }
			    	  catch(Exception e1) {
			    		  e1.printStackTrace();
			    		  return;
			    	  }
			      }				
				
			}
			
		});		
		contentPane.add(rmExpenditureButton);		
		getContentPane().add(bgLabel);
				
	}
	public boolean isShowCompleted(String showTime) {
		Date d = new Date(System.currentTimeMillis());
	    String formatString = "yyyy-MM-dd HH:mm:ss";
	    String current=null;
	    try {
	        SimpleDateFormat format = new SimpleDateFormat(formatString);
	        format.setLenient(false);
	        current = format.format(d);
	    } catch (IllegalArgumentException e) {
	    	e.printStackTrace();
	    }	
	    try {
	        SimpleDateFormat format = new SimpleDateFormat(formatString);
	        format.setLenient(false);
	        java.util.Date showDate =format.parse(showTime);
	        java.util.Date todayDate =format.parse(current);
	        return showDate.before(todayDate);
	    } catch (IllegalArgumentException e) {
	    	e.printStackTrace();
	    	return false;
	    } catch (ParseException e) {
			// TODO Auto-generated catch block    	
			e.printStackTrace();
			return false;
		}    
	}	
	
	public static void main(String args[]) {
		new AccountantFrame(3,"Sajid").setVisible(true);
	}
}
