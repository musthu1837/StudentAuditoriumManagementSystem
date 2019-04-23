package sams;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BookVIPTicket extends JFrame{
	private static final long serialVersionUID = 1L;
	public DataBase db = new DataBase();
	public ArrayList<Integer> nseatsArray=new ArrayList<Integer>(),bseatsArray=new ArrayList<Integer>();
	public int nseats,bseats;
	public int showId;
	public String showName;
	public JFrame frame,w;
	public GridLayout grid,grid1;
	public JPanel contentPane;
	public JPanel normalRows,balconyRows;
	public JLabel stage,normalSeats,balconySeats,bookedSeats,availableSeats,bookedSeatsMatter,availableSeatsMatter;
	public JButton oK,cancel;
	public BookVIPTicket(AddShow w) {
		this.frame= this;
		this.w = w;
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
				normalRows.add(m);
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
					}
					
				});				
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
					balconyRows.add(m);
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
						}
						
					});
				}				
			}
		}
		balconyRows.setBounds(0, 470, 1370, 230);	
		this.getContentPane().add(normalRows);
		this.getContentPane().add(balconyRows);
		oK = new JButton("Ok");
		oK.setFont(new Font("Tahoma", Font.BOLD,12));
		oK.setBounds(570,705,100,30);
		oK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				w.balconySeatsField.setText(bseats+"");
				w.normalSeatsField.setText(nseats+"");
				w.setVisible(true);
			}
			
		});
		this.getContentPane().add(oK);
		
		cancel = new JButton("Cancel");
		cancel.setFont(new Font("Tahoma", Font.BOLD,12));
		cancel.setBounds(685,705,100,30);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				w.balconySeatsField.setText("0");
				w.normalSeatsField.setText("0");
				w.setVisible(true);
			}
			
		});
		this.getContentPane().add(cancel);		
		
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
		this.setTitle("VIP seats");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BookVIPTicket s = new BookVIPTicket(null);
		s.setVisible(true);		
	}

}
