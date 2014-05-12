import java.io.*;

public class Viewer
{	public static void main(String[] args)
	{	if(args.length<3)
			error();
		File[] filesList = new File[args.length - 2];
		for(int i = 0;i<filesList.length;i++)
			filesList[i] = new File(args[i+2]);
		if(args[0].equals("server"))
			new Server(Integer.parseInt(args[1]), filesList).start();
		else if(args[0].equals("client"))
			new Client(Integer.parseInt(args[1]), filesList).start();
		else
			error();
	}

	private static void error()
	{	System.out.println("Viewer program");
		System.out.println("Usage:");
		System.out.println("java Viewer <server/client> <port> <file lists>");
		System.exit(0);
	}
}