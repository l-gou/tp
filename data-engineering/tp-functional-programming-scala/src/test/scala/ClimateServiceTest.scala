import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC"))
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  test("getMinMax should return (0.0, 10.0) for a list containing CO2Record instances with ppm values from 0.0 to 10.0") {
    val records = List(
      CO2Record(2023, 1, 0.0),
      CO2Record(2023, 2, 5.0),
      CO2Record(2023, 3, 10.0)
    )
    val result = ClimateService.getMinMax(records)
    assert(result == (0.0, 10.0))
  }

  test("getMinMax should return (0.0, 0.0) for a list containing only one CO2Record instance with ppm value 0.0") {
    val records = List(
      CO2Record(2023, 1, 0.0)
    )
    val result = ClimateService.getMinMax(records)
    assert(result == (0.0, 0.0))
  }

  test("getMinMax should return (0.0, 0.0) for an empty list") {
    val records = List.empty[CO2Record]
    val result = ClimateService.getMinMax(records)
    assert(result == (0.0, 0.0))
  }

test("getMinMaxByYear should return (0.0, 10.0) for a list containing CO2Record instances with ppm values from 0.0 to 10.0 for the year 2023") {
    val records = List(
      CO2Record(2023, 1, 0.0),
      CO2Record(2023, 2, 5.0),
      CO2Record(2023, 3, 10.0)
    )
    val result = ClimateService.getMinMaxByYear(records, 2023)
    assert(result == (0.0, 10.0))
  }

  test("getMinMaxByYear should return (0.0, 0.0) for a list containing only one CO2Record instance with ppm value 0.0 for the year 2023") {
    val records = List(
      CO2Record(2023, 1, 0.0)
    )
    val result = ClimateService.getMinMaxByYear(records, 2023)
    assert(result == (0.0, 0.0))
  }

  test("getMinMaxByYear should return (0.0, 0.0) for an empty list for the year 2023") {
    val records = List.empty[CO2Record]
    val result = ClimateService.getMinMaxByYear(records, 2023)
    assert(result == (0.0, 0.0))
  }

  test("getMinMaxByYear should return (0.0, 0.0) for a list containing CO2Record instances with ppm values from 0.0 to 10.0 for a year that does not exist in the list") {
    val records = List(
      CO2Record(2023, 1, 0.0),
      CO2Record(2023, 2, 5.0),
      CO2Record(2023, 3, 10.0)
    )
    val result = ClimateService.getMinMaxByYear(records, 2024)
    assert(result == (0.0, 0.0))
  }

  test("calculateDifference should return Some(10.0) for a list containing CO2Record instances with ppm values from 0.0 to 10.0") {
    val records = List(
      CO2Record(2023, 1, 0.0),
      CO2Record(2023, 2, 5.0),
      CO2Record(2023, 3, 10.0)
    )
    val result = ClimateService.calculateDifference(records)
    assert(result == Some(10.0))
  }

  test("calculateDifference should return Some(0.0) for a list containing only one CO2Record instance with ppm value 0.0") {
    val records = List(
      CO2Record(2023, 1, 0.0)
    )
    val result = ClimateService.calculateDifference(records)
    assert(result == Some(0.0))
  }

  test("calculateDifference should return None for an empty list") {
    val records = List.empty[CO2Record]
    val result = ClimateService.calculateDifference(records)
    assert(result == None)
  }

  //test("calculateDifference should return None for a list containing CO2Record instances with ppm values from 0.0 to 0.0") {
  //   val records = List(
  //    CO2Record(2023, 1, 0.0),
  //    CO2Record(2023, 2, 0.0),
  //    CO2Record(2023, 3, 0.0)
  //  )
  //  val result = ClimateService.calculateDifference(records)
  //  assert(result == None)
  //}

  //@TODO
  //test("filterDecemberData") {
  //  assert(true == false)
  //}
  test("filterDecemberData should remove all values from December (month 12) of every year") {
  val inputList = List(
    Some(CO2Record(2023, 1, 400.0)),
    Some(CO2Record(2023, 12, 410.0)),
    Some(CO2Record(2024, 6, 420.0)),
    Some(CO2Record(2024, 12, 430.0)),
    Some(CO2Record(2025, 12, 440.0)),
    None
  )
  val expectedOutput = List(
    CO2Record(2023, 1, 400.0),
    CO2Record(2024, 6, 420.0)
  )
  val actualOutput = ClimateService.filterDecemberData(inputList)
  assert(actualOutput == expectedOutput)
}
}