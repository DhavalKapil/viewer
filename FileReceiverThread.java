import java.io.*;
import java.net.*;

class FileReceiverThread extends Thread
{	private Socket s;
	private File file;

	private BufferedReader br;
	private PrintWriter pw;

	public FileReceiverThread(Socket s, File file)
	throws IOException
	{	this.s = s;
		this.file = file;

		this.pw = new PrintWriter(s.getOutputStream());
		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));

		System.out.println("Receiving file: " + file.getName());
	}

	public void run()
	{	try
		{	while(true)
			{	String loop;
				boolean first = true;
				loop = br.readLine();	// File is ready to be sent from the sender's end
				PrintWriter fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				while(true)
				{	loop = br.readLine();
					if(loop.equals(Config.FILE_END))
						break;
					if(!first)
						fileWriter.println();
					fileWriter.print(loop);
					first = false;
				}
				fileWriter.close();
			}
		}
		catch(Exception e)
		{	System.out.println("Exception: " + e.toString());
		}
	}
}