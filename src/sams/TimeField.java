package sams;

import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeField extends JPanel{	
	private static final long serialVersionUID = 1L;
	public JComboBox<Object> hoursField,minField,amPmField,date,month,year;
	public JLabel hoursLabel,minLabel,header,ddLabel,mmLabel,yyyyLabel;
	public JButton next;
	public String time;
	public boolean toggle=false;
	public TimeField() {
		//this.setBounds(10, 10, 510, 100);
		this.setLayout(null);
		
		yyyyLabel = new JLabel("YYYY:");
		yyyyLabel.setBounds(0,30,40,14);
		
		year = new JComboBox<Object>(getYears());    
		year.setBounds(35,25,55,27);
		
		mmLabel = new JLabel("MM:");
		mmLabel.setBounds(98,30,30,14);

		Object months[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		month = new JComboBox<Object>(months);    
		month.setBounds(125,25,40,27);
		
		ddLabel = new JLabel("DD:");
		ddLabel.setBounds(170,30,20,14);
		
		Object dates[] = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		date = new JComboBox<Object>(dates);    
		date.setBounds(192,25,50,27);
		
		hoursLabel = new JLabel("Hour:");
		hoursLabel.setBounds(258, 25, 35, 27);
		hoursLabel.setFont(new Font("Tahoma", Font.BOLD,12));
		
		Object[] hours = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		hoursField = new JComboBox<Object>(hours); 
		hoursField.setBounds(295,25,50,27);
		
		minLabel = new JLabel("Min:");
		minLabel.setBounds(350, 25, 30, 27);
		minLabel.setFont(new Font("Tahoma", Font.BOLD,12));
	
		Object[] minutes = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"};
		minField = new JComboBox<Object>(minutes);
		minField.setBounds(380, 25, 50, 27);
		
		Object[] amPm = {"AM","PM"};
		amPmField = new JComboBox<Object>(amPm);
		amPmField.setBounds(440, 25, 50, 27);
						
		this.setLayout(null);
		this.setBackground(new Color(255, 192, 203));
		//this.setBackground(Color.BLUE);		
		this.add(yyyyLabel);
		this.add(year);
		this.add(mmLabel);
		this.add(month);
		this.add(ddLabel);
		this.add(date);
		this.add(hoursLabel);
		this.add(hoursField);
		this.add(minLabel);
		this.add(minField);
		this.add(amPmField);		
	}	
	public String getTime() {
		time = month.getItemAt(month.getSelectedIndex())+"/"+date.getItemAt(date.getSelectedIndex())+"/"+year.getItemAt(year.getSelectedIndex())+" "+hoursField.getItemAt(hoursField.getSelectedIndex())+":"+minField.getItemAt(minField.getSelectedIndex())+":00 "+amPmField.getItemAt(amPmField.getSelectedIndex());
		return time;
	}
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
	public static void main(String args[]) {
		new TimeField().setVisible(true);
	}
}
