package services


import play.api.Play
import slick.driver.JdbcProfile


import slick.driver.MySQLDriver.api._
import models.Tables
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}


object MemberService {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  def getAll: Future[Seq[Tables.MemberRow]] = {
    dbConfig.db.run(Tables.Member.result)
  }



  /*def add(member: MemberRow) = {
    Member ++= Seq(
      (members.length + 1, member.firstname, member.lastname, member.email)
    )
  }*/
}
