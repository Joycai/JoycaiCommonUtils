
import joycai.utils.time.DateTime;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class DateTimeTest {


    @Test
    public void testDateTime() {
        LocalDateTime ldt = DateTime.toLocalDateTime(new Date());
        System.out.println(ldt);
        DateTime.zeroDate(new Date());
        assert true;
    }
}
