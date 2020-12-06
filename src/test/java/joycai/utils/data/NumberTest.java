package joycai.utils.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void get3BitDecimalTest() {
        float f1 = 1.9999f;
        float f2 = 1.0116f;
        float f3 = 1.0111f;
        float f4 = 0.9999f;
        Assertions.assertEquals(2.000d,Number.get3BitDecimal(f1).doubleValue());
        Assertions.assertEquals(1.012d,Number.get3BitDecimal(f2).doubleValue());
        Assertions.assertEquals(1.011d,Number.get3BitDecimal(f3).doubleValue());
        Assertions.assertEquals(1.000d,Number.get3BitDecimal(f4).doubleValue());
    }

    @Test
    void get2BitDecimalTest() {
        float f1 = 1.999f;
        float f2 = 1.016f;
        float f3 = 1.011f;
        float f4 = 0.999f;
        Assertions.assertEquals(2.00d,Number.get2BitDecimal(f1).doubleValue());
        Assertions.assertEquals(1.02d,Number.get2BitDecimal(f2).doubleValue());
        Assertions.assertEquals(1.01d,Number.get2BitDecimal(f3).doubleValue());
        Assertions.assertEquals(1.00d,Number.get2BitDecimal(f4).doubleValue());
    }
}
