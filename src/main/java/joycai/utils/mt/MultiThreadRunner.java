package joycai.utils.mt;

public class MultiThreadRunner<T, K> {

    TaskOffer<T> taskOffer;
    ResultHandler<K> resultHandler;
    TaskRunner<T, K> taskRunner;

    public void registerTaskOffer(TaskOffer<T> taskOffer) {
        this.taskOffer = taskOffer;
    }

    public void registerTaskHandler(ResultHandler<K> resultHandler) {
        this.resultHandler = resultHandler;
    }

    public void registerTaskRunner(TaskRunner<T, K> taskRunner) {
        this.taskRunner = taskRunner;
    }

    public interface TaskOffer<T> {
        T offerTask();
    }

    public static interface ResultHandler<K> {
        void processResult(K result);
    }

    public static interface TaskRunner<T, K> {
        K process(T task);
    }
}
