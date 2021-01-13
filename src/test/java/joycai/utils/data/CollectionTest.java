package joycai.utils.data;


import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CollectionTest {

    @Test
    void notNullOrEmptyTest() {
        Assertions.assertEquals(Boolean.TRUE, Collection.notNullOrEmpty(Lists.newArrayList(1)));
        Assertions.assertEquals(Boolean.FALSE, Collection.notNullOrEmpty(Lists.newArrayList()));
    }
}
