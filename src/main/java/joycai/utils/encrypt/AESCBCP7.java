package joycai.utils.encrypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class AESCBCP7 extends AESBase {
    final protected IvParameterSpec iv;
    public AESCBCP7(SecretKeySpec sks, IvParameterSpec iv) {
        super(sks);
        this.iv = iv;
    }

    @Override
    protected Cipher initCipher(int cipherMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC");
        cipher.init(cipherMode, sks, iv);
        return cipher;
    }
}
