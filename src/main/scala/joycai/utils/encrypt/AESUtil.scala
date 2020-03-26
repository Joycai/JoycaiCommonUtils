package joycai.utils.encrypt

import java.security.{SecureRandom, Security}

import com.google.common.base.Charsets
import javax.crypto.KeyGenerator
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Base64

object AESUtil {
  Security.addProvider(new BouncyCastleProvider())

  /**
    *
    * @param keySize
    * @param seed
    * @return base64 encoded key
    */
  def genAESKey(keySize: Int, seed: String): String = {
    val kgen = KeyGenerator.getInstance("AES")
    val random = SecureRandom.getInstance("SHA1PRNG")
    random.setSeed(seed.getBytes(Charsets.UTF_8))
    kgen.init(keySize, random)
    val secretKey = kgen.generateKey()
    //encode to base64
    Base64.toBase64String(secretKey.getEncoded)
  }

  /**
    *
    * @param key 128/192/256 length
    * @return
    */
  def getNewAESHelper(key: Array[Byte]): AESHelper = {
    new AESImpl(new SecretKeySpec(key, "AES"))
  }

  def getNewAESHelper(keyStr: String): AESHelper = getNewAESHelper(Base64.decode(keyStr))

  /**
    * 微信的加密方式 AES-128-CBC，数据采用PKCS#7填充
    *
    * @param key 128/192/256 length
    * @param iv 128 length
    * @return
    */
  def getNewAESCBCHelper(key: Array[Byte], iv: Array[Byte]): AESHelper = {
    new AESCBCImpl(new SecretKeySpec(key, "AES"), new IvParameterSpec(iv))
  }

  def getNewAESCBCHelper(keyStr: String, ivStr: String): AESHelper = getNewAESCBCHelper(Base64.decode(keyStr), Base64.decode(ivStr))

}
