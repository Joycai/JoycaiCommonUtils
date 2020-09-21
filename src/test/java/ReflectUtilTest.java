import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtilTest {

    @Test
    public void test() {
        Map<String, Object> srcMap = new HashMap<>();
        srcMap.put("name", "user");
        srcMap.put("age", 12);
        srcMap.put("value", 12.0);

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(srcMap, User.class);

        System.out.println(user.getName());
        System.out.println(user.getAge());
        System.out.println(user.getValue());
        assert true;
    }

    static class User{
        String name;
        int age;
        BigDecimal value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }
    }
}
