package joycai.utils.mt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

class MultiThreadContainerTest {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadContainerTest.class);

    volatile int counter = 0;

    private synchronized int getAndAdd() {
        return counter++;
    }

    @BeforeEach
    void init() {
        counter = 0;
    }

    @Test
    void multiThreadTest() {

        Assertions.assertDoesNotThrow(()->{
            MultiThreadContainer<TaskType, ResultType> mtc = new MultiThreadContainer<>(
                    Executors.newFixedThreadPool(25),
                    "Test",
                    (taskType)->{
                        ResultType ret = new ResultType();
                        ret.setMsg(taskType.getId()+" processed");
                        return ret;
                    }
            );

            mtc.setThreadNum(5);
            mtc.setBufferSize(10);

            mtc.registerTaskOffer(()->{
                TaskType taskType = new TaskType();
                taskType.setId(getAndAdd());
                return taskType;
            });

            mtc.registerResultProcessor((result)->{
                logger.info("result {} ",result.msg);
            });
            Thread.sleep(1000);
        });
    }
}