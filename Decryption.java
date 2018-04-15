import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class Decryption
{
	
	static String IV = "AAAAAAAbAAA"+DH.encryptionKey2;
//	static String encryptionKey = "0123456789abcdef";
	static String encryptionKey = DH.encryptionKey1+DH.encryptionKey2;
	byte[] cipher;
	String decrypted="";

	Decryption(byte[] cipher)
	{
		this.cipher=cipher;

//////////////////////////////////
/*
		AES_LSB.jta.append("\n\n\n");
		for (int i=0; i<cipher.length; i++)
       	 		AES_LSB.jta.append(new Integer(cipher[i])+" ");

		AES_LSB.jta.append("\n");
*/
///////////////////////////

		try
		{
			decrypted = decrypt(cipher, encryptionKey);
			AES_LSB.jta.setText(decrypted.trim());
			AES_LSB.jtf4.setText("Sucessfully Decrypted");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception
	{
    		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    		cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    		return new String(cipher.doFinal(cipherText),"UTF-8");
  	}
}