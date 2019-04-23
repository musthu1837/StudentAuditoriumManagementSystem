package sams;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AccountantAdd extends JFrame {
	private DataBase db = new DataBase();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane,panel;
	private JLabel header,nameLabel,genderLabel,ageLabel;
	private JLabel mobileNumberLabel,userNameLabel,addressLabel,passWordLabel;
	private JLabel emailLabel;
	private JTextField ageField,nameField,mobileNumberField,emailField,userNameField,addressField;
	private JPasswordField passWordField;
	private JRadioButton maleRadioButton,femaleRadioButton,otherRadioButton;
	private ButtonGroup genderButtonGroup;
	private JButton showButton,cancleButton,updateButton;
	private boolean toggle = false;
	String showManager = null;
	int id;
    String name = null;
    String gender = null;
    int age = 0;
    long mobilenumber;
    String email = null;
    String address = null;
    String username = null;
    String password = null;	
    int nseats,bseats,seatsperrow;
	public AccountantAdd(int id,String _showManager) {
		this.showManager = _showManager;
		this.id = id;
		//settings for window
		setResizable(false);
		setTitle("Accountant Add");
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
		setBounds(250, 100, 564, 553);
		
		//setting for window background
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 102, 51));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//creating panel to add entry fields
		panel = new JPanel();
		panel.setBackground(new Color(204, 102, 51));
		panel.setLayout(null);
		panel.setBounds(8, 11, 537, 403);
		contentPane.add(panel);		
		
		//creating header
		header = new JLabel("Login Details");
		header.setFont(new Font("Tahoma", Font.BOLD, 20));
		header.setBounds(205, 11, 250, 29);
		panel.add(header);
		
		//creating name label and field
		nameLabel = new JLabel("Name");
		nameLabel.setBounds(20, 51, 158, 28);
		panel.add(nameLabel);		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(188, 51, 339, 29);
		panel.add(nameField);		
		
		//creating gender radio buttons
		genderLabel = new JLabel("Gender");
		genderLabel.setBounds(20, 90, 46, 27);
		panel.add(genderLabel);
		maleRadioButton = new JRadioButton("Male");
		maleRadioButton.setBounds(188, 90, 90, 27);
		maleRadioButton.setBackground(new Color(204, 102, 51));
		maleRadioButton.setSelected(true);
		panel.add(maleRadioButton);
		femaleRadioButton = new JRadioButton("Female");
		femaleRadioButton.setBounds(278, 90, 90, 27);
		femaleRadioButton.setBackground(new Color(204, 102, 51));
		panel.add(femaleRadioButton);		
		otherRadioButton = new JRadioButton("Other");
		otherRadioButton.setBounds(368, 90, 90, 27);
		otherRadioButton.setBackground(new Color(204, 102, 51));
		panel.add(otherRadioButton);		
		genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(femaleRadioButton);
		genderButtonGroup.add(maleRadioButton);
		genderButtonGroup.add(otherRadioButton);
		
		//creating Age field
		ageLabel = new JLabel("Age");
		ageLabel.setBounds(20, 128, 158, 28);
		panel.add(ageLabel);
		ageField = new JTextField();
		ageField.setBounds(188, 132, 57, 27);
		panel.add(ageField);		
		
		//creating mobile number field
		mobileNumberLabel = new JLabel("Mobile Number");
		mobileNumberLabel.setBounds(20, 167, 97, 28);
		panel.add(mobileNumberLabel);
		mobileNumberField = new JTextField();
		mobileNumberField.setColumns(10);
		mobileNumberField.setBounds(188, 167, 339, 29);
		panel.add(mobileNumberField);		
		
		//creating Email address field
		emailLabel = new JLabel("Email");
		emailLabel.setBounds(20, 210, 97, 28);
		panel.add(emailLabel);
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(188, 210, 339, 29);
		panel.add(emailField);
		
		//creating Address field
		addressLabel = new JLabel("Address");
		addressLabel.setBounds(20, 249, 97, 28);
		panel.add(addressLabel);		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(188, 249, 339, 29);
		panel.add(addressField);
		
		//creating userName field
		userNameLabel = new JLabel("Username");
		userNameLabel.setBounds(20, 288, 97, 28);
		panel.add(userNameLabel);
		userNameField = new JTextField();
		userNameField.setColumns(10);
		userNameField.setBounds(188, 288, 339, 29);
		panel.add(userNameField);
		
		//creating password field
		passWordLabel = new JLabel("Password");
		passWordLabel.setBounds(20, 327, 97, 28);
		panel.add(passWordLabel);
		passWordField = new JPasswordField();
		passWordField.setBounds(188, 329, 295, 29);
		passWordField.setEchoChar('*');	
		panel.add(passWordField);		
		//show hide button for password
		showButton = new JButton("S");
		showButton.setBounds(480, 329, 45, 29);
		showButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		showButton.setBackground(Color.WHITE);
		// toggling between show and hide using showButton
		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(toggle) {
					passWordField.setEchoChar('*');
					toggle = false;
				}
				else {
					passWordField.setEchoChar((char)0);
					toggle = true;
				}
			}
			
		});		
		panel.add(showButton);
		// creating update button to update entered values in database
		updateButton = new JButton("Update");
		updateButton.setBounds(62, 476, 158, 37);
		contentPane.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					name = nameField.getText().trim();
					String NAME_REGEX = "^[a-zA-Z\\s]*$";
					Boolean b = name.matches(NAME_REGEX);				 
					if(!b || name.length()==0) {
						JOptionPane.showMessageDialog(contentPane, "1)Upto 20 characters includes only spaces and alphabets\n2)Should not be empty","Invalid Name",JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(name.length()>20) {
						JOptionPane.showMessageDialog(contentPane, "1)Upto 20 characters includes only spaces and alphabets\n2)Should not be empty","Invalid Name",JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(maleRadioButton.isSelected())
						gender = "Male";
					else if(femaleRadioButton.isSelected())
						gender = "Female";
					else
						gender = "Other";
					String a = ageField.getText();
					try {
						age = Integer.parseInt(a);
						try {
							if(age<18 || age>60) {
								throw new Exception();
							}
						}
						catch(Exception e1age) {
							JOptionPane.showMessageDialog(contentPane, "1)Age must be between 18 and 60 inclusive\n2)Should not be empty\n3)Must be number","Invalid Age",JOptionPane.ERROR_MESSAGE);							
							return;							
						}
					}
					catch(Exception eage) {
						JOptionPane.showMessageDialog(contentPane, "1)Age must be between 18 and 60 inclusive\n2)Should not be empty","Invalid Age",JOptionPane.ERROR_MESSAGE);
						return;						
					}
					try {
						Pattern p = Pattern.compile("[6-9][0-9]{9}");
						Matcher m = p.matcher(mobileNumberField.getText());				
						mobilenumber = Long.parseLong(mobileNumberField.getText());
						if(!(m.find() && m.group().equals(mobileNumberField.getText()))) {
							throw new Exception();
						}
						
					} catch (Exception ea) {
						JOptionPane.showMessageDialog(contentPane, "1)Only India numbers\n2)Should not be empty","Invalid Mobile Number",JOptionPane.ERROR_MESSAGE);						
						return;
					}
					email = emailField.getText();
					String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            				"[a-zA-Z0-9_+&*-]+)*@" + 
                            				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            				"A-Z]{2,7}$";
				    Boolean b1 = email.matches(EMAIL_REGEX);				 
					if(!b1) {
						JOptionPane.showMessageDialog(contentPane, "1)Valid email format\n2)Should not be empty","Invalid Email",JOptionPane.ERROR_MESSAGE);												
						return;
					}		   
					address = addressField.getText().trim();
					if(address.equals("")) {
						JOptionPane.showMessageDialog(contentPane, "1)Should not be empty","Invalid Address",JOptionPane.ERROR_MESSAGE);
						return;
					}				
					username = userNameField.getText().trim();
					password = passWordField.getText().trim();
					if(username.equals("")&&password.equals("")) {
						JOptionPane.showMessageDialog(contentPane, "Username and password field cannot be empty !");
						return;
					}
					String PASSWORD_REGEX="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,}$";
				    Boolean b2 = password.matches(PASSWORD_REGEX);				 
					if(!b2) {
						JOptionPane.showMessageDialog(contentPane, "1)At least 8 chars\n2)Contains at least one digit\n3)Contains at least one lower alpha char and one upper alpha char\n4)Contains at least one char within a set of special chars (@#%$^ etc.)\n5)Does not contain space, tab, etc.\n6)Should not be empty","Invalid Password",JOptionPane.ERROR_MESSAGE);												
						return;
					}	
					String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,}$";
				    Boolean b3 = username.matches(USERNAME_REGEX);				 
					if(!b3) {
						JOptionPane.showMessageDialog(contentPane, "1)At least 3 chars\n2)Valid characters: a-z, A-Z, 0-9, points, dashes and underscores\n","Invalid Username",JOptionPane.ERROR_MESSAGE);												
						return;
					}		
					db.connect();
					int id = db.getUserId(mobilenumber);
					if(id !=0) {
						JOptionPane.showMessageDialog(contentPane, "Accountant already exits with "+mobilenumber,"Sorry",JOptionPane.ERROR_MESSAGE);						
						return;						
					}					
					db.insertUser(name,gender,age,mobilenumber,email,address,username,password,0,0,0,2);		
				    JOptionPane.showMessageDialog(contentPane, "Accountant added successfully !");				    
				    
									    
				    ShowManagerFrame frame = new ShowManagerFrame(id,showManager);
				    frame.setVisible(true);
					dispose();
				} catch (Exception e1) {					
					e1.printStackTrace();
				}
			}
		});		
		
		// creating cancel button
		cancleButton = new JButton("Cancel");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    ShowManagerFrame frame = new ShowManagerFrame(id,showManager);
			    
			    frame.setVisible(true);
				dispose();
			}
		});
		cancleButton.setBounds(296, 476, 198, 37);
		contentPane.add(cancleButton);		
	}
	public static void main(String args[]) {
		new AccountantAdd(1,"Musthafa").setVisible(true);;
	}
}

