package joycai.utils.http

import java.io._
import java.net.{HttpURLConnection, URL}
import java.util
import java.util.zip.GZIPInputStream

import com.google.common.base.Strings
import joycai.utils.http.model.HttpResponse
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class HttpUtil private(val url: String) {

  protected val logger: Logger = LoggerFactory.getLogger(HttpUtil.getClass)

  protected var requestBody: String = _

  protected var readTimeout: Int = 30000

  protected var requestHeader : Map[String,String] = _

  implicit def jMapToMap( jmap : util.Map[String,String]) = {

//    import scala.collection.JavaConverters._
    import scala.jdk.CollectionConverters.MapHasAsScala
    jmap.asScala.toMap
  }

  /**
    * 添加请求体
    *
    * @param body
    * @return
    */
  def addBody(body: String): HttpUtil = {
    if (!Strings.isNullOrEmpty(body)) {
      this.requestBody = body
    }
    return this
  }

  /**
    * 添加请求头
    *
    * @param header
    * @return
    */
  def addHeader(header: Map[String, String]): HttpUtil = {
    if (header != null && !(header.isEmpty)) {
      this.requestHeader = header
    }
    return this
  }

  def addHeader(header: util.Map[String, String]): HttpUtil = {
    import scala.jdk.CollectionConverters.MapHasAsScala
    addHeader(header.asScala.toMap)
  }

  /**
    * 设置读取超时
    *
    * @param timeout
    * @return
    */
  def setReadTimeout(timeout: Int): HttpUtil = {
    if (timeout > 0) {
      this.readTimeout = timeout
    }
    return this;
  }

  /**
    * post 请求
    *
    * @return
    */
  def post: HttpResponse = {
    var conn = buildHttpURLConn()
    conn.setRequestMethod("POST")
    conn.connect
    if (!Strings.isNullOrEmpty(this.requestBody)) {
      val out = new OutputStreamWriter(conn.getOutputStream, "UTF-8") // utf-8编码
      out.append(this.requestBody)
      out.flush()
      out.close()
    }
    return readResponse(conn);
  }

  /**
    * get 请求
    *
    * @return
    */
  def get: HttpResponse = {
    var conn = buildHttpURLConn()
    conn.setRequestMethod("GET")
    conn.connect
    return readResponse(conn);
  }

  def download: Array[Byte] = {
    var conn = buildHttpURLConn()
    val inStream = conn.getInputStream
    val baos = new ByteArrayOutputStream
    var len = 0
    val buffer = new Array[Byte](512)
    while ( {
      len = inStream.read(buffer)
      len != -1
    }) baos.write(buffer, 0, len)
    inStream.close()
    baos.close()
    baos.flush()
    return baos.toByteArray
  }


  /**
    * 读取请求结果
    *
    * @return
    */
  protected def readResponse( conn: HttpURLConnection): HttpResponse = {
    var is: InputStream = null

    var response: HttpResponse = new HttpResponse

    //获取头
    import scala.jdk.CollectionConverters._

    var responseHeader: mutable.Map[String, String] = mutable.HashMap()

    val headerFields: mutable.Map[String, util.List[String]] = conn.getHeaderFields.asScala

    if (null != headerFields && !(headerFields.isEmpty)) {

      headerFields.view.filterKeys( k => !Strings.isNullOrEmpty( k ) )
        .foreach( mapEntity => {
          var str: mutable.StringBuilder = new mutable.StringBuilder()
          mapEntity._2.asScala.foreach( value => str.append(value) );

          responseHeader.put(mapEntity._1, str.toString())
        })

      response.setHeaders(responseHeader.asJava)
    }

    //处理返回值
    if (conn.getResponseCode == 200) {
      //请求成功
      is = conn.getInputStream

      var content: String = null
      if (conn.getContentEncoding != null && conn.getContentEncoding.toLowerCase.indexOf("gzip") > -1) {
        //gzip
        content = readGZip(is)
      } else {
        content = readNormal(is, conn.getContentLength)
      }
      response.setContent(content)

    } else {
      is = conn.getErrorStream
      response.setHttpCode(conn.getResponseCode.toString)
      response.setSuccess(false)
    }

    response
  }

  protected def readNormal(is: InputStream, length: Int): String = {
    if (length != -1) {
      val data = new Array[Byte](length)
      val temp = new Array[Byte](512)
      var readLen = 0
      var destPos = 0
      while ( {
        readLen = is.read(temp)
        readLen > 0
      }) {
        System.arraycopy(temp, 0, data, destPos, readLen)
        destPos += readLen
      }
      val result = new String(data, "UTF-8") // utf-8编码
      logger.info(result)
      return result
    } else {
      /**
        * 尝试规避content-length问题
        */
      val isr = new InputStreamReader(is, "UTF-8")

      val br = new BufferedReader(isr)
      val sb = new StringBuffer
      var tempbf: String = null
      while ( {
        tempbf = br.readLine
        tempbf != null
      }) {
        sb.append(tempbf)
        sb.append("\r\n")
      }
      isr.close()
      logger.info(sb.toString)
      return sb.toString
    }
  }

  /**
    * 读取gzip压缩后的结果
    *
    * @param is
    * @return
    */
  protected def readGZip(is: InputStream): String = {
    val gzin = new GZIPInputStream(is)

    val isr = new InputStreamReader(gzin, "UTF-8")
    val br = new BufferedReader(isr)
    val sb = new StringBuffer
    var tempbf: String = null
    while ( {
      tempbf = br.readLine
      tempbf != null
    }) {
      sb.append(tempbf)
      sb.append("\r\n")
    }
    isr.close()
    gzin.close()
    logger.info(sb.toString)

    return sb.toString
  }

  protected def buildHttpURLConn(): HttpURLConnection = {
    val url: URL = new URL(this.url);
    var connection: HttpURLConnection = url.openConnection.asInstanceOf[HttpURLConnection];
    connection.setDoOutput(true)
    connection.setDoInput(true)
    connection.setUseCaches(true)
    connection.setConnectTimeout(3000)
    connection.setReadTimeout(this.readTimeout)
    connection.setInstanceFollowRedirects(true)

    if (null!=requestHeader&& !requestHeader.isEmpty){
      requestHeader.foreach(  kv  => {
        connection.setRequestProperty(kv._1, kv._2)
      })
    }

    return connection
  }
}

object HttpUtil {

  def fromUrl(url: String): HttpUtil = {
    return new HttpUtil(url)
  }


}
