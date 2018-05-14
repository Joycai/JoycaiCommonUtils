package joycai.utils.encrypt

import java.security.Security
import java.util.Base64

import com.google.common.base.Charsets
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import org.bouncycastle.jce.provider.BouncyCastleProvider

/**
  * 微信的加密方式 AES-128-CBC，数据采用PKCS#7填充
  */
object AESCBC {

  var CODE_CHARSET = Charsets.UTF_8

  /**
    * 加密【byte】
    *
    * @param data
    * @param pwd
    * @param iv
    * @return
    */
  def encrypt(data: Array[Byte], pwd: String, iv: String): Array[Byte] = {
    val cipher = getCipher()
    cipher.init(Cipher.ENCRYPT_MODE, getKey(pwd), getIV(iv))
    val encryptedData = cipher.doFinal(data)
    encryptedData
  }

  /**
    * 加密，string
    *
    * @param data string
    * @param pwd
    * @param iv
    * @return Base64 string
    */
  def encrypt(data: String, pwd: String, iv: String): String = {
    val enData = encrypt(data.getBytes(CODE_CHARSET), pwd, iv)
    Base64.getEncoder.encodeToString(enData)
  }

  /**
    * 解密[byte]
    *
    * @param data
    * @param pwd
    * @param iv
    * @return
    */
  def decrypt(data: Array[Byte], pwd: String, iv: String): Array[Byte] = {
    val cipher = getCipher()
    cipher.init(Cipher.DECRYPT_MODE, getKey(pwd), getIV(iv))
    val decryptedData = cipher.doFinal(data)
    decryptedData
  }

  /**
    * 解密base64
    *
    * @param data base64String
    * @param pwd
    * @param iv
    * @return
    */
  def decrypt(data: String, pwd: String, iv: String): Array[Byte] = {
    val decodeByte = Base64.getDecoder.decode(data)
    decrypt(decodeByte, pwd, iv)
  }

  private def getCipher(): Cipher = {
    Security.addProvider(new BouncyCastleProvider())
    Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC")
  }

  private def getKey(pwd: String): SecretKeySpec = new SecretKeySpec(pwd.getBytes(CODE_CHARSET), "AES");

  private def getIV(iv: String): IvParameterSpec = new IvParameterSpec(iv.getBytes(CODE_CHARSET))
}
