import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import javax.swing.border.Border;
import java.util.Random;
import java.math.*;


class DH implements ActionListener
{
	JFrame f;
	static JTextArea jta;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JLabel jl1,jl2,jl3,jl4,jl5,jl6,jl7;
	JButton connect,generate,broadcast,send,calculate,find_key;
	JButton next;
	String name;
	int[] set_p=new int[]{12301,12601,14149,13003,12799,13883,11969,15077,15551,18289,17419,20431};		// set of prime numbers (12 values)
	int[] set_q=new int[]{58,23,14,53,65,32,60,39,56,52,26,44};						// set of primitive root modulo of corresponding prime numbers
	
/*
	*NOTE: only 5 maximum 5 digit prime number are taken as more number of digit led to more computation, which require better processor
*/

	static String encryptionKey1 = "vishalsingh";				// 11 bit
	static String encryptionKey2 = "12345";					// need 5 bit more (default value)

	int prime,generator,pk;

//**************************************************
/*
	jta-textarea for chatting

	jtf1- entering p
	jtf2- entering q
	jtf3- entering private key
	jtf4- entering calculated
	jtf5- entering calculated received
	jtf6- contains final key

	jl1-BROADCAST
	jl2-P
	jl3-Q
	jl4-Private key
	jl5-calculated
	jl6-calculated received
	jl7-name of user

*/
//**************************************************



	Socket s;
	DataInputStream din;
	DataOutputStream dout;

	static int flag=1;				// not connected with the server, if connected it will be 0
	
