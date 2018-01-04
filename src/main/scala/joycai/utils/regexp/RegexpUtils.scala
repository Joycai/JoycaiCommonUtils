package joycai.utils

import java.util.regex.Pattern

object RegexpUtils {
  val URL_REGEXP: String = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"
  val EMAIL_REGEXP: String = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+"

  def isEmail(email: String): Boolean = regexpMatcher(email, EMAIL_REGEXP)

  def isUrl(url: String): Boolean = regexpMatcher(url, URL_REGEXP)

  def regexpMatcher(toTest: String, regexp: String): Boolean = {
    val pattern = Pattern.compile(regexp)
    val matcher = pattern.matcher(toTest)
    matcher.matches
  }
}
