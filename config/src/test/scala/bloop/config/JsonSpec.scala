package bloop.config

import bloop.config.Config.File
import org.junit.{Assert, Test}

class JsonSpec {
  def parseConfig(config: File): Unit = {
    import io.circe.parser
    import ConfigEncoderDecoders._
    val jsonConfig = bloop.config.toStr(config)
    val parsed = parser.parse(jsonConfig).getOrElse(sys.error("error parsing"))
    allDecoder.decodeJson(parsed) match {
      case Right(parsedConfig) =>
        // Compare stringified representation because `Array` equals uses reference equality
        Assert.assertEquals(allEncoder(parsedConfig).spaces4, jsonConfig)
      case Left(failure) => throw failure
    }
  }

  @Test def testEmptyConfigJson(): Unit = {
    parseConfig(File.empty)
  }

  @Test def testSimpleConfigJson(): Unit = {
    parseConfig(File.dummyForTests)
  }
}
