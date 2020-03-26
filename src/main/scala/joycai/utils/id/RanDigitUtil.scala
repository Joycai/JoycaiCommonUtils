package joycai.utils.id

import org.apache.commons.lang3.RandomUtils

/**
  * 随机数字发生器
  */
object RanDigitUtil {

  def randomDigit(digit: Int): String = {
    var str = ""
    for (i <- 1 to digit) {
      str += RandomUtils.nextInt(0, 9)
    }
    str
  }

  def random6digit(): String = {
    randomDigit(6)
  }

  def random4digit(): String = {
    randomDigit(6)
  }
}
