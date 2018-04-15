/*

	***** starting file *****
*/


import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;


class Stegano
{
	JFrame f;
	JLabel jl1,jl2,jl3;						// jl1 for second box and jl2 for first box
	JButton enter;
	JTextField jtf;
	String name;

	Stegano()
	{
		try
		{
			InetAddress addr = InetAddress.getLocalHost();			
			name = addr.getHostName();				// getting name of local host
		}
		catch(Exception ex)
		{	
			name="Enter here";
		}	

		f=new JFrame("Final Year Project");
		
		jl2=new JLabel("<html><center><h1>VIDEO STEGANOGRAPHY</h1></center></html>",JLabel.CENTER);
		jl2.setSize(600,60);
		jl2.setLocation(100,20);
		Border border1 = BorderFactory.createLineBorder(Color.BLUE, 2);
		jl2.setBorder(border1);
		
		jl1=new JLabel("<html><center><h1><u>FINAL YEAR PROJECT</u></h1><br><h2><u>Made By</u>:<br>Vishal Singh (121)<br>Shivam Saxena (099)<br>Charu Bhatt (904)</h2></center></html>",JLabel.CENTER);
		jl1.setSize(600,300);
		jl1.setLocation(100,100);
		Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
		jl1.setBorder(border);
	
		jl3=new JLabel("Enter your name : ");
		jl3.setSize(100,30);
		jl3.setLocation(245,420);

		jtf=new JTextField(name);
		jtf.setSize(130,30);
		jtf.setLocation(340,420);
		

		enter= new JButton("ENTER");
		enter.setSize(80,30);
		enter.setLocation(480,420);
		enter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				name=jtf.getText();
				System.out.println(name);
				new DH(name);
				f.setVisible(false);
			//	JOptionPane.showMessageDialog(null,"Testing");
			}
		});
		
		f.add(enter);
		f.add(jl1);
		f.add(jl2);
		f.add(jl3);
		f.add(jtf);
	

		f.setLocation(280,80);
		f.setSize(800,500);
		f.setLayout(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}

	public static void main(String args[])
	{
		try 
		{
            		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        	} 
		catch (Exception exc) 
		{
            		System.err.println("Error loading L&F: " + exc);
        	}
		new Stegano();
	}		
	

}