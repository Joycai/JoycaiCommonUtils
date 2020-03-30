import joycai.utils.mt.MultiThreadRunner;
import org.junit.Test;

public class MultiThreadTest {

    @Test
    public void testInit() {
        MultiThreadRunner<String, Integer> runner = new MultiThreadRunner<String, Integer>();
        runner.registerTaskRunner(task -> 1
        );
        assert true;
    }
}
