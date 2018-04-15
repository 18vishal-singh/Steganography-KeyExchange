import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;



public class SecretLogic {
    private String secretMessage="";
    FileInputStream inputFile;
    int read;
    int messageLenghtCounter = 0;
    
    
    public String getSecretMessage(String filePath) throws IOException
    {
            setMessageLength(filePath);
        try {
                inputFile = new FileInputStream(filePath);
                inputFile.skip(inputFile.available()-messageLenghtCounter);     // pointer reach to the end of buffer file and starting of secret message       

                for(int i=0; i<messageLenghtCounter;i++)
                {
                    read = inputFile.read();
                    secretMessage += (char) decoder(read);
                }
            } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(SecretLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretMessage;
    }
    
    public void setSecretMessage(String message, String filePath,String c_path) throws FileNotFoundException, IOException
    {
           inputFile = new FileInputStream(filePath);
           String msg="";

           msg+='|'; 							//(int)Indication to starting the message
           for(int i=0; i<message.length();i++)
           {
                msg+=(char)encoder((int)message.charAt(i));
           }
	 							//		AES_LSB.jta.setText(msg);	
	
	   FileInputStream from=new FileInputStream(filePath);
	   FileOutputStream to=new FileOutputStream(c_path);
	   byte []buffer=new byte[4096];
	   int byteRead;
	   while((byteRead=from.read(buffer))!=-1)
	   {
		to.write(buffer,0,byteRead);
	   }

           PrintWriter bw = new PrintWriter(new FileWriter(c_path, true));
           bw.write(msg);
           bw.close();
    }
    
/*
	*NOTE : encoder() and decoder() function just shift the real message assci value
*/
    public int decoder(int encodedChar)
    {
        int value = encodedChar - 10;
        while(value < 0)
            value += 128;
        return value;
    }
    
    public int encoder(int asciiOfChar)
    {
        int value = asciiOfChar + 10;
        while(value > 127)
            value -= 128;
        return value;
    }
    
    public void  setMessageLength(String filePath) throws FileNotFoundException, IOException 
    {
        inputFile = new FileInputStream(filePath);
        inputFile.skip(inputFile.available()-1);							// reahing end of file
        read = inputFile.read();
        
        while(read != 124)//special character ! ascii // Still back till find the begining of the message
                {
                    messageLenghtCounter++;
                    inputFile.close();
                    
                    inputFile = new FileInputStream(filePath);
                    inputFile.skip(inputFile.available() - messageLenghtCounter -1); 			//Read before the last readed character 
                    read = inputFile.read();
                }
                inputFile.close();
                                
    }
}
