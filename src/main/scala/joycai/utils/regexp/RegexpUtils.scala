package joycai.utils

import java.util.regex.Pattern

object RegexpUtils {

  val URL_REGEXP = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]".r

  val PHONE_REGEXP = "^[0-9]{11}$".r

  val EMAIL_REGEXP = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+".r

  def isEmail(email: String): Boolean = URL_REGEXP.pattern.matcher(email).matches()

  def isUrl(url: String): Boolean = URL_REGEXP.pattern.matcher(url).matches()

  def isPhone(phone: String): Boolean = PHONE_REGEXP.pattern.matcher(phone).matches()

  def regexpMatcher(toTest: String, regexp: String): Boolean = {
    regexp.r.pattern.matcher(toTest).matches()
  }
}
