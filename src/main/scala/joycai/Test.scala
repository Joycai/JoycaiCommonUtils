package joycai

import java.util

import joycai.utils.http.HttpUtil
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class Main {



}

object Main {

  val logger: Logger = LoggerFactory.getLogger(Main.getClass)

  def main(args: Array[String]): Unit = {
    var header: util.Map[String, String] = new util.HashMap[String,String]()
    header.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
    header.put("Content-Type", "text/html; charset=utf-8");
    header.put("Accept-Encoding", "gzip, deflate, br");
    header.put("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.6, en; q=0.4, ja; q=0.2");
    header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");

    var a :Int = 1

    var response=HttpUtil.fromUrl("https://www.baidu.com").addHeader(header).get
    logger.info(response.getContent)
  }

}
