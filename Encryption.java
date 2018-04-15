import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;					//******** NOTE : IV is also different by DH key  *********
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class Encryption
{
	static String IV = "AAAAAAAbAAA"+DH.encryptionKey2;			//IV is intialization vector (its size should be equal to block size i.e 16bytes)
										//IV and salt helps in distingues the cipher text for the same password.
	static String plainText;
//	static String encryptionKey = "0123456789abcdef";
	static String encryptionKey = DH.encryptionKey1+DH.encryptionKey2;	//This is 16byte(128-bit) key
	static byte[] cipher;

	Encryption(String plainText)
	{
		this.plainText=plainText;
		try
		{
			cipher = encrypt(plainText, encryptionKey);
			AES_LSB.jta.setText("");
			for (int i=0; i<cipher.length-1; i++)
       	 			AES_LSB.jta.append(new Integer(cipher[i])+" ");
			AES_LSB.jta.append(""+new Integer(cipher[cipher.length-1]));

			AES_LSB.jtf4.setText("Encryption done sucessfully ");	// +cipher.length);
		}
		catch(Exception e) 
    		{
      			AES_LSB.jtf4.setText("Something wrong happens *SORRY* ");
    		} 
	}

	public static byte[] encrypt(String plainText, String encryptionKey) throws Exception 
  	{
   		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    		cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    		return cipher.doFinal(plainText.getBytes("UTF-8"));
  	}

}
