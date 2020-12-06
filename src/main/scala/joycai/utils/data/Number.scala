package joycai.utils.data

import java.math.{BigDecimal, RoundingMode}

object Number {

  /**
    * 保留3位小数
    * @param input
    * @return
    */
  def get3BitDecimal( input : Double ) : BigDecimal = {
    val b = new BigDecimal(input)
    getDecimalHalfUp(b, 3)
  }

  /**
    * 保留两位小数
    * @param input
    * @return
    */
  def get2BitDecimal( input : Double) : BigDecimal = {
    val b = new BigDecimal(input)
    getDecimalHalfUp(b, 2)
  }

  def getDecimalHalfUp( decimal:BigDecimal , bit: Int): BigDecimal ={
    decimal.setScale(bit,BigDecimal.ROUND_HALF_UP)
  }

  implicit def convertDoubleToFloat (in : Double) : Float = in.toFloat
  implicit def convertFloatToDouble (in : Float) : Double = in.toDouble
}
