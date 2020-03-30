package joycai.utils.mt

/**
  * 任务处理
  *
  * @tparam T
  * @tparam R
  */
trait TaskProcessor[T, R] {
  def processTask(task: T): R
}
