package joycai.utils.date

import java.math.BigDecimal

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

  def get2BitDecimal( input : Double) : Double = {
    val b = new BigDecimal(input)
    getDecimalHalfUp(b, 2).doubleValue()
  }

  def getDecimalHalfUp( decimal:BigDecimal , bit: Int): BigDecimal ={
    decimal.setScale(bit,BigDecimal.ROUND_HALF_UP)
    decimal
  }

  implicit def convertDoubleToFloat (in : Double) : Float = in.toFloat
  implicit def convertFloatToDouble (in : Float) : Double = in.toDouble
}
