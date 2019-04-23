package sams;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
public class CancelTickets extends JFrame{
	private static final long serialVersionUID = 1L;
	public DataBase db = new DataBase();
	public ArrayList<Integer> nseatsArray=new ArrayList<Integer>(),bseatsArray=new ArrayList<Integer>();
	public int nseats,bseats;
	public int showId,userId;
	public String showName,userName;
	public JFrame frame,w;
	public GridLayout grid,grid1;
	public JPanel contentPane;
	public JPanel normalRows,balconyRows;
	public JLabel stage,normalSeats,balconySeats,bookedSeats,availableSeats,bookedSeatsMatter,availableSeatsMatter,VIPBookedSeats,VIPBookedSeatsMatter;
	public JButton unBookButton,cancel;
	public String seatTypes[] = {"Normal","Balcony"};
	public CancelTickets(int showId,int userId,String userName) {
		this.frame= this;
		this.userId= userId;
		this.userName = userName;
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		this.setResizable(false);
		this.setLayout(null);
		this.setBounds(0,0,1550,780);
		stage = new JLabel("                                             STAGE");
		stage.setBounds(380, 5, 600, 40);
		stage.setOpaque(true);
		stage.setFont(new Font("Tahoma", Font.BOLD,20));
		stage.setBackground(Color.BLACK);
		stage.setForeground(Color.WHITE);
		this.getContentPane().add(stage);
		
		normalSeats = new JLabel("Normal Seats");
		normalSeats.setFont(new Font("Tahoma", Font.BOLD,15));
		normalSeats.setBounds(7,12,200,50);
		this.getContentPane().add(normalSeats);
		
		balconySeats = new JLabel("Balcony Seats");
		balconySeats.setFont(new Font("Tahoma", Font.BOLD,15));
		balconySeats.setBounds(7,430,200,50);
		this.getContentPane().add(balconySeats);
		grid = new GridLayout(15,23);
		normalRows = new JPanel();
		normalRows.setLayout(grid);		
		int k=1;
		db.connect();
		ResultSet rs = db.getBookedSeats(showId);
		ArrayList<Integer> a = new ArrayList<Integer>();
		try {
			while(rs.next()) {
				a.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = db.getVIPSeats(showId);
		ArrayList<Integer> b = new ArrayList<Integer>();
		try {
			while(rs.next()) {
				b.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		for(int i=0;i<15;i++) {
			for(int j=0;j<23;j++) {
				if(j==0 || j==12) {
					int value =65+i;
					char c=(char) value ;
					JButton m = new JButton(c+"");
					m.setFont(new Font("Tahoma", Font.BOLD,10));
					m.setForeground(Color.RED);
					m.setBackground(Color.WHITE);
					m.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {							
							// TODO Auto-generated method stub
							JOptionPane.showMessageDialog(contentPane, "This row name is "+m.getText()+", contains 20 seats","Row '"+m.getText()+"'",JOptionPane.PLAIN_MESSAGE);
						}
						
					});
					normalRows.add(m);					
				}
				else if(j==11) {
					JButton m = new JButton("");
					m.setFont(new Font("Tahoma", Font.BOLD,8));
					m.setEnabled(false);
					normalRows.add(m);					
				}
				else {
				JButton m = new JButton(k+++"");
				m.setFont(new Font("Tahoma", Font.BOLD,8));
				if(!a.contains(k-1)){
					m.setEnabled(false);
				}
				if(b.contains(k-1)) {
					m.setEnabled(false);
					m.setBackground(Color.GREEN);
				}
				m.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						int seatId = Integer.parseInt(m.getText());
						if(seatId >=1 || seatId <=300) {
							nseats++;
							nseatsArray.add(seatId);
							
						}
						m.setEnabled(false);
						m.setBackground(new Color(255, 77, 77));
					}
					
				});					
				normalRows.add(m);			
				}				
			}
		}
		
