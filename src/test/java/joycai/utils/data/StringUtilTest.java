package joycai.utils.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    void getSiteDomainTest() {
        assertEquals("joycai",StringUtil.getSiteDomain("http://www.joycai.cn"));
        assertEquals("joycai",StringUtil.getSiteDomain("https://www.joycai.cn"));
        assertEquals(null,StringUtil.getSiteDomain("www.joycai.cn"));
    }
}
