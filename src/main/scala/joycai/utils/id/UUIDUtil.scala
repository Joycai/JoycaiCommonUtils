package joycai.utils.id

import java.util.UUID

import com.google.common.base.CharMatcher

object UUIDUtil {

  def getUUID: UUID = {
    this.synchronized {
      return UUID.randomUUID()
    }
  }

  def getUUIDStr: String = {
    CharMatcher.anyOf("-").removeFrom(getUUID.toString)
  }

}
