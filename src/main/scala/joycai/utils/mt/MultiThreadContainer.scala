package joycai.utils.mt

import java.util.concurrent.{ConcurrentLinkedQueue, ExecutorService}

class MultiThreadContainer[T, R](val executorService: ExecutorService,
                                 val name: String,
                                 val taskProcessor: TaskProcessor[T, R] ) {

  @volatile var maxThreadNum: Int = 1

  @volatile var threadCounter: Int = 0

  protected val taskQueue: ConcurrentLinkedQueue[T] = new ConcurrentLinkedQueue[T]()

  protected val resultQueue: ConcurrentLinkedQueue[R] = new ConcurrentLinkedQueue[R]()


  //watchdog
  executorService.submit(new Runnable {
    override def run(): Unit = {
      while (true) {
        try {
          //检测任务
          if (taskOffer != null && canOffer()) {
            taskQueue.offer(taskOffer.offerTask())
          }
          //检测结果
          if (null != resultProcessor && !resultQueue.isEmpty) {
            executorService.submit(new Runnable {
              override def run(): Unit = {
                resultProcessor.processResult(resultQueue.poll())
              }
            })
          }
          //检测是否需要增加处理器
          if (threadCounter < maxThreadNum && !taskQueue.isEmpty) {
            executorService.submit(new Runner)
          }
          Thread.sleep(1000l)
        } catch {
          case e: Exception => e.printStackTrace()
        }
      }
    }
  })

  /**
    * 判断是否可以加入任务
    *
    * @return
    */
  def canOffer(): Boolean = {
    this.synchronized {
      return (taskQueue.size() + threadCounter) <= maxThreadNum * 2 && resultQueue.size() <= maxThreadNum * 2
    }
  }

  /*提交任务*/
  var taskOffer: TaskOffer[T] = null

  def registerTaskOffer(offer: TaskOffer[T]): Unit = {
    this.taskOffer = offer
  }

  def offerTask(task: T): Boolean = {
    if (canOffer()) {
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
      return None
    } else {
      return Some(resultQueue.poll());
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
        var result = taskProcessor.processTask(taskQueue.poll())
        if (null != result) {
          resultQueue.offer(result)
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
