package joycai.utils.data

import java.math.{BigDecimal, RoundingMode}

object Number {

  /**
    * 保留3位小数
    * @param input
    * @return
    */
  def get3BitDecimal( input : Double ) : Double = {
    val b = new BigDecimal(input)
    getDecimalHalfUp(b, 3).doubleValue()
  }

  /**
    * 保留两位小数
    * @param input
    * @return
    */
  def get2BitDecimal( input : Double) : Double = {
    val b = new BigDecimal(input)
    getDecimalHalfUp(b, 2).doubleValue()
  }

  def getDecimalHalfUp( decimal:BigDecimal , bit: Int): BigDecimal ={
    decimal.setScale(bit, RoundingMode.HALF_UP)
    decimal
  }

  implicit def convertDoubleToFloat (in : Double) : Float = in.toFloat
  implicit def convertFloatToDouble (in : Float) : Double = in.toDouble
}
