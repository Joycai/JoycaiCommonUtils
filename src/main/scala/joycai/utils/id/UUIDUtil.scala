package joycai.utils.id

import java.util.UUID

import com.google.common.base.CharMatcher

object UUIDUtil {

  def getUUID: UUID = {
    this.synchronized {
      var uuid = UUID.randomUUID();
      return uuid;
    }
  }

  def getUUIDStr: String = {
    return CharMatcher.anyOf("-").removeFrom(getUUID.toString)
  }

}
