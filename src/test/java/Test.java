import com.google.common.base.Charsets;
import joycai.utils.encrypt.AESCBC;

import java.util.Base64;

public class Test {

    String phoneInfo = "{\"phoneNumber\": \"13258787123\",\"purePhoneNumber\": \"13258787123\",\"countryCode\": \"86\",\"watermark\":{\"appid\":\"APPID\",\"timestamp\":1527238257}}";

    String baseInfo = "{\"openId\": \"openijfdskfjk\",\"nickName\": \"滑稽大人1\",\"gender\": 1,\"city\": \"SH\",\"province\": \"SH\",\"country\": \"CN\",\"avatarUrl\": \"http://111.com\",\"unionId\": \"UNIONID\",\"watermark\":{\"appid\":\"APPID\",\"timestamp\":1527237257}}";

    @org.junit.Test
    public void testAESUtil() {
        String key = "sklhdflsjfsdgdeg";
        String iv = "cfbsdfgsdfxccvd1";
//        String data = "你好，1234555";


        String phoneBase64Str = AESCBC.encrypt(phoneInfo,
                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));
        String base64Str = AESCBC.encrypt(baseInfo,
                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));
        System.out.println(phoneBase64Str);
        System.out.println(base64Str);

        byte[] deData = AESCBC.decrypt(base64Str,
                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));

        System.out.println(new String(deData, Charsets.UTF_8));
        assert true;
    }
}
