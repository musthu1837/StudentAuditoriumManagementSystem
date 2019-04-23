package sams;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddShow extends JFrame {
	private static final long serialVersionUID = 1L;	
	public BookVIPTicket book;
	private AddShow frame;
	private String showManager;
	private int id;
	private JPanel contentPane;
	private JLabel header,sqlStartTimeLabel,sqlEndTimeLabel,showNameLabel,dateLabel;
	private JLabel vipSeatsLabel,balconySeatsLabel,normalSeatsLabel,priceLabel,pnormalSeatsLabel,pbalconySeatsLabel;
	public JTextField showNameField,balconySeatsField,pnormalSeatsField,pbalconySeatsField,normalSeatsField;
	private JSeparator separator,separator_1,separator_2;
	private int nseats,avn;
	private int bseats,avb;
	private JButton cancelButton,addButton,browseSeatsButton;
	private DataBase db = new DataBase();
	private String showName;
	private int vipNormalSeats,vipBalconySeats;
	private double balconyPrice,normalPrice;
	private String sqlStartTime,sqlEndTime;
	public AddShow(String managerName,int id,ArrayList<Integer> nseatsArray,ArrayList<Integer> bseatsArray) {
		this.id = id;
		this.showManager = managerName;
		frame = this;
		setResizable(false);
		setTitle("Add Show");
		db.connect();
		ResultSet rs = db.getSeatsOfAuditorium();
		try {
			nseats = rs.getInt(1);
			bseats = rs.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
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
		setBounds(100, 100, 649, 491);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		header = new JLabel("Show Details");
		header.setFont(new Font("Tahoma", Font.BOLD, 20));
		header.setBounds(240, 11, 200, 34);
		getContentPane().add(header);
		
		showNameLabel = new JLabel("Show Name");
		showNameLabel.setBounds(10, 76, 103, 14);
		contentPane.add(showNameLabel);
		
		showNameField = new JTextField();
		showNameField.setBounds(104, 70, 449, 27);
		contentPane.add(showNameField);
		showNameField.setColumns(10);		

		dateLabel = new JLabel("Show Timings:");
		dateLabel.setBounds(10, 115, 103, 14);
		contentPane.add(dateLabel);		
		
        separator_2 = new JSeparator();
        separator_2.setBounds(10, 136, 613, 2);
        contentPane.add(separator_2);

        vipSeatsLabel = new JLabel("V.I.P Seats");
        vipSeatsLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        vipSeatsLabel.setBounds(10, 234, 103, 20);
        contentPane.add(vipSeatsLabel);
        
        separator = new JSeparator();
        separator.setBounds(10, 257, 613, 2);
        contentPane.add(separator);
        
        priceLabel = new JLabel("Ticket Price");
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        priceLabel.setBounds(10, 310, 103, 20);
        contentPane.add(priceLabel);
               
		sqlStartTimeLabel = new JLabel("Start Time:");		
		sqlStartTimeLabel.setBounds(10,156,100,20);
		contentPane.add(sqlStartTimeLabel);
		
		sqlEndTimeLabel = new JLabel("End Time: ");		
		sqlEndTimeLabel.setBounds(10,195,100,20);
		contentPane.add(sqlEndTimeLabel);  
		
        TimeField t = new TimeField();
		t.setBounds(100, 130, 570, 55);
		contentPane.add(t);
		
        TimeField t1 = new TimeField();
		t1.setBounds(100, 170, 570, 55);
		contentPane.add(t1);		
		
        separator_1 = new JSeparator();
        separator_1.setBounds(10, 335, 613, 2);
        contentPane.add(separator_1);
                
        balconySeatsLabel = new JLabel("Balcony:");
        balconySeatsLabel.setBounds(295, 273, 70, 14);
        contentPane.add(balconySeatsLabel);
        
        balconySeatsField = new JTextField();
        balconySeatsField.setBounds(350, 270, 150, 27);
        balconySeatsField.setEditable(false);
        balconySeatsField.setText(bseatsArray.size()+"");
        contentPane.add(balconySeatsField);
        
        browseSeatsButton = new JButton("Browse");
        browseSeatsButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        browseSeatsButton.setBounds(525,270,70,27);
        contentPane.add(browseSeatsButton);
        browseSeatsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				book = new BookVIPTicket(frame);
				book.setVisible(true);
				
			}
        	
        });
        
        normalSeatsLabel = new JLabel("Normal:");
        normalSeatsLabel.setBounds(10, 273, 93, 14);
        contentPane.add(normalSeatsLabel);
        
        normalSeatsField = new JTextField();
        normalSeatsField.setBounds(60, 270, 150, 27);
        normalSeatsField.setText(nseatsArray.size()+"");
        normalSeatsField.setEditable(false);
        contentPane.add(normalSeatsField);
        
  /*      selectNormalSeats = new JButton("Browse");
        selectNormalSeats.setFont(new Font("Tahoma", Font.PLAIN, 10));
        selectNormalSeats.setBounds(215,270,70,27);
        contentPane.add(selectNormalSeats);*/
        
        pnormalSeatsLabel = new JLabel("Normal Seats");
        pnormalSeatsLabel.setBounds(10, 351, 93, 14);
        contentPane.add(pnormalSeatsLabel);
        
        pnormalSeatsField = new JTextField();
        pnormalSeatsField.setBounds(100, 348, 165, 27);
        contentPane.add(pnormalSeatsField);
        
        pbalconySeatsLabel = new JLabel("Balcony Seats");
        pbalconySeatsLabel.setBounds(330, 351, 93, 14);
        contentPane.add(pbalconySeatsLabel);
        
        pbalconySeatsField = new JTextField();
        pbalconySeatsField.setBounds(423, 348, 165, 27);
        contentPane.add(pbalconySeatsField);  
        
        cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    ShowManagerFrame frame1 = new ShowManagerFrame(frame.id,managerName);
			    frame1.setVisible(true);
				dispose();
			}
		});
        cancelButton.setBounds(386, 401, 165, 42);
        contentPane.add(cancelButton);
        
        addButton = new JButton("Add");
        addButton.setBounds(80, 401, 155, 42);
        contentPane.add(addButton);
        addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {				
				showName = showNameField.getText().trim();				
				String SHOWNAME_REGEX = "^[a-zA-Z\\s]*$";
				Boolean b = showName.matches(SHOWNAME_REGEX);				 
				if(!b || showName.length()==0) {
					JOptionPane.showMessageDialog(contentPane, "1)Upto 20 characters includes only spaces and alphabets\n2)Should not be empty","Invalid ShowName",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(showName.length()>20) {
					JOptionPane.showMessageDialog(contentPane, "1)Upto 20 characters includes only spaces and alphabets\n2)Should not be empty","Invalid ShowName",JOptionPane.ERROR_MESSAGE);
					return;
				}
				 				
				String fieldStartTime = t.getTime();
				sqlStartTime = validateDate(fieldStartTime);
				if(sqlStartTime == null) {
					JOptionPane.showMessageDialog(contentPane, "1)should be between tomorrow date and date after next 3 months from tomorrow \n2)Date should be exits","Invalid Date",JOptionPane.ERROR_MESSAGE);
					return;					
				}
				String fieldEndTime = t1.getTime();
				sqlEndTime = validateDate(fieldEndTime);
				if(sqlEndTime == null) {
					JOptionPane.showMessageDialog(contentPane, "1)should be between tomorrow date and date after next 3 months from tomorrow \n2)Date should be exits","Invalid Date",JOptionPane.ERROR_MESSAGE);
					return;					
				}		
				if(!isDateisLesser(fieldStartTime,fieldEndTime)) {
					JOptionPane.showMessageDialog(contentPane, "1)should be between tomorrow date and date after next 3 months from tomorrow \n2)Date should be exits","Invalid Date",JOptionPane.ERROR_MESSAGE);
					return;
				}			
				if(!isStartDateBefore3Months(fieldStartTime)) {
					JOptionPane.showMessageDialog(contentPane, "1)should be between tomorrow date and date after next 3 months from tomorrow \n2)Date should be exits","Invalid Date",JOptionPane.ERROR_MESSAGE);
					return;					
				}
				if(!isShowDurationValid(fieldStartTime,fieldEndTime)) {
					JOptionPane.showMessageDialog(contentPane, "1) Show duration must be between 1hr to 8hrs inclusively","Invalid Timmings",JOptionPane.ERROR_MESSAGE);
					return;					
				}				
				String a = normalSeatsField.getText();
				try {
					vipNormalSeats = Integer.parseInt(a);
					try {
						if(vipNormalSeats>0 && nseats-vipNormalSeats<0) {
							throw new Exception();
						}
						avn = nseats-vipNormalSeats;
					}
					catch(Exception e1age) {
						JOptionPane.showMessageDialog(contentPane, "1)VIP Normal Seats cannot be greater than Normal Seats\n2)Should not be empty\n3)Must be number and positive","Invalid VIP Normal Seats",JOptionPane.ERROR_MESSAGE);							
						return;							
					}
				}		    	
				catch(Exception eage) {
					JOptionPane.showMessageDialog(contentPane, "1)VIP Normal Seats cannot be greater than Normal Seats\n2)Should not be empty\n3)Must be number and positive","Invalid VIP Normal Seats",JOptionPane.ERROR_MESSAGE);
					return;						
				}
				String balcony = balconySeatsField.getText();
				try {
					vipBalconySeats = Integer.parseInt(balcony);
					try {
						if(vipBalconySeats>0 && bseats-vipBalconySeats<0) {
							throw new Exception();
						}
						avb = bseats-vipBalconySeats;
					}
					catch(Exception e1age) {
						JOptionPane.showMessageDialog(contentPane, "1)VIP Balcony Seats cannot be greater than Balcony Seats\n2)Should not be empty\n3)Must be number and positive","Invalid VIP Balcony Seats",JOptionPane.ERROR_MESSAGE);							
						return;							
					}
				}		    	
				catch(Exception eage) {
					JOptionPane.showMessageDialog(contentPane, "1)VIP Balcony Seats cannot be greater than Balcony Seats\n2)Should not be empty\n3)Must be number and positive","Invalid VIP Balcony Seats",JOptionPane.ERROR_MESSAGE);
					return;						
				}	
				String nprice = pnormalSeatsField.getText();				
				try {
					 normalPrice = Double.parseDouble(nprice);						

					try {
						if(normalPrice<=0d || normalPrice >=5000d) {
							throw new Exception();
						}
					}
					catch(Exception e) {						
						JOptionPane.showMessageDialog(contentPane, "1)Seat Price should not exceed 5000/-\n2)Normal seats price cannot be negative and zero\n3)Should not be empty\n4)Must be number","Invalid Normal Seat price",JOptionPane.ERROR_MESSAGE);							
						return;							
					}
				}		    	
				catch(Exception e) {					
					JOptionPane.showMessageDialog(contentPane, "1)Seat Price should not exceed 5000/-\n2)Normal seats price cannot be negative and zero\n3)Should not be empty\n4)Must be number","Invalid Normal Seat price",JOptionPane.ERROR_MESSAGE);
					return;						
				}
				String bprice = pbalconySeatsField.getText();
				try {
					 balconyPrice = Double.parseDouble(bprice);						

					try {
						if(balconyPrice<=0d ||  balconyPrice>5000d) {
							throw new Exception();
						}
					}
					catch(Exception e) {						
						JOptionPane.showMessageDialog(contentPane, "1)Seat Price should not exceed 5000/-\n2)Balcony seats price cannot be negative and zero\n3)Should not be empty\n4)Must be number","Invalid Balcony Seat price",JOptionPane.ERROR_MESSAGE);							
						return;							
					}
				}		    	
				catch(Exception e) {				
					JOptionPane.showMessageDialog(contentPane, "1)Seat Price should not exceed 5000/-\n2)Balcony seats price cannot be negative and zero\n3)Should not be empty\n4)Must be number","Invalid Balcony Seat price",JOptionPane.ERROR_MESSAGE);
					return;						
				}
				try {
					if(balconyPrice<=normalPrice) {
						throw new Exception();
					}
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(contentPane, "Balcony Seat price must be greater than Normal Seat price","Invalid Seat prices",JOptionPane.ERROR_MESSAGE);
					return;					
				}

				if(db.isShowExit(sqlStartTime, sqlEndTime)){
					JOptionPane.showMessageDialog(contentPane, "Already show exits on given date and time","Invalid Date & Time",JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					int vipnseats = Integer.parseInt(normalSeatsField.getText());
					int vipbseats = Integer.parseInt(balconySeatsField.getText());
					db.insertIntoShowsTable(showName, sqlStartTime, sqlEndTime, normalPrice, balconyPrice, avn, avb,vipnseats,vipbseats);
					int tableId = db.getShowId(sqlStartTime, sqlEndTime);
					if(tableId>0) {						
						db.createSeatTable(tableId);
					    int totalSeats=0;
					    totalSeats=nseats+bseats;	
					    addButton.setText("adding..");
					    for(int i=1;i<=totalSeats;i++) {
					    	if(i<=nseats) {
					    		if(book != null && book.nseatsArray.contains(i)) {
					    			db.insertIntoSeatTable(tableId, 0, 2,normalPrice);
					    		}
					    		else {
					    			db.insertIntoSeatTable(tableId, 0, 0,normalPrice);
					    		}
					    	}
					    	else {
					    		if(book != null && book.bseatsArray.contains(i)) {
					    			db.insertIntoSeatTable(tableId, 1, 2,balconyPrice);
					    		}
					    		else {
						    		db.insertIntoSeatTable(tableId, 1, 0,balconyPrice);					    	

					    		}
					    	}
					    	
					    }
					    addButton.setText("Add");
					    JOptionPane.showMessageDialog(contentPane, "Show add successfully!!!","Success",JOptionPane.INFORMATION_MESSAGE);					    
					    ShowManagerFrame window  = new ShowManagerFrame(frame.id,showManager);
					    window.setVisible(true);
					    
					    dispose();
						
					}
				}
			}
        	
        });
		
	}
	public boolean isShowDurationValid(String fieldStartTime,String fieldEndTime) {
		@SuppressWarnings("deprecation")
		double duration = (Date.parse(fieldEndTime)-Date.parse(fieldStartTime))/(1000f*60f*60f);
		if(duration >= 1.0 && duration <= 8.0) 
			return true;
		else
			return false;
	}
	public boolean isDateisLesser(String fieldStartTime,String fieldEndTime) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");	        
        df.setLenient(false);         
        java.util.Date sDate=null;
        java.util.Date eDate=null;
		try {
			sDate = df.parse(fieldStartTime);
	        eDate =  df.parse(fieldEndTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
        return sDate.before(eDate);
	}
	public boolean isStartDateBefore3Months(String fieldStartTime) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");	        
        df.setLenient(false);
		Calendar cal = Calendar.getInstance();
		Date today = new Date(cal.getTimeInMillis());
		cal.add(Calendar.MONTH, 3);
		cal.add(Calendar.DATE,1);		
	    Date lastday = new Date(cal.getTimeInMillis());
	    java.util.Date fieldDay=null;
		try {
			fieldDay = df.parse(fieldStartTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	    return fieldDay.before(lastday) && fieldDay.after(today);
	}
	public String validateDate(String fieldDate) {
	    DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date d=null;
	    try {
	        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");	        
	        df.setLenient(false);        
	        
	        d =  df.parse(fieldDate);	        
	    } catch (Exception e) {
	        return null;
	    }
	    
	/*    try {
	    	
	    	String validateFieldDate = month.getItemAt(month.getSelectedIndex())+"/"+date.getItemAt(date.getSelectedIndex())+"/"+year.getItemAt(year.getSelectedIndex());
	        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy ");
	        format.setLenient(false);
	        format.parse(validateFieldDate);	    	
	    }
	    catch(Exception e) {
	    	return false;
	    }*/
	    return outputformat.format(d);
		
	}
/*	public Object[] getDays() {
	    Calendar cal = Calendar.getInstance(); 
	    cal.add(Calendar.DATE, 1);
	    Date date = new Date(cal.getTimeInMillis());
	    int day = Integer.parseInt((date+"").split("-")[2]);
	    int remday = 32-day;
	    Object[] days = new Object[remday];
	    for(int i=0;i<remday;i++) {
	    	days[i]=day;
	    	day++;
	    	
	    }
	    
		return days;
	}
	public Object[] getMonths() {
	    Calendar cal = Calendar.getInstance(); 
	    Date date = new Date(cal.getTimeInMillis());
	    int month = Integer.parseInt((date+"").split("-")[1]);	
	    int remmonth = 13-month;
	    Object[] months = new Object[remmonth];
	    for(int i=0;i<remmonth;i++) {
	    	months[i]=month;
	    	month++;
	    	
	    }	    
	    return months;
	}*/
	public Object[] getYears() {
		Date date1 = new Date(System.currentTimeMillis());
		int year1 = Integer.parseInt((date1+"").split("-")[0]);
	    Calendar cal = Calendar.getInstance(); 
	    cal.add(Calendar.MONTH, 3);
	    cal.add(Calendar.DATE, 1);
	    Date date2 = new Date(cal.getTimeInMillis());
	    int year2 = Integer.parseInt((date2+"").split("-")[0]);	    
		if(year1 == year2) {
			Object[] years = {year1};
			return years;
		}
		else {
			Object[] years = {year1,year2};
			return years;			
		}
			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddShow s = new AddShow("Musthafa",1,new ArrayList<Integer>(),new ArrayList<Integer>());
		s.setVisible(true);
	}

}
