package com.acorns

import java.io.PrintStream

import scala.Console
import com.typesafe.config.ConfigFactory
import io.circe.Printer
import io.circe.generic.auto._
import io.circe.syntax._
import org.scalastyle.{FileSpec, Message, MessageHelper, StyleError}

import scala.collection.JavaConverters._

class CodeClimateIssuePrinter(ps: PrintStream) {
  private val printer = Printer.spaces2.copy(dropNullKeys = true)

  private val messageHelper = new MessageHelper(ConfigFactory.load())

  def printIssue[T <: FileSpec](msg: Message[T]): Unit = msg match {
    case se: StyleError[FileSpec] =>
      val errPosition = se.lineNumber.map(line => Position(line, se.column))
      val location = Location(path = se.fileSpec.name, positions = Seq(
        LinePosition(begin = errPosition, end = errPosition)
      ))
      val msg: String = messageHelper.message(se.key, se.args)
      val issue = Issue(location = location,
        description = String.format(msg, se.args.asJava),
        check_name = Some(se.clazz.getName),
        categories = Seq("Style"),
        severity = Some("major")
      )
      val jsonStr = printer.pretty(StyleErrorMessage(issue).asJson)
      ps.println(jsonStr)
    case _ => // skip
  }
}
