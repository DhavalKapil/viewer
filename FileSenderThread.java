import java.io.*;
import java.net.*;
import java.security.*;

class FileSenderThread extends Thread
{	private Socket s;
	private File file;

	private BufferedReader br;
	private PrintWriter pw;

	public FileSenderThread(Socket s, File file)
	throws IOException
	{	this.s = s;
		this.file = file;

		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.pw = new PrintWriter(s.getOutputStream());

		System.out.println("Sending file " + file.getName());
	}

	private String getFileChecksum()
	throws IOException, NoSuchAlgorithmException
	{	InputStream in = new FileInputStream(file.getAbsolutePath());
		
		byte[] buffer = new byte[1024];
		int bytes_read;		

		MessageDigest complete = MessageDigest.getInstance("MD5");
		
		do
		{	bytes_read = in.read(buffer);
			if(bytes_read>0)
				complete.update(buffer, 0, bytes_read);
		}while(bytes_read!=-1);
		
		in.close();

		byte[] b = complete.digest();

		String result = "";
		for(int i = 0;i<b.length;i++)
			result += Integer.toString( (b[i]&0xff) + 0x100, 16).substring(1);
		
		return result;
	}

	public void run()
	{	try
		{	String prevChecksum = "";
			while(true)
			{	String currentChecksum = getFileChecksum();
				if(currentChecksum.equals(prevChecksum))
				{	Thread.sleep(1000);
					continue;
				}
				BufferedReader fileReader = new BufferedReader(new FileReader(file));
				String loop;
				pw.println(Config.FILE_START);
				while((loop=fileReader.readLine())!=null)
				{	pw.println(loop);
				}
				pw.println(Config.FILE_END);
				pw.flush();
				fileReader.close();
				prevChecksum = currentChecksum;
			}
		}
		catch(Exception e)
		{	if(file.exists())
				System.out.println("Exception: " + e.toString());
			else
				run();
		}
	}
}
