import java.io.*;
import java.net.*;

class Client
{	private int port;
	private String host;
	private File[] files;
	public Client(int port, File[] files)
	{	this.port = port;
		this.files = files;

		System.out.print("Enter address of host to connect: ");
		try
		{	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			this.host = br.readLine();
			br.close();
		}
		catch(Exception e)
		{	System.out.println("Exception: " + e.toString());
			System.exit(0);
		}
	}
	public void start()
	{	try
		{	for(int i = 0;i<files.length;i++)
			{	Socket s = new Socket(host, port);
				Thread fileSenderThread = new FileSenderThread(s, files[i]);
				fileSenderThread.start();
			}
		}
		catch(Exception e)
		{	System.out.println("Exception: " + e.toString());
		}
	}
}