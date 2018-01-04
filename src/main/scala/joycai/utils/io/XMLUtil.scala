package joycai.utils.io

import java.io.{StringReader, StringWriter}
import javax.xml.bind.{JAXBContext, JAXBException, Marshaller, Unmarshaller}

object XMLUtil {

  /**
    * xmlStr to Obj
    * @param clazz
    * @param xmlStr
    * @tparam T
    * @return
    */
  def  convertXmlStrToObject [T] (clazz : Class[T], xmlStr :String) : Option[T] = {
    try {
      var context:JAXBContext = JAXBContext.newInstance(clazz);
      // 进行将Xml转成对象的核心接口
      var unmarshaller:Unmarshaller = context.createUnmarshaller();
      var sr:StringReader = new StringReader(xmlStr);
      var xmlObject = unmarshaller.unmarshal(sr);
      Some(xmlObject.asInstanceOf[T])
    } catch {
      case e: JAXBException => {
        e.printStackTrace
      }
    }
    None
  }

  /**
    * 对象转xml
    * @param any
    * @return
    */
  def convertObjToXmlStr ( any: Any) :String ={
    // 创建输出流
    def sw = new StringWriter();
    try {
      // 利用jdk中自带的转换类实现
      var context = JAXBContext.newInstance(any.getClass);

      var marshaller = context.createMarshaller();
      // 格式化xml输出的格式
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
        true);
      // 将对象转换成输出流形式的xml
      marshaller.marshal(any, sw);
    } catch {
      case e: JAXBException=>
      e.printStackTrace();
    }
    sw.toString();
  }
}
