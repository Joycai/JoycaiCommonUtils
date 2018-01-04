package joycai.utils.id

import java.security.MessageDigest

object MD5 {

  def hash(str:String):String = {
    val md = MessageDigest.getInstance("MD5")
    md.update(str.getBytes)
    val b = md.digest
    var i = 0
    val buf = new StringBuilder("")
    for (aB <- b) {
      i = aB
      if (i < 0) i += 256
      if (i < 16) buf.append("0")
      buf.append(Integer.toHexString(i))
    }
    buf.toString
  }

  def hash16(str:String) :String = {
    hash(str).substring(8,24)
  }
}
