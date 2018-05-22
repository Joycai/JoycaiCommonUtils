import com.google.common.base.Charsets;
import joycai.utils.encrypt.AESCBC;

import java.util.Base64;

public class Test {

    @org.junit.Test
    public void testAESUtil() {
        String key = "sklhdflsjfsdgdeg";
        String iv = "cfbsdfgsdfxccvd1";
        String data = "你好，1234555";


        String base64Str = AESCBC.encrypt(data,
                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));

        System.out.println(base64Str);

        byte[] deData = AESCBC.decrypt(base64Str,
                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));

        System.out.println(new String(deData, Charsets.UTF_8));
        assert true;
    }
}
