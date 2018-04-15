import java.io.*;

class My implements Runnable
{
	DataInputStream din;
	My(DataInputStream din)
	{
		this.din=din;
	}
	public void run()
	{
		String s2="";
		try
		{
			while(true)
			{
				s2=din.readUTF();
				DH.jta.append("\n"+s2);
				System.out.println(s2);
			}
		}
		catch(Exception ex)
		{
			DH.flag=1;
		}
	}
}