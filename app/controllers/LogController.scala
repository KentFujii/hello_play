package controllers

import javax.inject._
import play.api._
import play.api.mvc._


import io.circe.generic.auto._
import io.circe.syntax._
import play.api.libs.circe.Circe
import models.Log

import queries.CreateLog

@Singleton
// class LogController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {
class LogController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) with Circe {
  def show(id: Long) = Action {
    Log.findById(id).fold(NotFound(s"Not found id=${id}")) { log => Ok(log.asJson) }
  }

  def list() = Action {
    Ok(Log.findAll().asJson)
  }

  def create() = Action(circe.tolerantJson[CreateLog]) { req =>
    if(Log.create(req.body) > 0) Ok("Success") else InternalServerError("SQLError")
  }

  def delete(id: Long) = Action {
    if(Log.deleteById(id) > 0) Ok("Success") else InternalServerError("SQLError")
  }
}
