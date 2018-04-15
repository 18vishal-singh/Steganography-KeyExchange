import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;

class AES_LSB implements ActionListener
{
	JFrame f;
	static JTextArea jta;
	JTextField jtf1,jtf2;
	static JTextField jtf3,jtf4;
	JLabel jl1,jl2,jl3,jl4,jl5;
	JPanel jp1,jp2;
	JButton load_vfile,load_mfile;					// load_vfile is loading any format cover file
	JButton encode,decode,encrypt,decrypt;
	
	String plainText = new String();
	JFileChooser jfc,jfc1;

	public String encoderChoosedFile="";
    	public String decoderChoosedFile="";
	public String encoderMessage="";
    	public String decoderMessage="";

	String c_path="";						// path of the cover file

	byte[] cipher;
	JScrollPane scrollpane;

//*********************************************
/*
	jta-textarea of message
	jp1-panel for encoding/decoding
	jp2-panel for encryption/decryption
	
	jtf1-path for video file
	jtf2-path for msg file
	jtf3-output window in jp1 (static)
	jtf4-outpur window in jp2 (static)

	jl1-message
	jl2-encoding/decoding
	jl3-encryption/decryption
	jl4-path in jp1
	jl5-path in jp2
*/
//*********************************************

	AES_LSB()
	{
		f=new JFrame("AES_LSB");
		jfc1 =new JFileChooser("D:\\java" );
		jfc =new JFileChooser("D:\\java" );
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		jfc.setFileFilter(filter);
		
		jta=new JTextArea();
		jta.setSize(715,200);
		jta.setLocation(20,40);
		Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
		jta.setBorder(border);
		jta.setLineWrap(true);
		scrollpane=new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		

		jl1=new JLabel("<html><h3><u>MESSAGE :</u></h3></html>");
		jl1.setSize(120,30);
		jl1.setLocation(20,10);
		
		jp1=new JPanel();
		jp1.setSize(350,280);
		jp1.setLocation(20,260);
		Border border1 = BorderFactory.createLineBorder(Color.BLUE, 2);
		jp1.setBorder(border1);

		jp2=new JPanel();
		jp2.setSize(345,280);
		jp2.setLocation(390,260);
		Border border2 = BorderFactory.createLineBorder(Color.BLUE, 2);
		jp2.setBorder(border2);
		
		jl2=new JLabel("<html><h3><u>ENCODING / DECODING :</u></h3></html>");
		jl2.setSize(220,30);
		jl2.setLocation(90,15);

		load_vfile=new JButton("Load Cover File");
		load_vfile.setSize(150,30);
		load_vfile.setLocation(100,50);
		load_vfile.addActionListener(this);
		
		jl4=new JLabel("<html><h4>Path :</h4></html>");
		jl4.setSize(50,30);
		jl4.setLocation(20,95);

		jtf1=new JTextField();
		jtf1.setSize(260,30);
		jtf1.setLocation(70,95);
		jtf1.setEditable(false);
	
		encode=new JButton("ENCODE");
		encode.setSize(100,30);
		encode.setLocation(125,140);
		encode.addActionListener(this);
	
		decode=new JButton("DECODE");
		decode.setSize(100,30);
		decode.setLocation(125,185);
		decode.addActionListener(this);

		jtf3=new JTextField();
		jtf3.setSize(310,30);
		jtf3.setLocation(20,230);
		jtf3.setEditable(false);
		jtf3.setForeground(Color.RED);
	
		jp1.setLayout(null);
		jp1.add(jl2);
		jp1.add(load_vfile);
		jp1.add(jl4);
		jp1.add(jtf1);
		jp1.add(encode);
		jp1.add(decode);
		jp1.add(jtf3);

		jl3=new JLabel("<html><h3><u>ENCRYPTION / DECRYPTION :</u></h3></html>");
		jl3.setSize(220,30);
		jl3.setLocation(70,15);

		load_mfile=new JButton("Load Message File");
		load_mfile.setSize(150,30);
		load_mfile.setLocation(100,50);
		load_mfile.addActionListener(this);
		
		jl5=new JLabel("<html><h4>Path :</h4></html>");
		jl5.setSize(50,30);
		jl5.setLocation(20,95);

		jtf2=new JTextField();
		jtf2.setSize(255,30);
		jtf2.setLocation(70,95);
		jtf2.setEditable(false);
	
		encrypt=new JButton("ENCRYPT");
		encrypt.setSize(100,30);
		encrypt.setLocation(125,140);
		encrypt.addActionListener(this);
	
		decrypt=new JButton("DECRYPT");
		decrypt.setSize(100,30);
		decrypt.setLocation(125,185);
		decrypt.addActionListener(this);
		
		jtf4=new JTextField();
		jtf4.setSize(305,30);
		jtf4.setLocation(20,230);
		jtf4.setEditable(false);
		jtf4.setForeground(Color.RED);
	
		jp2.setLayout(null);
		jp2.add(jl3);
		jp2.add(load_mfile);
		jp2.add(jl5);
		jp2.add(jtf2);
		jp2.add(encrypt);
		jp2.add(decrypt);
		jp2.add(jtf4);

		f.add(jta);
		f.getContentPane().add(scrollpane);
		f.add(jl1);
		f.add(jp1);
		f.add(jp2);
		

		f.setLocation(280,80);
		f.setSize(760,580);
		f.setLayout(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
/////////////////////////////////////////////

	public void setEncoderChoosedFile(String encoderChoosedFile) 
	{
        	this.encoderChoosedFile = encoderChoosedFile;
    	}

    	public void setEncoderMessage(String encoderMessage) 
	{
        	this.encoderMessage = encoderMessage;
   	}

    	public String getEncoderChoosedFile() 
	{
        	return encoderChoosedFile;
    	}	

    	public String getEncoderMessage() 
	{
        	encoderMessage = jta.getText();
        	return encoderMessage;
    	}
    	public void setDecoderChoosedFile(String decoderChoosedFile) 
	{
        	this.decoderChoosedFile = decoderChoosedFile;
    	}

    	public void setDecoderMessage(String decoderMessage) 
	{
        	this.decoderMessage = decoderMessage;
//		jtf3.setText(decoderMessage);					// TESTING
        	jta.setText(decoderMessage);
    	}

    	public String getDecoderChoosedFile() 
	{
       		return decoderChoosedFile;
    	}

    	public String getDecoderMessage() 
	{
        	return decoderMessage;
    	}

/////////////////////////////////////////////
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==load_vfile)
		{
			jfc1.showOpenDialog(null);
			File myFile = jfc1.getSelectedFile();
			String filePath = jfc1.getSelectedFile().getPath();
			setEncoderChoosedFile(myFile.getAbsolutePath());
			setDecoderChoosedFile( myFile.getAbsolutePath());				
			jtf3.setText("Cover file loaded sucessfully");
			c_path=""+myFile.getParentFile()+"\\"+"new_"+myFile.getName();			// setting path of the new cover file
			System.out.println(c_path);
			jtf1.setText(filePath);
		
		}
	
		if(e.getSource()==load_mfile)
		{
			int r=jfc.showOpenDialog(null);
			if(r==JFileChooser.APPROVE_OPTION)
			{
				String filePath = jfc.getSelectedFile().getPath();
				try 
				{
					FileInputStream fr = new FileInputStream(filePath);
					InputStreamReader isr = new InputStreamReader(fr, "UTF-8");
					BufferedReader reader = new BufferedReader(isr);
					StringBuffer buffer = new StringBuffer();
					
					String line = null;
					while ((line = reader.readLine()) != null) 
					{
						buffer.append(line+"\n");
					}
		
					reader.close();

					jta.setText(buffer.toString());
					System.out.println(buffer);			// on consol (testing)
				}
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
				
				jtf2.setText(filePath);
				jtf4.setText("message loaded"); 
			}
		
		}

		if(e.getSource()==encode)
		{
			SecretLogic secretLogic = new SecretLogic();
			try 
			{
            			secretLogic.setSecretMessage(getEncoderMessage(), getEncoderChoosedFile(),c_path);
				jtf3.setText(" Message Encoded Sucessfully");
				jtf1.setText(c_path);
        		} 
			catch(Exception ex)
			{
				System.out.println(ex);
			}
		}

		if(e.getSource()==decode)
		{
			SecretLogic secretLogic = new SecretLogic();
			try 
			{
            			setDecoderMessage(secretLogic.getSecretMessage(getDecoderChoosedFile()));
				jtf3.setText(" Message Decoded Sucessfully");
        		} 
			catch(Exception ex)
			{
				System.out.println(ex);
			}
		}
		
		if(e.getSource()==encrypt)
		{
			plainText=jta.getText();
			int count=charCount(plainText);
		//	jtf4.setText(""+count);
			int c;
/*
	*NOTE : As messge length should be multiple of 16 as AES is fixed block size 
*/
			
			if(count==16)
				new Encryption(plainText);
			if(count<16 || count>16)
			{	
				if(count<16)
					c=16-count;
				else
				{
					c=count%16;
					c=16-c;
				}

				StringBuffer tempb=new StringBuffer("");
				for(int i=1;i<=c;i++)
				{
					tempb.append(" ");
				}
				String temp=tempb.toString();
				new Encryption(plainText+temp);
			//	int temp_count=charCount(plainText+temp);
			//	jtf4.setText(jtf4.getText()+"  "+c+" "+temp_count+" "+plainText+temp);
			}
		
		}
		
		if(e.getSource()==decrypt)
		{
			
			String temp =jta.getText()+" ";
		
			int[] i1=new int[5000];
			int j=0;
			for(int i=0;i<temp.length();i++)					// all this doing for converting hexadecimal code to byte array
			{
				String s1="";
				while(temp.charAt(i)!=' ')
				{
					s1=s1+temp.charAt(i);
					i++;
				}

				i1[j]=Integer.parseInt(s1);
				j++;
			}
			cipher=new byte[j];
			for(int k=0;k<j;k++)
			{
				cipher[k]=(byte)i1[k];
			}	
		
//******************************************************************************************
//			jta.append("\n");
//			for (int i=0; i<cipher.length; i++)
//       	 		AES_LSB.jta.append(new Integer(cipher[i])+" ");
			new Decryption(cipher);
		}
	}
	
	static int charCount(String s)				
	{
		int count=0;
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)!=' ' || s.charAt(i)==' ')
				count++;
		}
		return count;
	}
	
//////////////////////// main is for testing

	public static void main(String arg[])
	{
		new AES_LSB();
	}

}