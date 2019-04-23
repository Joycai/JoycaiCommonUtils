package joycai.utils.mt

trait TaskProcessor[T, R] {
  def processTask(task: T): R
}
