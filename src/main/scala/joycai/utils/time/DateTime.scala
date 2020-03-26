package joycai.utils.time

import java.text.SimpleDateFormat
import java.time.{DayOfWeek, LocalDateTime, ZoneId}
import java.util.{Calendar, Date, Locale, TimeZone}

object DateTime {

  private val zoneChina = ZoneId.of("Asia/Shanghai")

  /**
    * 获取中国的当前时间
    *
    * @return
    */
  def nowChina(): Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINA)

  def toLocalDateTime(date: Date, zoneId: ZoneId): LocalDateTime = {
    LocalDateTime.ofInstant(date.toInstant, zoneId)
  }

  def toLocalDateTime(date: Date): LocalDateTime = {
    LocalDateTime.ofInstant(date.toInstant, ZoneId.systemDefault())
  }

  def zeroDate(date: Date, zoneId: ZoneId): Date = {
    val ldt = toLocalDateTime(date, zoneId)
    val current = ldt.withHour(0).withMinute(0).withSecond(0).withNano(0)
    Date.from(current.atZone(zoneId).toInstant())
  }

  def zeroDate(date: Date):Date = {
    zeroDate(date, ZoneId.systemDefault())
  }

  def workDay(date:Date):Boolean={
    val ldt = toLocalDateTime(date)
    val day = ldt.getDayOfWeek
    !(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
  }

  /**
    * 获取时间字符串
    *
    * @param format
    * @param timeZone
    * @return
    */
  def getTimeStr(format: String, timeZone: String): String = {
    val timestamp = System.currentTimeMillis
    val df = new SimpleDateFormat(format)
    df.setTimeZone(TimeZone.getTimeZone(timeZone))
    df.format(new Date(timestamp))
  }
}
