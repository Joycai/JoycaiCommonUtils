package joycai.utils.encrypt;

import com.google.common.base.Charsets;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

public class AESUtil {

    public static String genAESKey(int keySize, String seed) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed.getBytes(Charsets.UTF_8));
        kgen.init(keySize, random);
        SecretKey secretKey = kgen.generateKey();
        return Base64.toBase64String(secretKey.getEncoded());
    }

    public static AESHelper getNewAESHelper(byte[] key) {
        Security.addProvider(new BouncyCastleProvider());
        return new AESBase(new SecretKeySpec(key, "AES"));
    }

    public static AESHelper getNewAESHelper(String keyStr) {
        return getNewAESHelper(Base64.decode(keyStr));
    }

    /**
     * 微信的加密方式 AES-128-CBC，数据采用PKCS#7填充
     *
     * @param key 128/192/256 length
     * @param iv 128 length
     * @return
     */
    public static AESHelper getNewAESCBCHelper(byte[] key, byte[] iv) {
        Security.addProvider(new BouncyCastleProvider());
        return new AESCBCP7(new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
    }

    public static AESHelper getNewAESCBCHelper(String keyStr, String ivStr) {
        return getNewAESCBCHelper(Base64.decode(keyStr), Base64.decode(ivStr));
    }
}
