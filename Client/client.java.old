import java.net.*;
import java.io.*;

public class client
{
	public static void main(String[] args)
	{
		try{
			Socket aSocket = new Socket("137.99.129.59", 6013);

			InputStream inStream = aSocket.getInputStream();
			BufferedReader inBuffer = new
					BufferedReader(new InputStreamReader(inStream));

			String line;

			while( (line = inBuffer.readLine() ) != null)
			{
				System.out.println(line);
			}

			aSocket.close();

		}catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
