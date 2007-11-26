import java.net.*;
import java.io.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;

public class sender 
{
	
	public static void main (String [] args ) throws IOException 
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		
		ServerSocket servsock = new ServerSocket(13267);
		PublicKey key = null;
		
		String cleartextFile = "fishing_the_sky.mp3";
		String ciphertextFile = "encrypted.mp3";
	
		while (true) 
		{
			System.out.println("Waiting...");
			
			Socket sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);
			
			try
			{
				InputStream in = sock.getInputStream();
				ObjectInputStream objIn = new ObjectInputStream(in);
				key = (PublicKey)objIn.readObject();
				
				//System.out.println(key.toString());
				//System.out.println("Got Key");
			}
			catch(Exception e)
			{}
			
			Cipher RSAcipher = null;
			SecretKey secretKey = null;
			Cipher AEScipher = null;
			
			try
			{
				RSAcipher = Cipher.getInstance("RSA");
				
				RSAcipher.init(Cipher.ENCRYPT_MODE, key);
				KeyGenerator aGen = KeyGenerator.getInstance("AES");
				aGen.init(128);
				secretKey = aGen.generateKey();
				
				byte[] aEncodedKey = RSAcipher.doFinal(secretKey.getEncoded());
				
				for(int i=0;i<aEncodedKey.length; i++)
				{
					System.out.println(aEncodedKey[i]);
				}
				
				OutputStream os = sock.getOutputStream();
				DataOutputStream aObjOut = new DataOutputStream(os);
			    
				//System.out.println("Sending encrypted symmetric key...");
			    
				aObjOut.write(aEncodedKey,0,aEncodedKey.length);
			    aObjOut.flush();
			      
			    AEScipher = Cipher.getInstance("AES");
			    AEScipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
			
			FileInputStream fis = null;
			FileOutputStream fos = null;
			CipherOutputStream cos = null;

			byte[] block = new byte[8];
			
			try {
				fis = new FileInputStream(cleartextFile);
				fos = new FileOutputStream(ciphertextFile);
				cos = new CipherOutputStream(fos, AEScipher);
				
				//System.out.print("Encrypting file: \"" + cleartextFile + "\"\n");
				//System.out.print("Writing to file: \"" + ciphertextFile + "\"\n\n");
				
				int i = 0;
			
				while ((i = fis.read(block)) != -1) {
					cos.write(block, 0, i);
				}
				cos.close();
				
				//System.out.println("...encrypting done!\n\n");
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			
		      File aFile = new File (ciphertextFile);
		      byte [] mybytearray  = new byte [(int)aFile.length()];
		      
		      FileInputStream aFis = new FileInputStream(aFile);
		      BufferedInputStream bis = new BufferedInputStream(aFis);
		      bis.read(mybytearray,0,mybytearray.length);
		      
		      OutputStream os = sock.getOutputStream();
		      //System.out.println("Sending...");
		      os.write(mybytearray,0,mybytearray.length);
		      os.flush();
		      
		      sock.close();
		}
	}
}