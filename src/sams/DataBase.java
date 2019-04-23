package sams;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase 
{
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	String sql;
	public ResultSet getShowsInYear(int year) {
		try {
			
			stmt = con.createStatement();
		    sql = "select id as ShowId,name as ShowName from shows where year(starttime)='"+year+"'";		    
		    rs = stmt.executeQuery(sql);
		    return rs;	    		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}			
	}
	public boolean isEmployeeExits() {
		try {
			
			stmt = con.createStatement();
		    sql = "select * from user where post='2' or post='1'";		    
		    rs = stmt.executeQuery(sql);
		    if(rs.next())
		    	return true;
		    
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;			
		}		
		return false;
	}
	public double getTotalExpenditure(int showId) {
		try {
			
			stmt = con.createStatement();
		    sql = "select sum(amount) from expenditure where showid='"+showId+"'";		    
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getDouble(1);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;			
		}			
	}
	public String[] getExpendituresRemove(int showId) {
		try {
			
			stmt = con.createStatement();
			sql = "Select count(*) from expenditure where showid='"+showId+"'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int size = rs.getInt(1);
		    sql = "Select id,type from expenditure where showid='"+showId+"'";		    
		    rs = stmt.executeQuery(sql);
		    String[] sp = new String[size];
		    int i=0;
		    while(rs.next()) {
		    	sp[i] = new String(rs.getInt(1)+"-"+rs.getString(2));
		    	i++;
		    }
		    return sp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}
	}	
	public String[] getEmployees() {
		try {
			
			stmt = con.createStatement();
			sql = "Select count(*) from user where post!='0'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int size = rs.getInt(1);
		    sql = "SELECT id,name FROM user WHERE post !='0'";		    
		    rs = stmt.executeQuery(sql);
		    String[] sp = new String[size];
		    int i=0;
		    while(rs.next()) {
		    	sp[i] = new String(rs.getInt(1)+"-"+rs.getString(2));
		    	i++;
		    }
		    return sp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}
	}	
	public ResultSet getExpenditure(int showId) {
		try {
			
			stmt = con.createStatement();
		    sql = "SELECT type as Type,amount as Amount FROM expenditure WHERE showid ='"+showId+"' ";		    
		    rs = stmt.executeQuery(sql);
		    return rs;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}
	}
	public void insertIntoExpenditure(int aid,int showId,String type,double amount) {
		try {
			stmt = con.createStatement();
		    sql = "INSERT INTO expenditure(accountantid,showid,type,amount) " +
	                   "VALUES ('"+aid+"','"+showId+"','"+type+"','"+amount+"')" ;
		    stmt.executeUpdate(sql);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void createExpenditureTable() {
		try {
			stmt = con.createStatement();
			sql = "CREATE TABLE IF NOT EXISTS " +
	                   " expenditure(id INTEGER not NULL AUTO_INCREMENT, " +
	                   " accountantid INTEGER, " +
	                   " showid INTEGER, " +
	                   " type VARCHAR(255), " + 
	                   " amount DOUBLE, " + 
	                   " PRIMARY KEY ( id ))";
			stmt.executeUpdate(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public boolean removeExpenditure(int eid) {
		try {
			
			stmt = con.createStatement();
		    sql = "Delete from expenditure where id='"+eid+"'";		 
		    int i =stmt.executeUpdate(sql);
		    if(i>0)
		    	return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		return false;
	}	
	public boolean fireEmployee(int pid) {
		try {
			
			stmt = con.createStatement();
		    sql = "Delete from user where id='"+pid+"'";		 
		    int i =stmt.executeUpdate(sql);
		    if(i>0)
		    	return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		return false;
	}
	public double totalSalesofShow(int showId) {
		try {
			
			stmt = con.createStatement();
		    sql = "select sum(price) from seat_show_"+showId+" where status='1'";		    
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getDouble(1);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;			
		}		
	}
	public ResultSet getShowStatus(int showId) {
		try {
			
			stmt = con.createStatement();
		    sql = "select avnseats,avbseats,VIPnseats,VIPbseats from shows where id='"+showId+"'";		    
		    rs = stmt.executeQuery(sql);
		    return rs;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}					
	} 
	public ResultSet getShowSales(int showId) {
		try {
			
			stmt = con.createStatement();
		    sql = "select id as SeatId,name as SeatType,price as SeatPrice from seattype,seat_show_"+showId+" where status='1' and type=seattype";		    
		    rs = stmt.executeQuery(sql);
		    return rs;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}					
	}		
	public ResultSet getSalesPerEmployee(int showId) {
		try {
			
			stmt = con.createStatement();
		    sql = "select tr.spid as Id,u.name as Name,sum(se.price) as Totalamount from user as u,transactions tr,seat_show_"+showId+" se WHERE u.id=tr.spid and tr.seatid=se.id GROUP BY spid";		    
		    rs = stmt.executeQuery(sql);
		    return rs;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}					
	}	
	public ResultSet getTransactions() {
		try {
			
			stmt = con.createStatement();
		    sql = "Select * from transactions";
		    rs = stmt.executeQuery(sql);
		    return rs;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;			
		}					
	}
	public boolean isSalesPersonExit() {
		try {
			
			stmt = con.createStatement();
		    sql = "Select * from user where post=1";
		    rs = stmt.executeQuery(sql);
		    return rs.next();
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;			
		}			
	}
	public void deleteTransaction(int showId,int seatId) {
		try {
			
			stmt = con.createStatement();
		    sql = "Delete from transactions where showid='"+showId+"' and seatId='"+seatId+"'";
		    stmt.executeUpdate(sql);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	public void updateSeatAvailability(int avnseats,int avbseats,int showId) {
		try {
			
			stmt = con.createStatement();
			sql ="select avnseats,avbseats from shows where id='"+showId+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				
				avnseats=rs.getInt(1)-avnseats;
				avbseats=rs.getInt(2)-avbseats;
			}
		    sql = "Update shows set avnseats='"+avnseats+"' , avbseats='"+avbseats+"' where id='"+showId+"'";
		    stmt.executeUpdate(sql);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public double getTicketPrice(int seattype,int showId) {
		try {
			stmt = con.createStatement();
			if(seattype==1)
				sql = "select bprice from shows where id='"+showId+"'";
			else
				sql = "select nprice from shows where id='"+showId+"'";
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getDouble(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}		
	}
	public int getTransactionId(int showId,int seatId) {
		try {
			stmt = con.createStatement();
		    String sql = "select id from transactions where showid='"+showId+"' and seatid='"+seatId+"'";
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}		
	}
	public String getBookingDate(int showId,int seatId) {
		try {
			stmt = con.createStatement();
		    String sql = "select dateofbooking from transactions where showid='"+showId+"' AND seatid='"+seatId+"'";
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getDate(1)+"";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
		
	}
	public ResultSet getTicketDetails(int showId,int seatId) {
		try {
			stmt = con.createStatement();
		    String sql = "select sh.name,sh.starttime,sh.endtime,se.seattype from shows as sh ,seat_show_"+showId+" se where sh.id='"+showId+"' AND se.id='"+seatId+"'";
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	public void insertIntoTransaction(int showId,String date,int seatId,int pId) {
		try {
			stmt = con.createStatement();
		    sql = "INSERT INTO Transactions(showid,dateofbooking,seatid,spid) " +
	                   "VALUES ('"+showId+"','"+date+"','"+seatId+"','"+pId+"')" ;
		    stmt.executeUpdate(sql);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public void updateSeatStatus(int showId,int seatId,int status) {
		try {
			stmt = con.createStatement();
			sql = "update seat_show_"+showId+" set status='"+status+"' where id='"+seatId+"'"; 
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public void createTransactionTable() {
		try {
			stmt = con.createStatement();
		    sql = "CREATE TABLE IF NOT EXISTS " +
	                   "transactions(id INTEGER not NULL AUTO_INCREMENT, " +
	                   "showid INTEGER, " +
	                   "dateofbooking DATETIME, " +	                   
	                   "seatid INTEGER, " +
	                   "spid INTEGER, " +
	                   "PRIMARY KEY ( id ))"; 
		    stmt.executeUpdate(sql);		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}			
	}
	public int getUserId(long phoneNo) {
		try {
			stmt = con.createStatement();
		    String sql = "SELECT id from user where mobilenumber='"+phoneNo+"'";
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block						
			return 0;
		}				
	}
	public String getShowName(int showId) {
		try {
			stmt = con.createStatement();
		    String sql = "SELECT name from shows where id='"+showId+"'";
		    rs = stmt.executeQuery(sql);
		    rs.next();
		    return rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	public ResultSet getVIPSeats(int tableId) {
		try {
			stmt = con.createStatement();
		    String sql = "SELECT id from seat_show_"+tableId+" where status ='2'";
		    rs = stmt.executeQuery(sql);
		    return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}	
	public ResultSet getBookedSeats(int tableId) {
		try {
			stmt = con.createStatement();
		    String sql = "SELECT id from seat_show_"+tableId+" where status ='1'";
		    rs = stmt.executeQuery(sql);
		    return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	public ResultSet getShowDetails(int showId) {
		try {
			stmt = con.createStatement();
		    String sql = "SELECT avnseats,avbseats,nprice,bprice FROM shows WHERE id ='"+showId+"' ";
		    rs = stmt.executeQuery(sql);
		    return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ResultSet getShows() {
		try {
			stmt = con.createStatement();
			sql = "select name,starttime,endtime from shows";
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean isShowTableExits() {
	      try {
	    	  stmt = con.createStatement();
		      DatabaseMetaData md = con.getMetaData();
		      rs = md.getTables(null, null,"shows", null);
		      boolean b = rs.next();
		      
		      return b;
	      }
		  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return false;
	}
	public void insertIntoSeatTable(int tableId,int seatType,int status,double price) {
		try {
			stmt = con.createStatement();
		    sql = "INSERT INTO seat_show_"+tableId+"(seattype,status,price) " +
	                   "VALUES ('"+seatType+"','"+status+"','"+price+"')" ;
		    stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getShowId(String startTime, String endTime) {
		try {
			stmt = con.createStatement();
			sql = "Select id from shows where starttime='"+startTime+"' and endtime='"+endTime+"'";			
			rs = stmt.executeQuery(sql);		
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public void insertIntoShowsTable(String name,String startTime,String endTime,double nprice,double bprice,int avnseats,int avbseats,int VIPnseats,int VIPbseats) {
		try {
			stmt = con.createStatement();
		    sql = "INSERT INTO shows(name,starttime,endtime,nprice,bprice,avnseats,avbseats,VIPnseats,VIPbseats) " +
	                   "VALUES ('"+name+"','"+startTime+"','"+endTime+"','"+nprice+"','"+bprice+"','"+avnseats+"','"+avbseats+"','"+VIPnseats+"','"+VIPbseats+"') " ;
		    stmt.executeUpdate(sql);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void createSeatTable(int id) {
		try {
			stmt = con.createStatement();
			String sql = "CREATE TABLE seat_show_"+id+"(id INTEGER not NULL AUTO_INCREMENT, " +
	                  " seattype INTEGER, "+
	                  "status INTEGER,"+
	                  "price DOUBLE,"+
	                  " PRIMARY KEY ( id ) )"; 			
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void createShowsTable() {
		try {
			stmt = con.createStatement();
		    sql = "CREATE TABLE IF NOT EXISTS " +
		    			"shows(id INTEGER not NULL AUTO_INCREMENT, " +
	                   " name VARCHAR(255), " + 
	                   " starttime datetime, "+
	                   " endtime datetime, "+
	                   " nprice INTEGER, "+
	                   " bprice INTEGER, "+
	                   " avnseats INTEGER, "+
	                   " avbseats INTEGER, "+	
	                   " VIPnseats INTEGER, "+
	                   " VIPbseats INTEGER, "+
	                   " PRIMARY KEY ( id ))"; 
		    stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean isShowExit(String startTime,String endTime) {
		try {
			stmt = con.createStatement();
			sql = "select * from shows where starttime between '"+startTime+"' and '"+endTime+"'";		
			rs = stmt.executeQuery(sql);
			if(rs.next())
				return true;
			else {
				stmt = con.createStatement();
				sql = "select * from shows where endtime between '"+startTime+"' and '"+endTime+"'";	
				rs = stmt.executeQuery(sql);
				if(rs.next())
					return true;
				else
					return false;
			}
		}
		catch (SQLException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;		
	}
	public void connect()
	{
		if(con != null)
			return;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			try
			{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
				Statement stmt = null;
				stmt = con.createStatement();
			    String sql = "CREATE DATABASE IF NOT EXISTS SAMS";
			    stmt.executeUpdate(sql);		
			    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sams","root",""); 
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e)
		{	System.out.println("Add MySQL jar file");
			e.printStackTrace();
		}
	}
	
	public void disconnect()
	{
		if(con != null)
		{
			try 
			{
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public void createSeatTypeTabel() {
		try {
			stmt = con.createStatement();
		    sql = "CREATE TABLE IF NOT EXISTS " +
	                   "seattype( type INTEGER, " + 
	                   " name VARCHAR(255), " + 	                  
	                   " PRIMARY KEY ( type ))"; 

		    stmt.executeUpdate(sql);				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	public void insertIntoSeatType(int type,String name) {
		try {
			stmt = con.createStatement();
		    sql = "INSERT INTO seattype(type,name) " +
	                   "VALUES ('"+type+"','"+name+"')" ;
		    stmt.executeUpdate(sql);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}		
	}	
	public void createUserTabel() {
		try {
			stmt = con.createStatement();
		    sql = "CREATE TABLE IF NOT EXISTS " +
	                   "user(id INTEGER not NULL AUTO_INCREMENT, " +
	                   " name VARCHAR(255), " + 
	                   " gender VARCHAR(255), " + 
	                   " age INTEGER, " + 
	                   " mobilenumber LONG, " + 
	                   " email VARCHAR(255), " + 
	                   " address VARCHAR(255), " + 
	                   " username VARCHAR(255), " +
	                   " password VARCHAR(255), " +
	                   " nseats INTEGER, "+
	                   " bseats INTEGER, "+
	                   " post INTEGER, "+
	                   " seats_per_row INTEGER, "+
	                   " PRIMARY KEY ( id ))"; 

		    stmt.executeUpdate(sql);				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public boolean isShowManagerExits() {	
	    sql = "Select * from user where post = 0";
	    try {
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public ResultSet getUserDetails(int id) {
		sql = "select * from user where id ='"+id+"'";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	public ResultSet getShowManagerDetails() {
		sql = "select * from user where post =0";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void updateUser(String name,String gender,int age,long mobileNumber,String email,String address,String username, String password,int id) {
		try {
			stmt = con.createStatement();
			sql = "Update user set name='"+name+"' , gender='"+gender+"' , age='"+age+"' , mobilenumber='"+mobileNumber+"' , email='"+email+"' , username='"+username+"' , password='"+password+"' where id='"+id+"'";
		    stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	public void insertUser(String name,String gender,int age,long mobileNumber,String email,String address,String username, String password, int nseats,int bseats,int seatsPerRow,int post) {
		try {
			stmt = con.createStatement();
			if(post == 0) {
				sql = "delete from user where post=0";
				stmt.executeUpdate(sql);
			}
		    sql = "INSERT INTO user(name,gender,age,mobilenumber,email,address,username,password,nseats,bseats,post,seats_per_row) " +
	                   "VALUES ('"+name+"','"+gender+"','"+age+"','"+mobileNumber+"','"+email+"','"+address+"','"+username+"','"+password+"','"+nseats+"','"+bseats+"','"+post+"','"+seatsPerRow+"') ";	         
		    stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public String userLogin(String username,String password,int post) {
		sql = "Select id,name from user where username ='"+username+"'"+"and password = '"+password+"'"+"and post='"+post+"'";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt(1)+"$"+rs.getString(2);
			}
			else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	public ResultSet getSeatsOfAuditorium() {
		sql = "select nseats,bseats from user where post = 0";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			return rs; 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public Connection getConnection()
	{
		return this.con;
	}
}
