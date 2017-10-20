package com.acorns

import java.nio.charset.Charset
import java.nio.file.{FileSystems, Files}

import io.circe.generic.auto._
import io.circe.parser.parse

import scala.collection.JavaConverters._

object CodeClimateEngine extends App {
  private val defaultStyleConfigurationPath = "/usr/src/app/scalastyle_config.xml"

  val configFilePath = FileSystems.getDefault.getPath("/config.json")
  val configFile = Files.readAllLines(configFilePath, Charset.defaultCharset()).asScala.toSeq.mkString("\n")

  val config = parse(configFile).right.flatMap(_.as[ScalastyleCodeClimateConfiguration]).right.toOption.getOrElse(
    ScalastyleCodeClimateConfiguration(defaultStyleConfigurationPath, directories = Seq("/code"))
  )

  val ccPrinter = new CodeClimateIssuePrinter(Console.out)

  ScalaStyleRunner.runCheckstyle(config) foreach ccPrinter.printIssue
}
