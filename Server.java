import java.net.*;
import java.io.*;

class Server
{	private int port;
	private File[] files;
	public Server(int port, File[] files)
	{	this.port = port;
		this.files = files;
	}
	public void start()
	{	try
		{	ServerSocket ss = new ServerSocket(port);
			for(int i = 0;i<files.length;i++)
			{	Socket s = ss.accept();
				Thread fileReceiverThread = new FileReceiverThread(s, files[i]);
				fileReceiverThread.start();
			}
		}
		catch(Exception e)
		{	System.out.println("Exception: " + e.toString());
		}
	}
}