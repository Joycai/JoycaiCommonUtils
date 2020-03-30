package joycai.utils.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AESBase implements AESHelper {

    final protected SecretKeySpec sks;

    public AESBase( SecretKeySpec sks) {
        this.sks = sks;
    }

    protected Cipher initCipher(int cipherMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipherMode,sks);
        return cipher;
    }

    @Override
    public byte[] encrypt(byte[] content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        return initCipher(Cipher.ENCRYPT_MODE).doFinal(content);
    }

    @Override
    public byte[] decrypt(byte[] content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException {
        return initCipher(Cipher.DECRYPT_MODE).doFinal(content);
    }
}
