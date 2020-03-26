package joycai.utils.encrypt

import java.security.Security

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import org.bouncycastle.jce.provider.BouncyCastleProvider

/**
  * 微信的加密方式 AES-128-CBC，数据采用PKCS#7填充
  *
  * @param sks
  * @param iv
  */
class AESCBCImpl(
                  override val sks: SecretKeySpec,
                  val iv: IvParameterSpec) extends AESImpl(sks) {
  override protected def getCipher(): Cipher = {
    Security.addProvider(new BouncyCastleProvider())
    Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC")
  }

  override protected def getCipherEncrypt(): Cipher = {
    val cipher = getCipher()
    cipher.init(Cipher.ENCRYPT_MODE, sks, iv)
    cipher
  }

  override protected def getCipherDecrypt(): Cipher = {
    val cipher = getCipher()
    cipher.init(Cipher.DECRYPT_MODE, sks, iv)
    cipher
  }
}
