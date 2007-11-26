import java.net.*;
import java.io.*;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public class receiver {
	
	private static PrivateKey privateKey;
	private static PublicKey publicKey;
	
	static String cipherFile = "encrypted2.mp3";
	static String cleanFile = "fishing_the_sky_2.mp3";
	
	public static void main (String [] args ) throws IOException {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	
		try
		{
			KeyPairGenerator aKeyGen = KeyPairGenerator.getInstance("RSA");
			KeyPair aPair = aKeyGen.generateKeyPair();
			
			privateKey = aPair.getPrivate();
			publicKey = aPair.getPublic();
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	    int filesize=6022386; // filesize temporary hardcoded

	    long start = System.currentTimeMillis();
	    int bytesRead;
	    int current = 0;
	    
	    Socket sock = new Socket("localhost",13267);
	    System.out.println("Connecting...");
	    
	    OutputStream out = sock.getOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(publicKey);
	
		SecretKey aSecretKey = null;
		Cipher AEScipher = null;
		
		try
		{
			byte[] mykey = new byte[128];
			InputStream in = sock.getInputStream();
			DataInputStream objIn = new DataInputStream(in);
			in.read(mykey, 0, mykey.length);

			//System.out.println("Got Symmetric Key");
			
			for(int i=0;i<mykey.length; i++)
			{
				System.out.println(mykey[i]);
			}
			Cipher RSAcipher = Cipher.getInstance("RSA");
			RSAcipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] mydeckey = RSAcipher.doFinal(mykey);
			
			aSecretKey = new SecretKeySpec(mydeckey, "AES");
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	    // receive file
	    byte [] mybytearray  = new byte [filesize];
	    InputStream is = sock.getInputStream();
	    
	    FileOutputStream fos = new FileOutputStream(cipherFile);
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    bytesRead = is.read(mybytearray,0,mybytearray.length);
	    
	    current = bytesRead;
	    //System.out.println("Transferring...");

	    do {
	       bytesRead =
	          is.read(mybytearray, current, (mybytearray.length-current));
	       if(bytesRead >= 0) current += bytesRead;
	    } while(bytesRead > -1);
	    
	    //System.out.println(current);
	    bos.write(mybytearray, 0 , current);
	    long end = System.currentTimeMillis();
	    
	    System.out.println("Transfer complete.");
	    
	    bos.close();
	    sock.close();
	    
	    try
	    {
	    	byte[] block = new byte[8];
	    	
	    	AEScipher = Cipher.getInstance("AES");
	    	AEScipher.init(Cipher.DECRYPT_MODE, aSecretKey);
	    	
	    	File aCipherFile = new File(cipherFile);
	    	File aCleanFile = new File(cleanFile);
	    	FileInputStream fis = new FileInputStream(aCipherFile);
	    	FileOutputStream aFos = new FileOutputStream(aCleanFile);
	    	
	    	byte[] myencbytes = new byte[(int)aCipherFile.length()];
	    	
	    	int offset = 0;
	        int numRead = 0;

	        fis.read(myencbytes);
	        
	        fis.close();
	    	
	    	byte[] mybytes = AEScipher.doFinal(myencbytes);
	    	
	    	aFos.write(mybytes);
	    	aFos.flush();
	    	aFos.close();
	    	
	    	System.out.println("Decryption complete.");
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e.toString());
	    }

	}
}
