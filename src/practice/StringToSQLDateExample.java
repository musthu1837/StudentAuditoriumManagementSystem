package practice;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;  
public class StringToSQLDateExample {  
public static void main(String[] args) {  
   // String str="04/30/2019 7:00:00 PM";  
   /* Date date=Date.valueOf(str);//converting string into sql date  
    Calendar cal = Calendar.getInstance(); 
    cal.add(Calendar.DATE, 17);
    System.out.println(new Date(cal.getTimeInMillis()));
    System.out.println(date); 
    Time time = Time.valueOf("7:36:50");
    System.out.println(time);*/
   // System.out.println(isValideTime(str));
   // String past = "2019-04-04 19:00:00";    
    	
	int seatId = 197;
	System.out.println(seatId/20);
}  
public boolean isShowExits(String showTime) {
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

private static boolean isValidDate(String input) {
    String formatString = "MM/dd/yyyy";

    try {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        format.setLenient(false);
        format.parse(input);
    } catch (ParseException e) {
        return false;
    } catch (IllegalArgumentException e) {
        return false;
    }

    return true;
}
private static boolean isValideTime(String input) {

    try {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setLenient(false);        
       java.util.Date d =  df.parse(input);
        System.out.println(outputformat.format(d));
    } catch (ParseException e) {
        return false;
    } catch (IllegalArgumentException e) {
        return false;
    }

    return true;
}
}