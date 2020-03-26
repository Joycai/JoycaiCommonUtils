package joycai.utils.encrypt

import org.bouncycastle.util.encoders.Base64

trait AESHelper {

  def encrypt(content: Array[Byte]): Array[Byte]

  def encryptToString(content: Array[Byte]): String = Base64.toBase64String(encrypt(content))

  def decrypt(encryptedContent: Array[Byte]): Array[Byte]

  def decryptFromString(encryptedContent: String): Array[Byte] = decrypt(Base64.decode(encryptedContent))
}
