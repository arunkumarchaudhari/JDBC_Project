package com.arun.guiapplication;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ScrollFrameTest extends JFrame implements ActionListener,WindowListener{
	
	private static final String GET_STUDENTS_QUERY ="SELECT ROLLNO, SNAME, SADD, MARKS FROM STUDENT";
	
	private JLabel lsno,lsname,laddrs,lavg;
	private JTextField tsno,tsname,taddrs,tavg;
	private JButton bFirst,bLast,bPrev,bNext;
	private Connection con=null;
	private PreparedStatement ps =null;
	private ResultSet rs=null;
	
	
	public ScrollFrameTest() {
		setTitle("Student Details");
		setSize(300,200);
		setLayout(new FlowLayout());
		
		//adding components
		lsno=new JLabel("RollNo");
		add(lsno);
		tsno=new JTextField(10);
		add(tsno);
		
		lsname=new JLabel("Name");
		add(lsname);
		tsname=new JTextField(10);
		add(tsname);
		
		laddrs=new JLabel("Address");
		add(laddrs);
		taddrs=new JTextField(10);
		add(taddrs);
		
		lavg=new JLabel("Avg");
		add(lavg);
		tavg=new JTextField(10);
		add(tavg);
		
		bFirst =new JButton("Fist");
		bLast =new JButton("Last");
		bPrev =new JButton("Prev");
		bNext =new JButton("Next");
		add(bFirst); add(bLast); add(bPrev); add(bNext);
		
		
		//register and activate ActionListener for all 4 button
		bFirst.addActionListener(this);
		bNext.addActionListener(this);
		bPrev.addActionListener(this);
		bLast.addActionListener(this);
		
		//Add windowListener to frame
		this.addWindowListener(this);
		//frame                //windowListener
		//disable editing on textbox
		
		tsno.setEditable(false);
		tsname.setEditable(false);
		taddrs.setEditable(false);
		tavg.setEditable(false);
		
		setVisible(true);
		
		//when we click on crossmark button, current running 
		//application should be terminated.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeJDBC();
		
	}
	
	private void initializeJDBC() {
		try {
			//establish the connectin
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","arunkc9900");
			
			//Create Prepared statement object
			if(con!=null)
				ps=con.prepareStatement(GET_STUDENTS_QUERY,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			//Prepare Scrollable RS object
			if(ps!=null)
				rs=ps.executeQuery();
			
			
			
			
		}//end of try block
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//initialized jdbc (end)
	
	
	public static void main(String[] args) {
		new ScrollFrameTest(); //anonymous objects( as no reference)
		
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		boolean flag=false;
		if(ae.getSource()==bFirst) {
			try {
				rs.first();
				flag=true;
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
			System.out.println("First Button is clicked...");
		}
		
		else if(ae.getSource()==bNext) {
			try {
				if(!rs.isLast()) {
					rs.next();
					flag=true;
				}
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
			System.out.println("Next Button is clicked...");
		}
		else if(ae.getSource()==bPrev) {
			try {
				if(!rs.isFirst()) {
					rs.previous();
					flag=true;
				}
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			System.out.println("Prev Button is clicked...");
		}
		else {
			try {
				rs.last();
				flag=true;
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			System.out.println("Last Button is clicked...");
		}
		
		if(flag)  {
			try{
				tsno.setText(rs.getString(1));
				tsname.setText(rs.getString(2));
				taddrs.setText(rs.getString(3));
				tavg.setText(rs.getString(4));
			}
		
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		//To close JDBC object...
		try {
			if(rs!=null)
				rs.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		try {
			if(ps!=null)
				ps.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		try {
			if(con!=null)
				con.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