	DH(String str)
	{
		f=new JFrame("Key Exchange by Deffi-Hellman");
		
		//name="vishal";
		name=str;

		jta=new JTextArea("Not Connected");
		jta.setSize(250,360);
		jta.setEditable(false);
		jta.setLocation(20,50);
		Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
		jta.setBorder(border);

		jl1=new JLabel("<html><h2>BROADCAST :</h2></html>",JLabel.CENTER);
		jl1.setLocation(20,10);
		jl1.setSize(250,30);

		connect=new JButton("CONNECT");
		connect.setLocation(100,425);
		connect.setSize(100,30);
		connect.addActionListener(this);
		
		generate=new JButton("GENERATE");
		generate.setLocation(300,90);
		generate.setSize(100,30);
		generate.addActionListener(this);
		
		jl2=new JLabel("<html><h4>(P)</h4></html>");
		jl2.setLocation(445,50);
		jl2.setSize(30,30);

		jl3=new JLabel("<html><h4>(Q)</h4></html>");
		jl3.setLocation(525,50);
		jl3.setSize(30,30);

		jtf1=new JTextField();
		jtf1.setSize(50,30);
		jtf1.setLocation(430,90);

		jtf2=new JTextField();
		jtf2.setSize(50,30);
		jtf2.setLocation(510,90);

		broadcast=new JButton("BROADCAST");
		broadcast.setLocation(590,90);
		broadcast.setSize(120,30);
		broadcast.addActionListener(this);

		jl4=new JLabel("<html><h4>Private Key :</h4></html>");
		jl4.setLocation(300,140);
		jl4.setSize(150,30);
	
		jtf3=new JTextField();
		jtf3.setSize(130,30);
		jtf3.setLocation(430,140);
		
		calculate=new JButton("CALCULATE");
		calculate.setLocation(590,140);
		calculate.setSize(120,30);
		calculate.addActionListener(this);

		jl5=new JLabel("<html><h4>Calculated :</h4></html>");
		jl5.setLocation(300,190);
		jl5.setSize(150,30);
		
		jtf4=new JTextField();
		jtf4.setSize(130,30);
		jtf4.setLocation(430,190);
		
		send=new JButton("SEND");
		send.setLocation(590,190);
		send.setSize(120,30);
		send.addActionListener(this);

		jl6=new JLabel("<html><h4>Calculated Received :</h4></html>");
		jl6.setLocation(300,240);
		jl6.setSize(150,30);
		
		jtf5=new JTextField();
		jtf5.setSize(130,30);
		jtf5.setLocation(430,240);

		find_key=new JButton("FIND KEY");
		find_key.setLocation(300,290);
		find_key.setSize(120,30);
		find_key.addActionListener(this);

		jtf6=new JTextField();
		jtf6.setSize(280,30);
		jtf6.setLocation(430,290);

		next=new JButton("NEXT");
		next.setSize(120,30);
		next.setLocation(590,390);
		next.addActionListener(this);

		jl7=new JLabel("<html><h4>Name : [ "+name+" ]</h4></html>");
		jl7.setLocation(590,20);
		jl7.setSize(150,30);


		f.add(jta);
		f.add(jl1);
		f.add(connect);
		f.add(generate);
		f.add(jl2);
		f.add(jl3);
		f.add(jtf1);
		f.add(jtf2);
		f.add(broadcast);
		f.add(jl4);
		f.add(jtf3);
		f.add(calculate);
		f.add(jl5);
		f.add(jtf4);
		f.add(send);
		f.add(jl6);
		f.add(jtf5);
		f.add(find_key);
		f.add(jtf6);
		f.add(next);
		f.add(jl7);
		

		f.setLocation(280,80);
		f.setSize(760,500);
		f.setLayout(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public static int random_int(int Min, int Max)
	{
     		return (int) (Math.random()*(Max-Min))+Min;
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==connect)
		{
			try
			{
				if(flag==1)
				{
					flag=0;
					jta.setText("");
					s=new Socket("192.168.43.96",3000);
					System.out.println(s);
					din=new DataInputStream(s.getInputStream());
					dout=new DataOutputStream(s.getOutputStream());
					String st;
					st=name+"*********CONNECTED*********";
					dout.writeUTF(st);
					dout.flush();
					My m=new My(din);
					Thread t1=new Thread(m);
					t1.start();
						
						
				}
				else
					jta.append("\n*****YOU ARE ALREADY CONNECTED*****");
					
		   	}
			catch(Exception ex)
			{
					jta.append("\n"+"SERVER DOWN");
					flag=1;
			}
		}

		if(e.getSource()==generate)
		{
			int rn=random_int(0, 12);
			System.out.println(rn);
			jtf1.setText(""+set_p[rn]);
			jtf2.setText(""+set_q[rn]);

			prime=set_p[rn];
			generator=set_q[rn];
	
		}
		
		if(e.getSource()==broadcast)
		{
			
			try
			{
			String p="";				// prime number
			String q="";				// corresponding primitive root modulo (generator)
			p=jtf1.getText();
			q=jtf2.getText();
			
			prime=Integer.parseInt(p);
			generator=Integer.parseInt(q);
			
			
			dout.writeUTF(name+" :->\np="+p+"\nq="+q);
			dout.flush();
			}
			catch(Exception ex)
			{
				jta.append("\n"+"SORRY......"+"\n"+"PLEASE CONNECT AGAIN THEN SEND AGAIN"); 
				flag=1;
			}
	
		}

		if(e.getSource()==calculate)
		{
			try
			{
			String st=jtf3.getText();
			pk=Integer.parseInt(st);				// pk is private key of individual user
			
			prime=Integer.parseInt(jtf1.getText());
			generator=Integer.parseInt(jtf2.getText());
			
			BigInteger bip=new BigInteger(String.valueOf(prime));
			BigInteger big=new BigInteger(String.valueOf(generator));

			BigInteger bi1=big.pow(pk);
			BigInteger cal=bi1.remainder(bip);
			jtf4.setText(""+cal);
			}
			catch(Exception ex)
			{
				jtf3.setText("Enter Private Key");
			}
		}
	
		if(e.getSource()==send)
		{
			String st;
			st=jtf4.getText();
			try
			{
				dout.writeUTF(name+" :->\ncalculated="+st);
				dout.flush();
			}
			catch(Exception ex)
			{
				jta.append("\n"+"SORRY......"+"\n"+"PLEASE CONNECT AGAIN THEN SEND AGAIN"); 
				flag=1;
			}
	
		}

		if(e.getSource()==find_key)
		{
		   try
		   {
			int rec=Integer.parseInt(jtf5.getText());
			BigInteger bi_rec=new BigInteger(String.valueOf(rec));
			BigInteger bip=new BigInteger(String.valueOf(prime));
			BigInteger bi1=bi_rec.pow(pk);
			BigInteger key=bi1.remainder(bip);
			jtf6.setText(""+key);
			String final_key=""+key;
			if(final_key.length()<5)
			{
				if(final_key.length()==1)
					encryptionKey2=final_key+"1234";
				if(final_key.length()==2)
					encryptionKey2=final_key+"123";
				if(final_key.length()==3)
					encryptionKey2=final_key+"12";
				if(final_key.length()==4)
					encryptionKey2=final_key+"1";
			}
			if(final_key.length()==5)
				encryptionKey2=""+final_key;
			
			}
		   catch(Throwable ex)
		   {
			jtf6.setText(" Something is wrong...? Key not generated..!!");	
		   }
	
		}
	
		if(e.getSource()==next)
		{
			if( encryptionKey2.equals("12345"))
			{
				JOptionPane.showMessageDialog(null," Their is no key exchange happens.\nSo taking default key set by software");	
			}
			new AES_LSB();
			f.setVisible(false);
		//	jtf6.setText(encryptionKey1+encryptionKey2);			//testing
		}
	}


//////////////////////// main is for testing

	public static void main(String arg[])
	{
		new DH("testing");
	}


}