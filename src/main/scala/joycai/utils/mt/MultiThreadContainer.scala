package joycai.utils.mt

import java.util.concurrent.{ConcurrentLinkedQueue, ExecutorService}

/**
  * 多线程容器
  *
  * @param executorService 线程池
  * @param name            名称
  * @param taskProcessor   任务处理函数
  * @tparam T 任务类型
  * @tparam R 结果类型
  */
class MultiThreadContainer[T, R](val executorService: ExecutorService,
                                 val name: String,
                                 val taskProcessor: TaskProcessor[T, R]) {

  @volatile var bufferSize: Int = 5;

  @volatile var maxThreadNum: Int = 1;

  @volatile var threadCounter: Int = 0;

  protected val taskQueue: ConcurrentLinkedQueue[T] = new ConcurrentLinkedQueue[T]()

  protected val resultQueue: ConcurrentLinkedQueue[R] = new ConcurrentLinkedQueue[R]()

  protected val watchdog_thread = new Runnable {

    def checkAndOfferTask(): Unit = {
      //检测任务
      if (taskOffer != null && canOffer) {
        val task = Some(taskOffer.offerTask())
        if (task.isDefined && task.get != null) {
          taskQueue.offer(task.get)
        }
      }
    }

    def checkResult(): Unit = {
      //检测结果
      if (null != resultProcessor && !resultQueue.isEmpty) {
        executorService.submit(new Runnable {
          override def run(): Unit = {
            val result = Some(resultQueue.poll())
            if (result.isDefined && result.get != null) {
              resultProcessor.processResult(result.get)
            }
          }
        })
      }
    }

    def checkProcessor(): Unit = {
      //检测是否需要增加处理器
      if (maxThreadNum > 0 && threadCounter < maxThreadNum && !taskQueue.isEmpty) {
        executorService.submit(new Runner)
      }
    }

    override def run(): Unit = {
      while (true) {
        try {
          checkAndOfferTask()
          checkResult()
          checkProcessor()
          Thread.sleep(100L)
        } catch {
          case e: Exception => e.printStackTrace()
        }
      }
    }
  }

  //watchdog
  executorService.submit(watchdog_thread)

  /**
    * 判断是否可以加入任务
    *
    * @return
    */
  def canOffer: Boolean = {
    this.synchronized {
      val size = bufferSize * 2;
      return (taskQueue.size() + threadCounter) <= size && resultQueue.size() <= size
    }
  }

  /*提交任务*/
  var taskOffer: TaskOffer[T] = null

  def registerTaskOffer(offer: TaskOffer[T]): Unit = {
    this.taskOffer = offer
  }

  def offerTask(task: T): Boolean = {
    if (canOffer) {
      taskQueue.offer(task)
      return true
    } else {
      return false
    }
  }

  /*处理结果*/
  var resultProcessor: ResultProcessor[R] = null

  def registerResultProcessor(resultProcessor: ResultProcessor[R]): Unit = {
    this.resultProcessor = resultProcessor
  }

  def pollResult(): Option[R] = {
    if (resultQueue.isEmpty) {
      None
    } else {
      Some(resultQueue.poll());
    }
  }

  /**
    * 设置线程数
    *
    * @param num
    */
  def setThreadNum(num: Int) = {
    this.synchronized {
      maxThreadNum = num
    }
  }

  /**
    * 设置缓冲大小
    *
    * @param num
    */
  def setBufferSize(num: Int) = {
    this.synchronized {
      this.bufferSize = num
    }
  }

  /**
    * 处理线程
    */
  private class Runner extends Runnable {

    var threadCode: Int = -1;

    override def run(): Unit = {
      //start
      this.synchronized {
        threadCounter = threadCounter + 1
        if (threadCounter > maxThreadNum) {
          return
        }
        threadCode = threadCounter
      }
      println(name + " thread-" + threadCode + " start")
      /*加入执行代码*/
      if (!taskQueue.isEmpty && taskProcessor != null) {
        val result = Some(taskProcessor.processTask(taskQueue.poll()))
        if (result.isDefined && result.get != null) {
          resultQueue.offer(result.get)
        }
      }

      println(name + " thread-" + threadCode + " end")
      //end
      this.synchronized {
        if (threadCounter >= 1) {
          threadCounter = threadCounter - 1
        }
      }
    }
  }

}
