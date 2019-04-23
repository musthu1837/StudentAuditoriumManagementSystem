package sams;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Start extends JFrame {
	private static final long serialVersionUID = 1L;
	private DataBase db = new DataBase();	
	private JLabel bgLabel;
	private JTextPane title;
	private JButton btnLogin,seatMapButton;
	private JScrollPane scrollPane;
	int counter = 0 ;
	private JTable showsTable;
	private JTextArea seatAvDisplay;
	BufferedImage img1=null;
	ArrayList<String> events=new ArrayList<String>();
	ArrayList<String> date=new ArrayList<String>();
	Vector<String> tableHeader;
	public JFrame frame;
	boolean flag=false;
	int showId=0;
	@SuppressWarnings("deprecation")
	public Start() {
		//home window settings			
		db.connect();				
		this.frame = this;
		this.setTitle("Home");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(bgLabel, "Are you sure want to exit from SAMS?", "Alert!", dialogButton);	
				if(dialogResult == JOptionPane.YES_OPTION){
					
					e.getWindow().dispose();
				}		        
		    }
		});
		this.setBounds(250,100,805, 582);
		this.setLayout(null);
		this.setResizable(false);
		
		// adding title to home window
		title = new JTextPane();
		title.setForeground(Color.WHITE);
		title.setEditable(false);
		title.setFont(new Font("Georgia", Font.BOLD, 34));
		title.setText("Student Auditorium Management System (SAMS	)");
		title.setBounds(10, 11, 573, 99);
		title.setOpaque(false);
		this.getContentPane().add(title);	
		
		//adding login button
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBounds(580, 20, 202, 48);
		btnLogin.disable();
		this.getContentPane().add(btnLogin);
		
		// adding action to login button
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = {"Show Manager",
	                    "Sales Person",
	                    "Accountant"};	
				//n=0 for showmanager,n=1 for sales person ,n=2 for accountant
				int n = JOptionPane.showOptionDialog(
						frame,      
						"Select Login Account Type : ",
						"Login",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[0]
				);	
				// Login for ShowManager
				if(n==0) {
					try {
						db.createUserTabel();
						db.createSeatTypeTabel();
						db.insertIntoSeatType(0, "Normal");
						db.insertIntoSeatType(1, "Balcony");
					    if(db.isShowManagerExits()) {
					    	JTextField xField = new JTextField(10);
						    JPasswordField yField = new JPasswordField(10);						    
						    JPanel myPanel = new JPanel();
						    myPanel.add(new JLabel("User Name"));
						    myPanel.add(xField);
						    myPanel.add(new JLabel("Password"));
						    myPanel.add(yField);
						    int result = JOptionPane.showConfirmDialog(frame, 
						    				myPanel, 
						    				"Login Details", 
						    				JOptionPane.OK_CANCEL_OPTION);
						    if (result == JOptionPane.OK_OPTION) {
						    	String name = db.userLogin(xField.getText(), yField.getText(),0);
						    	if(name != null) {			
						    		JOptionPane.showMessageDialog(frame,"Login Successful !");
						    		int index = name.indexOf("$");
						    		String userName =name.substring(index+1, name.length());
				    				int id = Integer.parseInt(name.substring(0, index));
							    					    				
						    		ShowManagerFrame f = new ShowManagerFrame(id,userName);
									f.setVisible(true);
									frame.dispose();						    		
						    	}
						    	else {
						    		JOptionPane.showMessageDialog(frame,"Login Unsuccessful Try Again!");
						    	}
						    }					    	
					    }
					    else {
					    	
					    	JOptionPane.showMessageDialog(frame, "No ShowManager Account exists, Create ShowManager account first !");
							ShowManagerDetails f = new ShowManagerDetails(0);
							f.setVisible(true);
							frame.dispose();
					    }
					}
					catch(Exception e1) {
						e1.printStackTrace();						
					}
				}
				else if(n==1) {
					try {
						db.createUserTabel();
				    	JTextField xField = new JTextField(10);
					    JPasswordField yField = new JPasswordField(10);						    
					    JPanel myPanel = new JPanel();
					    myPanel.add(new JLabel("User Name"));
					    myPanel.add(xField);
					    myPanel.add(new JLabel("Password"));
					    myPanel.add(yField);
					    int result = JOptionPane.showConfirmDialog(frame, 
					    				myPanel, 
					    				"Login Details", 
					    				JOptionPane.OK_CANCEL_OPTION);
					    if (result == JOptionPane.OK_OPTION) {
					    	String name = db.userLogin(xField.getText(), yField.getText(),1);
					    	if(name != null) {			
					    		JOptionPane.showMessageDialog(frame,"Login Successful !");
					    		int index = name.indexOf("$");
					    		String userName =name.substring(index+1, name.length());
					    		int id = Integer.parseInt(name.substring(0, index));
					    				
					    		SalesPersonFrame f = new SalesPersonFrame(id,userName);
								f.setVisible(true);
								frame.dispose();						    		
					    	}
					    	else {
					    		JOptionPane.showMessageDialog(frame,"Login Unsuccessful Try Again!");
					    	}
					    }					    	
					}
					catch(Exception e1) {
						e1.printStackTrace();						
					}					
				}
				else {
					try {
						db.createUserTabel();
				    	JTextField xField = new JTextField(10);
					    JPasswordField yField = new JPasswordField(10);						    
					    JPanel myPanel = new JPanel();
					    myPanel.add(new JLabel("User Name"));
					    myPanel.add(xField);
					    myPanel.add(new JLabel("Password"));
					    myPanel.add(yField);
					    int result = JOptionPane.showConfirmDialog(frame, 
					    				myPanel, 
					    				"Login Details", 
					    				JOptionPane.OK_CANCEL_OPTION);
					    if (result == JOptionPane.OK_OPTION) {
					    	String name = db.userLogin(xField.getText(), yField.getText(),2);
					    	if(name != null) {			
					    		JOptionPane.showMessageDialog(frame,"Login Successful !");
					    		int index = name.indexOf("$");
					    		String userName =name.substring(index+1, name.length());
					    		int id = Integer.parseInt(name.substring(0, index));
					    				
					    		AccountantFrame f = new AccountantFrame(id,userName);
								f.setVisible(true);
								frame.dispose();						    		
					    	}
					    	else {
					    		JOptionPane.showMessageDialog(frame,"Login Unsuccessful Try Again!");
					    	}
					    }					    	
					}
					catch(Exception e1) {
						e1.printStackTrace();						
					}					
				}
			}
		});
		//background image settings
		bgLabel = new JLabel("New label");
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
		//adding shows to table
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
					String cellValue = showsTable.getValueAt(i-1, 1)+"";				
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
				      seatAvDisplay.setText("   Available Seats :                           Seat Prices :\n\nNormal Seats : "+avnseats+"                  Normal Seat Price : "+nprice
				    		  +"\n\nBalcony Seats : "+avbseats+"                  Balcony Seat Price : "+bprice);
				}
	        }
	    });		
		
		seatMapButton = new JButton("Show seat map");
		seatMapButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		seatMapButton.setForeground(Color.BLACK);
		seatMapButton.setBounds(500, 400, 280, 60);
		seatMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(showId==0) {
					JOptionPane.showMessageDialog(frame, "No event selected !");
					return;
				}				
				int row = showsTable.getSelectedRow();
				String cellValue = showsTable.getValueAt(row, 1)+"";				
				String showTime = cellValue.substring(7, 26);
				if(isShowCompleted(showTime)) {
					JOptionPane.showMessageDialog(frame, "Sorry Event completed!!!");
					return;					
				}
				frame.dispose();
				SeatAvailability s = new SeatAvailability(showId,0,0,null);
				s.setVisible(true);			
			}
			
		});
		this.getContentPane().add(seatMapButton);		
		this.getContentPane().add(bgLabel); 
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
	public static void main(String[] args) {
		Start window = new Start();
		window.setVisible(true);

	}

}
