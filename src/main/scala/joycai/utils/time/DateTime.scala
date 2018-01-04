package joycai.utils.time

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Locale, TimeZone}

object DateTime {

  def nowChina(): Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINA)

  def zeroDate(data: Date): Date = {
    val cal1 = Calendar.getInstance
    cal1.setTime(data)
    cal1.set(Calendar.HOUR_OF_DAY, 0)
    cal1.set(Calendar.MINUTE, 0)
    cal1.set(Calendar.SECOND, 0)
    cal1.set(Calendar.MILLISECOND, 0)
    cal1.getTime
  }

  def getTimeStr(format: String, timeZone: String): String = {
    val timestamp = System.currentTimeMillis
    val df = new SimpleDateFormat(format)
    df.setTimeZone(TimeZone.getTimeZone(timeZone))
    df.format(new Date(timestamp))
  }
}
