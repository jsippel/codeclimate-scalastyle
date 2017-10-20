package com

package object acorns {
  case class ScalastyleCodeClimateConfiguration(
    config: String,
    exclude_paths: Seq[String] = Seq.empty,
    directories: Seq[String] = Seq.empty
  )

  sealed trait IssueSchema extends Product with Serializable
  case class Position(line: Int, column: Option[Int] = None) extends IssueSchema
  case class LinePosition(begin: Option[Position] = None, end: Option[Position] = None) extends IssueSchema
  case class Location(path: String, positions: Seq[LinePosition] = Seq.empty) extends IssueSchema
  case class Issue(location: Location, description: String, check_name: Option[String] = None,
    severity: Option[String] = None, remediation_points: Option[Int] = Some(50000), fingerprint: Option[String] = None,
    `type`: String=  "issue", categories: Seq[String] = Seq.empty) extends IssueSchema
  case class StyleErrorMessage(issue: Issue)

}
