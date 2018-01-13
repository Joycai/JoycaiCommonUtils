package joycai

import java.util.concurrent.Executors

import joycai.utils.mt.MultiThreadContainer
import org.slf4j.{Logger, LoggerFactory}

import scala.util.control.Breaks._

class Main {


}

object Main {

  val logger: Logger = LoggerFactory.getLogger(Main.getClass)

  def main(args: Array[String]): Unit = {
    //    var header: util.Map[String, String] = new util.HashMap[String,String]()
    //    header.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
    //    header.put("Content-Type", "text/html; charset=utf-8");
    //    header.put("Accept-Encoding", "gzip, deflate, br");
    //    header.put("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.6, en; q=0.4, ja; q=0.2");
    //    header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
    /*ele*/
    //    header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    //    header.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6,en;q=0.4,ja;q=0.2,de;q=0.2,et;q=0.2,af;q=0.2");
    //    header.put("Cache-Control", "no-cache");
    //    header.put("Content-Type", "application/json; charset=UTF-8");
    //    header.put("Host", "www.ele.me");
    //    header.put("Pragma", "no-cache");
    //    header.put("Upgrade-Insecure-Requests", "1");
    //    header.put("User-Agent",
    //      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");

    //    var a :Int = 1
    //
    //    var response=HttpUtil.fromUrl("http://www.dianping.com/shop/58223669/review_all?queryType=sortType&queryVal=latest").addHeader(header).get
    //    logger.info(response.getContent)

    var testMTContainer: MultiThreadContainer[String, String] = new MultiThreadContainer[String, String](Executors.newFixedThreadPool(50),
      "TEST",
      (task) => {
        println("task:" + task)
        new String("your")
      })

    testMTContainer.offerTask("my")

    var result: Option[String] = None

    breakable(
      while (true) {
        result = testMTContainer.pollResult()
        if (result.isDefined) {
          break()
        }
        Thread.sleep(2000l)
      }
    )

    println("result" + result.get)
  }
}
