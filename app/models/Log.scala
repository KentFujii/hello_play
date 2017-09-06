package models

import io.circe.Encoder
import io.circe.generic.auto._
import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId
import queries.CreateLog

case class Log(id: Long, logType: LogType, content: String)

abstract class LogType(val value: Int)

object LogType {
  case object NotFoundError extends LogType(1)
  case object Exception extends LogType(2)
  case object FormatError extends LogType(3)

  val values = NotFoundError :: Exception :: FormatError :: Nil
  def find(value: Int): Option[LogType] = values.find(_.value == value)

  implicit val typeBinder: TypeBinder[LogType] = TypeBinder.int.map { i => find(i).get }

  implicit val encoder: Encoder[LogType] = Encoder.forProduct2("name", "value") { u =>
    (u.toString, u.value)
  }
}


import scalikejdbc._

object Log extends SkinnyCRUDMapperWithId[Long, Log] {
  override def idToRawValue(id: Long) = id
  override def rawValueToId(value: Any) = value.toString.toLong

  override def defaultAlias = createAlias("l")
  override def extract(rs: WrappedResultSet, n: ResultName[Log]) = autoConstruct(rs, n)

  def create(log: CreateLog)(implicit session: DBSession = autoSession): Long = {
    createWithAttributes(
      'logType -> log.logType,
      'content -> log.content
    )
  }
}
