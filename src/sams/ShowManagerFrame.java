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
import java.sql.ResultSetMetaData;
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
import javax.swing.JDialog;
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

public class ShowManagerFrame extends JFrame {
	private static DataBase db = new DataBase();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane,panel1,panel2;
	private JTextPane title;
	private BufferedImage img1=null;
	private JButton logoutButton,addEmployeeButton,editProfileButton,addShowsButton;
	private JButton fireButton,salesPerEmployee;
	private JButton showEventSales,showEventStatus,showSeatMap,showEventExpenditures,showYearBalanceSheet;
	private JLabel bgLabel;
	private String showManagerName;
	private int id;
	private ShowManagerFrame sframe;
	int counter = 0;
	ArrayList<String> events=new ArrayList<String>();
	ArrayList<String> date=new ArrayList<String>();
	Vector<String> tableHeader;
	private JScrollPane scrollPane;
	private JTable showsTable;
	private JTextArea seatAvDisplay;
	boolean flag=false;
	int showId=0;
	//method used for displaying database data in the form of tables	
	/*public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {
	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
	    };

	}*/
	public static DefaultTableModel buildTableModel(ResultSet rs,int flag)
	        throws SQLException {
	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    if(flag==1)	columnNames.add("TotalAmount");
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	    	
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        if(flag==1) vector.add(db.totalSalesofShow(rs.getInt(1)));
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames){
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
	    };

	}
	
	public ShowManagerFrame(int id,String _showManagerName) {
		//home window settings
		sframe = this;
		showManagerName = _showManagerName;
		id = this.id;
		setTitle("ShowManager");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		
		//creating edit profile button
		editProfileButton = new JButton("Profile");
		editProfileButton.setBounds(550, 15, 100, 31);
		editProfileButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		editProfileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				ShowManagerDetails frame = new ShowManagerDetails(1);
				frame.setVisible(true);
				dispose();				
			}		
			
		});					
		contentPane.add(editProfileButton);

		
		// adding title to manager window
		title = new JTextPane();
		title.setForeground(Color.WHITE);
		title.setEditable(false);
		title.setFont(new Font("Georgia", Font.BOLD, 34));
		title.setText(" Welcome "+showManagerName+"!");
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.ORANGE));
		title.setBounds(10, 11, 780, 50);
		title.setOpaque(false);
		getContentPane().add(title);				

		// container for all buttons
		panel1 = new JPanel();
		panel1.setBounds(10,70,800,50);
		panel1.setBackground(new Color(240, 128, 128,0));
		panel1.setLayout(null);
		
		// adding addEmployee button 
		addEmployeeButton = new JButton("Add Employees");
		addEmployeeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		addEmployeeButton.setBounds(10, 10, 160, 40);
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = { "Sales Person","Accountant" };
				 String selected = (String) JOptionPane.showInputDialog(panel1, 
					        "Type of Employee :",
					        "Employee Type",
					        JOptionPane.QUESTION_MESSAGE, 
					        null, 
					        options ,
					        options[0]);
				 if(selected == null) return;
				 if(selected.equals("Sales Person")){
					 SalesPersonAdd frame = new SalesPersonAdd(sframe.id,showManagerName);
					 frame.setVisible(true);
					 dispose();
				 }
				 if(selected.equals("Accountant")){
					 AccountantAdd frame = new AccountantAdd(sframe.id,showManagerName);
					 frame.setVisible(true);
					 dispose();					 
				 }
			}
		});


		//creating edit add shows button
		addShowsButton = new JButton("Add Shows");
		addShowsButton.setBounds(220, 10, 160, 40);
		addShowsButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		addShowsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				db.createShowsTable();
				AddShow frame = new AddShow(showManagerName,sframe.id,new ArrayList<Integer>(),new ArrayList<Integer>());
				frame.setVisible(true);				
				dispose();
			}		
			
		});		
		
		//creating transactions button
		fireButton = new JButton("Fire Employee");
		fireButton.setBounds(420, 10, 150, 40);
		fireButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		fireButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!db.isEmployeeExits()) {
					JOptionPane.showMessageDialog(contentPane, "No Employee Account created !");
					return;
				}
				JComboBox<Object> sps =  new JComboBox<Object>(db.getEmployees());				
			      JPanel myPanel = new JPanel();
			      myPanel.setLayout(new GridLayout(1,2));
			      myPanel.add(new JLabel("Id-Name"));
			      myPanel.add(sps);
			      
			      int result = JOptionPane.showConfirmDialog(sframe, myPanel, 
			               "Delete Employee", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  try {
			    		  String sp= sps.getItemAt(sps.getSelectedIndex())+"";
				    	  int id = Integer.parseInt(sp.split("-")[0]);				    	  
				    	  boolean alg = db.fireEmployee(id);
				    	  if(alg) {
				    		  JOptionPane.showMessageDialog(contentPane, "Employee Deleted successfully");
				    	  }
			    	  }
			    	  catch(Exception e1) {
			    		  e1.printStackTrace();
			    		  return;
			    	  }
			      }				
			}		
			
				
		});		
		
		//creating salesPerEmployee button
		salesPerEmployee = new JButton("Sales/Employee");
		salesPerEmployee.setBounds(600, 10, 170, 40);
		salesPerEmployee.setFont(new Font("Tahoma", Font.BOLD, 16));
		salesPerEmployee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!db.isSalesPersonExit()) {
					JOptionPane.showMessageDialog(contentPane,"No Sales Person Found !");
					return;					
				}
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
				db.createTransactionTable();
				ResultSet rs = db.getSalesPerEmployee(i);					
			      Table dialog;
				try {
					dialog = new Table(new JTable(buildTableModel(rs,0)),sframe.id,sframe.showManagerName,"Sales Per Employee");
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					sframe.dispose();
					dialog.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}		
			
		});			
		panel1.add(addEmployeeButton);
		panel1.add(addShowsButton);
		panel1.add(fireButton);
		panel1.add(salesPerEmployee);
		contentPane.add(panel1);
		
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
		scrollPane.setBounds(20, 130, 760, 250);
		this.getContentPane().add(scrollPane);
		
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
				}
	        }
	    });		
		// container for all buttons
		panel2 = new JPanel();
		panel2.setBounds(10,390,800,50);
		panel2.setBackground(new Color(240, 128, 128,0));
		panel2.setLayout(null);
		
		showEventSales = new JButton("Show Event Sales");
		showEventSales.setBounds(50, 10, 200, 40);
		showEventSales.setFont(new Font("Tahoma", Font.BOLD, 16));
		showEventSales.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				ResultSet rs = db.getShowSales(i);					
			      Table dialog;
				try {
					sframe.dispose();					
					dialog = new Table(new JTable(buildTableModel(rs,0)),sframe.id,sframe.showManagerName,"Event Sales");
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);				
					dialog.setVisible(true);	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
			
		});	
		//creating edit add shows button
		showEventStatus = new JButton("Show Event Status");
		showEventStatus.setBounds(295, 10, 200, 40);
		showEventStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		showEventStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				int row = showsTable.getSelectedRow();
				String cellValue = showsTable.getValueAt(row, 1)+"";				
				String showTime = cellValue.substring(7, 26);
				if(isShowCompleted(showTime)) {
					JOptionPane.showMessageDialog(sframe, "Sorry Event completed!!!");
					return;					
				}				
				db.createExpenditureTable();
				ResultSet rs = db.getShowStatus(i);
				double sales = db.totalSalesofShow(i);
				double expenditureamount = db.getTotalExpenditure(i);				
				int avnseats=0;
				int avbseats=0;
				int VIPnseats=0;
				int VIPbseats=0;
				try {
					if(rs.next()) {
						avnseats = rs.getInt(1);
						avbseats = rs.getInt(2);
						VIPnseats = rs.getInt(3);
						VIPbseats = rs.getInt(4);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int nbooked = 300-avnseats-VIPnseats;
				int bbooked = 200-avbseats-VIPbseats;
				float percentageofbooking = ((nbooked+bbooked)/500f)*100f;				
			      JOptionPane.showMessageDialog(contentPane, "Available Normal Seats : "+avnseats+
			    		  "\nAvailable Balcony Seats : "+avbseats+
			    		  "\n\nVIP Normal Seats : "+VIPnseats+
			    		  "\nVIP Balcony Seats : "+VIPbseats+			    		  
			    		  "\n\nBooked Normal Seats : "+nbooked+
			    		  "\nBooked Balcony Seats : "+bbooked+
			    		  "\n\nPercentage of seats booked : "+percentageofbooking+
			    		  "\n\nTotal Expenditure amount : "+expenditureamount+
			    		  "\nTotal Sales of Event : "+sales);				
				
			}		
			
		});			
		
		showSeatMap = new JButton("Show Seat Map");
		showSeatMap.setBounds(530, 10, 200, 40);
		showSeatMap.setFont(new Font("Tahoma", Font.BOLD, 16));
		showSeatMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				int row = showsTable.getSelectedRow();
				String cellValue = showsTable.getValueAt(row, 1)+"";				
				String showTime = cellValue.substring(7, 26);
				if(isShowCompleted(showTime)) {
					JOptionPane.showMessageDialog(sframe, "Sorry Event completed!!!");
					return;					
				}				
				dispose();
				SeatAvailability s = new SeatAvailability(i,1,sframe.id,showManagerName);
				s.setVisible(true);
			}		
			
		});		
		panel2.add(showEventSales);
		panel2.add(showEventStatus);
		panel2.add(showSeatMap);
		contentPane.add(panel2);
		showYearBalanceSheet = new JButton("Show Yearly Balance Sheet");
		showYearBalanceSheet.setBounds(530, 10, 200, 40);
		showYearBalanceSheet.setFont(new Font("Tahoma", Font.BOLD, 16));
		showYearBalanceSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JTextField xField = new JTextField(10);

			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("Year "));
			      myPanel.add(xField);

			      int result = JOptionPane.showConfirmDialog(sframe, myPanel, 
			               "Enter year?", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  try {
				    	  int year = Integer.parseInt(xField.getText());
							db.createShowsTable();
							ResultSet rs = db.getShowsInYear(year);
						      Table dialog;
							try {
								sframe.dispose();
								dialog = new Table(new JTable(buildTableModel(rs,1)),sframe.id,sframe.showManagerName,year+" Balance Sheet");
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);					
								dialog.setVisible(true);					
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}				    	  
			    	  }
			    	  catch(Exception e1) {
			    		  JOptionPane.showMessageDialog(contentPane, "1)Should be number\n2)Should not be empty","Invalid Year",JOptionPane.ERROR_MESSAGE);
			    		  return;
			    	  }
			      }					
				
			}		
			
		});
		showYearBalanceSheet.setBounds(100, 470, 270, 40);
		
		showEventExpenditures = new JButton("Show Event Expenditures");
		showEventExpenditures.setBounds(530, 10, 200, 40);
		showEventExpenditures.setFont(new Font("Tahoma", Font.BOLD, 16));
		showEventExpenditures.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				ResultSet rs = db.getExpenditure(i);
			      Table dialog;
				try {
					sframe.dispose();
					dialog = new Table(new JTable(buildTableModel(rs,0)),sframe.id,sframe.showManagerName,"Show Expenditure");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);					
					dialog.setVisible(true);					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			
			}		
			
		});
		showEventExpenditures.setBounds(400, 470, 290, 40);
		getContentPane().add(showEventExpenditures);		
		getContentPane().add(showYearBalanceSheet);
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
		new ShowManagerFrame(1,"Musthafa").setVisible(true);
	}
}
