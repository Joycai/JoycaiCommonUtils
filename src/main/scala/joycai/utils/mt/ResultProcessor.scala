package joycai.utils.mt

/**
  * 结果处理
  *
  * @tparam T
  */
trait ResultProcessor[T] {
  def processResult(result: T): Unit
}
