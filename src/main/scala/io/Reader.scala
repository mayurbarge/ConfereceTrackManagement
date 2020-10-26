package io

import scalaz.effect.IO

object Reader {
  def getTokensFromFile(filePath: String) = {
    val regex = "(.*) ([0-9]*)min".r

    val io = IO {
      val source = scala.io.Source.fromFile(filePath)
      source.getLines.toStream
    }

    for {
      inputLine <- io.unsafePerformIO.toList
      tokens <- regex.findAllMatchIn(inputLine)
    } yield {
      (tokens.group(1), tokens.group(2))
    }
  }
}

