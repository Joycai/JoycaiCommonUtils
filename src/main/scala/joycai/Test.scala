package joycai

import joycai.utils.http.HttpUtil
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class Main {

}

object Main {

  val logger: Logger = LoggerFactory.getLogger(Main.getClass)

  def main(args: Array[String]): Unit = {
    var header: mutable.Map[String, String] = mutable.Map();
    header.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
    header.put("Content-Type", "application/json");
    header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");

    var response = HttpUtil.fromUrl("https://www.baidu.com")
      .addHeader(header.toMap)
      .get
    logger.info("ok")
  }
}
