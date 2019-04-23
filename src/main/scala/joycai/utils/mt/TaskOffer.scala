package joycai.utils.mt

/**
  * 任务提交器，实现offerTask方法来提交任务
  *
  * @tparam T
  */
trait TaskOffer[T] {
  def offerTask() : T
}
