package medale.xml

import javax.xml.stream.{ XMLInputFactory, XMLStreamReader }
import javax.xml.stream.XMLStreamConstants._
import java.io.File
import java.io.FileReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.XMLEventReader

import scala.collection.mutable.Map

import CompanyParser._

class CompanyParser {

  private var eventReader: XMLEventReader = _
  private val parsedValuesMap = Map[String, Any]()

  var parseResult: Either[Exception, Map[String, Any]] = _

  def parseInput(file: File) {
    try {
      val factory = XMLInputFactory.newInstance();
      factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
      val fileReader = new FileReader(file)
      eventReader = factory.createXMLEventReader(fileReader)
      if (eventReader.hasNext()) {
        val currEvent = eventReader.nextTag()
        handleCompanyRootElement(currEvent.asStartElement())
      }
      parseResult = Right(parsedValuesMap)
    } catch {
      case e: Exception => parseResult = Left(e)
    }

  }

  def handleCompanyRootElement(companyRootElement: StartElement) {
    parseCompanyName()
  }

  def parseCompanyName() {
    if (eventReader.hasNext()) {
      val expectedNameEvent = eventReader.nextTag()
      val nameElem: StartElement = expectedNameEvent.asStartElement()
      if (validateElem(nameElem, CompanyNameSpace, Name)) {
        val companyName = eventReader.getElementText()
        parsedValuesMap += (CompanyNameKey -> companyName)
      }
    }
  }

  def getString(): Option[String] = {
    var result: Option[String] = None
    if (eventReader.hasNext()) {
      val event = eventReader.nextEvent
      if (event.isCharacters()) {
        val chars = event.asCharacters()
      }
    }
    result
  }

  def validateElem(elem: StartElement, nameSpace: String, elemName: String): Boolean = {
    val qName = elem.getName()
    if (qName.getNamespaceURI() == nameSpace && qName.getLocalPart() == elemName) true else false
  }
}

object CompanyParser {
  val Name = "Name"
  val CompanyNameSpace = "http://company.org"
  val CompanyNameKey = "companyName"
}
