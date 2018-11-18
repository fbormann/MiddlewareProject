package distribuicao.criptografia;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import distribuicao.message.Message;

public class Cripto implements ICripto {
	
	private String key = "marflksdomnhtklq"; // Usando chave de 128-bits (16 bytes)
	
	public byte[] getKey(String myKey){
        MessageDigest sha = null;
        byte[] key=null;
        try {
        	key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return key;
}
	
	public byte[] encript(byte[] msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, IOException, ClassNotFoundException {

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		byte[] keyBytes = getKey(key);

		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
		byte[] encrypted = cipher.doFinal(msg);
		return encrypted;
	}

	public byte[] decript(byte[] cripted) throws GeneralSecurityException, NoSuchPaddingException, IOException, ClassNotFoundException {

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] keyBytes = getKey(key);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
		
		byte[] decrypted = cipher.doFinal(cripted);
		
		return decrypted;
	}
	
	public static void main(String args[]) {
		
		try {
			Cripto cripto = new Cripto();
			String oi = "oi";
			ArrayList<Object> array = new ArrayList<>();
			array.add(oi);
			Message msg = new Message(0, "add", array, null);
			Marshaller mar = new Marshaller();
			
			System.out.println("com marshaller com message");
			byte[] cripted = cripto.encript(mar.marshall(msg));
			String out = (String) ((Message)mar.unmarshall(cripto.decript(cripted))).getParameters().get(0);
			System.out.println(out);
		} catch (ClassNotFoundException | GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}