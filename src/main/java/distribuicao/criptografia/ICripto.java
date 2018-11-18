package distribuicao.criptografia;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface ICripto {

	public byte[] encript(byte[] msg) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IOException, ClassNotFoundException;
	public byte[] decript(byte[] cripted) throws GeneralSecurityException, NoSuchPaddingException, IOException, ClassNotFoundException;
}