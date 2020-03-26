package joycai.utils.encrypt

import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

class AESImpl(
               val sks: SecretKeySpec
             ) extends AESHelper {

  protected def getCipher():Cipher={
    Cipher.getInstance("AES")
  }

  protected def getCipherEncrypt():Cipher={
    val cipher = getCipher()
    cipher.init(Cipher.ENCRYPT_MODE, sks)
    cipher
  }

  protected def getCipherDecrypt():Cipher={
    val cipher = getCipher()
    cipher.init(Cipher.DECRYPT_MODE, sks)
    cipher
  }

  override def encrypt(content: Array[Byte]): Array[Byte] = {
    getCipherEncrypt().doFinal(content)
  }

  override def decrypt(encryptedContent: Array[Byte]): Array[Byte] = {
    getCipherDecrypt().doFinal(encryptedContent)
  }

}
