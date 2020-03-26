import com.google.common.base.Charsets;
import joycai.utils.encrypt.AESCBC;
import joycai.utils.encrypt.AESHelper;
import joycai.utils.encrypt.AESUtil;
import org.junit.Test;

import java.util.Base64;

public class AESTest {


    @Test
    public void testAESCBC() {
        String phoneInfo = "{\"phoneNumber\": \"13258787123\",\"purePhoneNumber\": \"13258787123\",\"countryCode\": \"86\",\"watermark\":{\"appid\":\"APPID\",\"timestamp\":1527238257}}";
        String baseInfo = "{\"openId\": \"openijfdskfjk\",\"nickName\": \"滑稽大人1\",\"gender\": 1,\"city\": \"SH\",\"province\": \"SH\",\"country\": \"CN\",\"avatarUrl\": \"http://111.com\",\"unionId\": \"UNIONID\",\"watermark\":{\"appid\":\"APPID\",\"timestamp\":1527237257}}";
        String key = "sklhdflsjfsdgdeg";
        String iv = "cfbsdfgsddddfxccvd1";
        String key64 = Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8));
        String iv64 = Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8));

        String ranKey = AESUtil.genAESKey(256, key);
        String ranIV = AESUtil.genAESKey(128, iv);

//        String data = "你好，1234555";

//        String phoneBase64Str = AESCBC.encrypt(phoneInfo,
//                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
//                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));
//        String base64Str = AESCBC.encrypt(baseInfo,
//                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
//                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));
//        System.out.println(phoneBase64Str);
//        System.out.println(base64Str);
//
//        byte[] deData = AESCBC.decrypt(base64Str,
//                Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8)),
//                Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8)));
//
//        System.out.println(new String(deData, Charsets.UTF_8));

        AESHelper helper = AESUtil.getNewAESCBCHelper(ranKey, ranIV);
        String encryptPhone = helper.encryptToString(phoneInfo.getBytes(Charsets.UTF_8));
        System.out.println(encryptPhone);
        String decrypt = new String(helper.decryptFromString(encryptPhone), Charsets.UTF_8);
        System.out.println(decrypt);

        assert decrypt.equals(phoneInfo);
    }

    @Test
    public void testAESUtil() {
        String keyStr = "1234567890ABCDEFGGGG";
        String keyStr2 = "123456789";
        String key1 = AESUtil.genAESKey(128, keyStr);
        String key1_1 = AESUtil.genAESKey(256, keyStr);

        String key2 = AESUtil.genAESKey(128, keyStr2);

        System.out.println(key1);
        System.out.println(key1_1);
        System.out.println(key2);
        assert true;
    }
}
