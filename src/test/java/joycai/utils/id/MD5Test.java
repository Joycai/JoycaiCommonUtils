package joycai.utils.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MD5Test {

    @Test
    void md5UtilTest() {
        String str = "AABBccdd111";
        Assertions.assertEquals("52504bb8206e85ca",MD5.hash16(str));
        Assertions.assertEquals("5619211b52504bb8206e85ca17017a51",MD5.hash(str));
    }
}
