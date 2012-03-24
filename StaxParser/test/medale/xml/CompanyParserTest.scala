package medale.xml

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.File

@RunWith(classOf[JUnitRunner])
class CompanyParserTest extends FunSuite {

  test("test with valid file") {
    val companyParser = new CompanyParser()
    val input = new File("data.xml")
    companyParser.parseInput(input)
    val result = companyParser.parseResult
    result match {
      case Right(map) => assert(map.contains(CompanyParser.CompanyNameKey))
    }
  }
}