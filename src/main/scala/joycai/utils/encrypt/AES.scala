package joycai.utils.encrypt

import java.net.URLDecoder
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.{Cipher, KeyGenerator, SecretKey}
import javax.crypto.spec.SecretKeySpec

import com.google.common.base.Strings
import org.slf4j.{Logger, LoggerFactory}

object AES {

  def logger : Logger = LoggerFactory.getLogger(AES.getClass)

  /**
    * 加密
    * @param content 加密内容
    * @param pwd 密钥 16位
    * @return null 表示加密失败 ; 成功则是Base64之后的字符串
    */
    def encrypt(content:String, pwd : String) : String ={
    if (Strings.isNullOrEmpty(pwd)|| pwd.length!=16){
      logger.error("pwd length must equals 16")
      return null;
    }

    val kgen = KeyGenerator.getInstance("AES")

    //不明原因，一定要这么写
    val random = SecureRandom.getInstance("SHA1PRNG")
    random.setSeed(pwd.getBytes)

    kgen.init(128, random)
    val secretKey = kgen.generateKey
    val enCodeFormat = secretKey.getEncoded
    val key = new SecretKeySpec(enCodeFormat, "AES")
    val cipher = Cipher.getInstance("AES")
    // 创建密码器
    val byteContent = content.getBytes("UTF-8")
    cipher.init(Cipher.ENCRYPT_MODE, key) // 初始化

    val result = cipher.doFinal(byteContent)
    Base64.getEncoder.encodeToString(result)
  }

  /**
    *
    * @param encrypted 已经加密的内容
    * @param pwd 密钥 16位
    * @return 原文
    */
  def decrypt( encrypted :String , pwd : String) : String ={
    if (Strings.isNullOrEmpty(pwd)|| pwd.length!=16){
      logger.error("pwd length must equals 16")
      return null;
    }

    val kgen = KeyGenerator.getInstance("AES")

    val random = SecureRandom.getInstance("SHA1PRNG")
    random.setSeed(pwd.getBytes)

    kgen.init(128, random)
    val secretKey = kgen.generateKey
    val enCodeFormat = secretKey.getEncoded
    val key = new SecretKeySpec(enCodeFormat, "AES")
    val cipher = Cipher.getInstance("AES") // 创建密码器
    cipher.init(Cipher.DECRYPT_MODE, key) // 初始化

    //先base64解密
    var decStr:Array[Byte] = null
    try
      decStr = Base64.getDecoder.decode(encrypted)
    catch {
      case e: IllegalArgumentException =>
        //处理url encode的问题
        val url = encrypted.replaceAll("%(?![0-9a-fA-F]{2})", "%25")
        val urlStr = URLDecoder.decode(url, "UTF-8")
        decStr = Base64.getDecoder.decode(urlStr)
    }

    val result = cipher.doFinal(decStr)
    val originalString = new String(result, "UTF-8")
    originalString
  }

}
