package joycai.utils.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

class DateTimeTest {

    @Test
    void testDateTime() {
        Assertions.assertDoesNotThrow(() -> DateTime.nowChina());
        Assertions.assertDoesNotThrow(() -> DateTime.toLocalDateTime(new Date()));
        Assertions.assertDoesNotThrow(() -> DateTime.toLocalDateTime(new Date(), ZoneId.systemDefault()));
        Assertions.assertDoesNotThrow(() -> DateTime.zeroDate(new Date()));
        Assertions.assertDoesNotThrow(() -> DateTime.zeroDate(new Date(), ZoneId.systemDefault()));

        Date day1 = Date.from(LocalDate.of(2020, 11, 30).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date day2 = Date.from(LocalDate.of(2020, 12, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date day3 = Date.from(LocalDate.of(2020, 12, 2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date day4 = Date.from(LocalDate.of(2020, 12, 3).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date day5 = Date.from(LocalDate.of(2020, 12, 4).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date day6 = Date.from(LocalDate.of(2020, 12, 5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date day7 = Date.from(LocalDate.of(2020, 12, 6).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        Assertions.assertEquals(Boolean.TRUE,DateTime.workDay(day1));
        Assertions.assertEquals(Boolean.TRUE,DateTime.workDay(day2));
        Assertions.assertEquals(Boolean.TRUE,DateTime.workDay(day3));
        Assertions.assertEquals(Boolean.TRUE,DateTime.workDay(day4));
        Assertions.assertEquals(Boolean.TRUE,DateTime.workDay(day5));
        Assertions.assertEquals(Boolean.FALSE,DateTime.workDay(day6));
        Assertions.assertEquals(Boolean.FALSE,DateTime.workDay(day7));
    }
}
