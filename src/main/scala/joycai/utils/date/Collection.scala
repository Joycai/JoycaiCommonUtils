package joycai.utils.date

import java.util

object Collection {

  def notNullOrEmpty[T]( collection: util.Collection[T] ) : Boolean = collection != null && !collection.isEmpty
}
