package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

class Json extends Controller {

  def hello = Action {
    Ok(Json.obj("message" -> "hello"))
  }

  def bye = Action {
    Ok(Json.obj("message" -> "bye"))
  }

}
