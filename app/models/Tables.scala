package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Address.schema ++ Member.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Address
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param address Database column address SqlType(VARCHAR), Length(100,true)
   *  @param cp Database column cp SqlType(VARCHAR), Length(10,true), Default(None)
   *  @param city Database column city SqlType(VARCHAR), Length(50,true), Default(None)
   *  @param idMember Database column id_member SqlType(INT) */
  case class AddressRow(id: Int, address: String, cp: Option[String] = None, city: Option[String] = None, idMember: Int)
  /** GetResult implicit for fetching AddressRow objects using plain SQL queries */
  implicit def GetResultAddressRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]]): GR[AddressRow] = GR{
    prs => import prs._
    AddressRow.tupled((<<[Int], <<[String], <<?[String], <<?[String], <<[Int]))
  }
  /** Table description of table address. Objects of this class serve as prototypes for rows in queries. */
  class Address(_tableTag: Tag) extends Table[AddressRow](_tableTag, "address") {
    def * = (id, address, cp, city, idMember) <> (AddressRow.tupled, AddressRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(address), cp, city, Rep.Some(idMember)).shaped.<>({r=>import r._; _1.map(_=> AddressRow.tupled((_1.get, _2.get, _3, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column address SqlType(VARCHAR), Length(100,true) */
    val address: Rep[String] = column[String]("address", O.Length(100,varying=true))
    /** Database column cp SqlType(VARCHAR), Length(10,true), Default(None) */
    val cp: Rep[Option[String]] = column[Option[String]]("cp", O.Length(10,varying=true), O.Default(None))
    /** Database column city SqlType(VARCHAR), Length(50,true), Default(None) */
    val city: Rep[Option[String]] = column[Option[String]]("city", O.Length(50,varying=true), O.Default(None))
    /** Database column id_member SqlType(INT) */
    val idMember: Rep[Int] = column[Int]("id_member")

    /** Foreign key referencing Member (database name id_member) */
    lazy val memberFk = foreignKey("id_member", id, Member)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Address */
  lazy val Address = new TableQuery(tag => new Address(tag))

  /** Entity class storing rows of table Member
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param firstname Database column firstname SqlType(VARCHAR), Length(255,true)
   *  @param lastname Database column lastname SqlType(VARCHAR), Length(255,true)
   *  @param email Database column email SqlType(VARCHAR), Length(75,true) */
  case class MemberRow(id: Int, firstname: String, lastname: String, email: String)
  /** GetResult implicit for fetching MemberRow objects using plain SQL queries */
  implicit def GetResultMemberRow(implicit e0: GR[Int], e1: GR[String]): GR[MemberRow] = GR{
    prs => import prs._
    MemberRow.tupled((<<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table member. Objects of this class serve as prototypes for rows in queries. */
  class Member(_tableTag: Tag) extends Table[MemberRow](_tableTag, "member") {
    def * = (id, firstname, lastname, email) <> (MemberRow.tupled, MemberRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(firstname), Rep.Some(lastname), Rep.Some(email)).shaped.<>({r=>import r._; _1.map(_=> MemberRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column firstname SqlType(VARCHAR), Length(255,true) */
    val firstname: Rep[String] = column[String]("firstname", O.Length(255,varying=true))
    /** Database column lastname SqlType(VARCHAR), Length(255,true) */
    val lastname: Rep[String] = column[String]("lastname", O.Length(255,varying=true))
    /** Database column email SqlType(VARCHAR), Length(75,true) */
    val email: Rep[String] = column[String]("email", O.Length(75,varying=true))
  }
  /** Collection-like TableQuery object for table Member */
  lazy val Member = new TableQuery(tag => new Member(tag))
}
