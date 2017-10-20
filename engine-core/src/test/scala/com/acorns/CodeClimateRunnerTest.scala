package com.acorns

import java.io.File

import org.scalastyle.StyleError
import org.scalatest.Matchers


class CodeClimateRunnerTest extends org.scalatest.FreeSpec with Matchers {

  "CodeClimateEngine" - {
    "should call sclacheck and produce style errors" in {
      val msgs = ScalaStyleRunner.runCheckstyle(ScalastyleCodeClimateConfiguration("engine-core/src/test/resources/scalastyle_config.xml",
        directories = Seq("engine-core/src/test/resources")))

      msgs should not be empty

      val styleErrors = msgs.flatMap {
        case se: StyleError[_] => Seq(se)
        case _ => Seq.empty
      }

      styleErrors should have size 2
    }
  }
}