		normalRows.setBounds(0, 50, 1370, 380);
		grid1 = new GridLayout(10,23);
		balconyRows = new JPanel();
		balconyRows.setLayout(grid1);		
		k=1;
		for(int i=0;i<10;i++) {
			for(int j=0;j<23;j++) {
				if(j==0 || j==12) {
					int value =65+i;
					char c=(char) value ;
					JButton m = new JButton(c+"");
					m.setFont(new Font("Tahoma", Font.BOLD,10));
					m.setForeground(Color.RED);
					m.setBackground(Color.WHITE);
					m.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {							
							// TODO Auto-generated method stub
							JOptionPane.showMessageDialog(contentPane, "This row name is "+m.getText()+", contains 20 seats","Row '"+m.getText()+"'",JOptionPane.PLAIN_MESSAGE);
						}
						
					});
					balconyRows.add(m);					
				}
				else if(j==11) {
					JButton m = new JButton("");
					m.setFont(new Font("Tahoma", Font.BOLD,8));
					m.setEnabled(false);
					balconyRows.add(m);					
				}
				else {
				JButton m = new JButton(k+++"");
				m.setFont(new Font("Tahoma", Font.BOLD,8));
				if(!a.contains(k-1+300)){
					m.setEnabled(false);
				}
				if(b.contains(k-1+300)) {
					m.setEnabled(false);
					m.setBackground(Color.GREEN);
				}				
				m.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						int seatId = Integer.parseInt(m.getText());
						if(seatId >300 || seatId <=500) {
							bseats++;
							bseatsArray.add(seatId+300);
							
						}
						m.setEnabled(false);
						m.setBackground(new Color(255, 77, 77));
					}
					
				});					
				balconyRows.add(m);
			
				}				
			}
		}
		balconyRows.setBounds(0, 470, 1370, 230);	
		this.getContentPane().add(normalRows);
		this.getContentPane().add(balconyRows);
		unBookButton = new JButton("Cancel");
		unBookButton.setFont(new Font("Tahoma", Font.BOLD,12));
		unBookButton.setBounds(570,705,100,30);
		unBookButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				db.createTransactionTable();
				if(nseats == 0 && bseats == 0) {
					JOptionPane.showMessageDialog(frame, "No Seats selected !");
					return;					
				}
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(contentPane, "Are you sure want to cancel Tickets?", "Alert!", dialogButton);	
				if(dialogResult == JOptionPane.YES_OPTION){
					Document document1 = new Document();
					
					try {
						PdfWriter.getInstance(document1, new FileOutputStream("last_transaction.pdf"));
					} catch (FileNotFoundException | DocumentException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					}
					document1.open();			
					String currDate = new Date(System.currentTimeMillis())+"";
					for(int i=0;i<nseatsArray.size();i++) {
						
						int seatId = nseatsArray.get(i);
						db.updateSeatStatus(showId, seatId,0);
						
						ResultSet rs = db.getTicketDetails(showId, seatId);
						try {
							document1.add(new Paragraph("Cancellation of Ticket Transaction ID : "+db.getTransactionId(showId, seatId)+
									"\nShow ID : "+showId+
									"\nShow Name : "+rs.getString(1)+
									"\nShow Start Time : "+rs.getString(2)+
									"\nShow End Time : "+rs.getString(3)+
									"\nSeat Type : "+seatTypes[rs.getInt(4)]+
									"\nSeat Number : "+seatId+
									"\nBooking Date : "+currDate+
									"\nCancellation Date : "+currDate+
									"\nAmount : "+db.getTicketPrice(rs.getInt(4), showId)+
									"\n\n"));
							document1.add(new LineSeparator());	
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}									
						db.deleteTransaction(showId, seatId);
					}
					for(int i=0;i<bseatsArray.size();i++) {
						int seatId = bseatsArray.get(i);					
						db.updateSeatStatus(showId, seatId,0);
						ResultSet rs = db.getTicketDetails(showId, seatId);
						try {
							document1.add(new Paragraph("Cancellation of Ticket Transaction ID : "+db.getTransactionId(showId, seatId)+
									"\nShow ID : "+showId+
									"\nShow Name : "+rs.getString(1)+
									"\nShow Start Time : "+rs.getString(2)+
									"\nShow End Time : "+rs.getString(3)+
									"\nSeat Type : "+seatTypes[rs.getInt(4)]+
									"\nSeat Number : "+(seatId-300)+
									"\nBooking Date : "+currDate+
									"\nAmount : "+db.getTicketPrice(rs.getInt(4), showId)+
									"\n\n"));
							document1.add(new LineSeparator());	
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						db.deleteTransaction(showId, seatId);
					}
					
					JOptionPane.showMessageDialog(frame, "Cancelltion successfully done");
				    document1.close();
				    if (Desktop.isDesktopSupported()) {
					    File myFile = new File("last_transaction.pdf");
						//JOptionPane.showMessageDialog(null,seatids.size());
					    try {
							Desktop.getDesktop().open(myFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				   
					}	
				    db.updateSeatAvailability(-nseats, -bseats, showId);
				    
					dispose();
					SalesPersonFrame s = new SalesPersonFrame(userId,userName);
					s.setVisible(true);
				}
				else {
					dispose();
					SalesPersonFrame s = new SalesPersonFrame(userId,userName);
					s.setVisible(true);					
				}
			}
			
		});
		this.getContentPane().add(unBookButton);
		
		cancel = new JButton("Back");
		cancel.setFont(new Font("Tahoma", Font.BOLD,12));
		cancel.setBounds(685,705,100,30);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				SalesPersonFrame s = new SalesPersonFrame(userId,userName);
				s.setVisible(true);
			}
			
		});
		this.getContentPane().add(cancel);		

		VIPBookedSeats = new JLabel();		
		VIPBookedSeats.setBackground(Color.GREEN);
		VIPBookedSeats.setBounds(890,705,30,27);
		VIPBookedSeats.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		VIPBookedSeats.setOpaque(true);
		this.getContentPane().add(VIPBookedSeats);
		
		VIPBookedSeatsMatter = new JLabel("VIP Seats");
		VIPBookedSeatsMatter.setBounds(927,705,100,27);
		this.getContentPane().add(VIPBookedSeatsMatter);		
		
		bookedSeats = new JLabel();		
		bookedSeats.setBackground(new Color(238,238,238));
		bookedSeats.setBounds(1000,705,30,27);
		bookedSeats.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bookedSeats.setOpaque(true);
		this.getContentPane().add(bookedSeats);
		
		bookedSeatsMatter = new JLabel("Booked Seats");
		bookedSeatsMatter.setBounds(1035,705,100,27);
		this.getContentPane().add(bookedSeatsMatter);
		
		availableSeats = new JLabel();		
		availableSeats.setBackground(new Color(204, 230, 255));
		availableSeats.setBounds(1140,705,30,27);
		availableSeats.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		availableSeats.setOpaque(true);
		this.getContentPane().add(availableSeats);
		
		availableSeatsMatter = new JLabel("Available Seats");
		availableSeatsMatter.setBounds(1180,705,100,27);
		this.getContentPane().add(availableSeatsMatter);	
		this.showName = db.getShowName(showId);
		this.setTitle(showName+"'s Seat Cancelltion");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CancelTickets s = new CancelTickets(1,1,"Sravan Kumar");
		s.setVisible(true);		
	}

}
