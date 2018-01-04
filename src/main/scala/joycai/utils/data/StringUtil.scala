package joycai.utils.data

import java.util.regex.{Matcher, Pattern}

object StringUtil {

  val URL_PATTERN = Pattern.compile("[a-zA-Z]+://[^\\s/]*")
  val PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z]+://")
  val DOMAIN_PATTERN = Pattern.compile("[a-zA-Z0-9]+\\.")

  def getSiteDomain(url: String): String = {
    val urlMatcher = URL_PATTERN.matcher(url)
    if (urlMatcher.find) {
      var urlStr = urlMatcher.group
      val protocolMatcher = PROTOCOL_PATTERN.matcher(urlStr)
      if (protocolMatcher.find) {
        val protocolStr = protocolMatcher.group
        urlStr = urlStr.replace(protocolStr, "")
      }
      val domainMatcher = DOMAIN_PATTERN.matcher(urlStr)
      var domain = ""

      while ( {
        domainMatcher.find
      }) domain = domainMatcher.group
      return domain.replace(".", "")
    }
    null
  }
}
