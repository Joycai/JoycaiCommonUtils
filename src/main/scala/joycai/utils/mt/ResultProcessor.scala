package joycai.utils.mt

trait ResultProcessor[T] {
  def processResult(result: T)
}
