package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import services.MemberService

import java.util.concurrent.TimeoutException
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Member extends Controller {

  /*def insert = Action { implicit request => {
      MemberService.add(MemberRow(0, "Damien", "Suhard", "test@test.com"))
      Ok(Json.obj("message" -> "insert OK"))
    }
  }*/


  def all: Action[AnyContent] = Action.async { implicit request =>
    val futureMember = MemberService.getAll
    futureMember.map { result =>
      println(result)
      Ok(Json.obj("message" -> "all OK"))
    }.recover {
      case ex: TimeoutException => InternalServerError(ex.getMessage)
    }
  }

}
