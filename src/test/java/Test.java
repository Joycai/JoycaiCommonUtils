import com.google.common.base.Charsets;
import joycai.utils.encrypt.AESCBC;

public class Test {

    @org.junit.Test
    public void testAESUtil() {
        String key = "sklhdflsjfsdgdeg";
        String iv = "cfbsdfgsdfxccvd1";
        String data = "你好，1234555";
        String base64Str = AESCBC.encrypt(data, key, iv);

        System.out.println(base64Str);

        byte[] deData = AESCBC.decrypt(base64Str, key, iv);

        System.out.println(new String(deData, Charsets.UTF_8));
        assert true;
    }
}
