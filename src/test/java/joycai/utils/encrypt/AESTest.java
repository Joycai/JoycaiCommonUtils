package joycai.utils.encrypt;

import com.google.common.base.Charsets;
import joycai.utils.encrypt.AESCBC;
import joycai.utils.encrypt.AESHelper;
import joycai.utils.encrypt.AESUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

class AESTest {

    @Test
    void testAESCBC() {
        String phoneInfo = "{\"phoneNumber\": \"13258787123\",\"purePhoneNumber\": \"13258787123\",\"countryCode\": \"86\",\"watermark\":{\"appid\":\"APPID\",\"timestamp\":1527238257}}";
        String baseInfo = "{\"openId\": \"openijfdskfjk\",\"nickName\": \"滑稽大人1\",\"gender\": 1,\"city\": \"SH\",\"province\": \"SH\",\"country\": \"CN\",\"avatarUrl\": \"http://111.com\",\"unionId\": \"UNIONID\",\"watermark\":{\"appid\":\"APPID\",\"timestamp\":1527237257}}";
        String key = "sklhdflsjfsdgdeg";
        String iv = "cfbsdfgsddddfxccvd1";
        String key64 = Base64.getEncoder().encodeToString(key.getBytes(Charsets.UTF_8));
        String iv64 = Base64.getEncoder().encodeToString(iv.getBytes(Charsets.UTF_8));

        String ranKey = AESUtil.genAESKey(256, key);
        String ranIV = AESUtil.genAESKey(128, iv);

        AESHelper helper = AESUtil.getNewAESCBCHelper(ranKey, ranIV);
        String encryptPhone = helper.encryptToString(phoneInfo.getBytes(Charsets.UTF_8));
        String decrypt = new String(helper.decryptFromString(encryptPhone), Charsets.UTF_8);

        Assertions.assertEquals(phoneInfo, decrypt);
    }

    @Test
    void testAESUtil() {
        String keyStr = "1234567890ABCDEFGGGG";
        String keyStr2 = "123456789";

        Assertions.assertDoesNotThrow(()->AESUtil.genAESKey(128, keyStr));
        Assertions.assertDoesNotThrow(()->AESUtil.genAESKey(256, keyStr));
        Assertions.assertDoesNotThrow(()->AESUtil.genAESKey(128, keyStr2));
        Assertions.assertDoesNotThrow(()->AESUtil.genAESKey(256, keyStr2));
    }
}
