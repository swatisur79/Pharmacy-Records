package Backend

import java.sql.{Connection, DriverManager, ResultSet}

class Database {


  val url = "jdbc:mysql://localhost/mysql?serverTimezone=UTC"
  val username: String = "root"
  val password: String = "Luckyfox=13579"

  var connection: Connection = DriverManager.getConnection(url, username, password)

  def table(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS drugs (id INT, Name TEXT, Quantity INT, Expiry_Date TEXT, Age TEXT, Rack_No INT, Cost DOUBLE(3,2))")
  }

  table()

  def addDrugs(d: Details): Unit = {

    val statement = connection.prepareStatement("INSERT INTO drugs VALUE (?,?,?,?,?,?,?)")

    statement.setInt(1, d.id)
    statement.setString(2, d.name)
    statement.setInt(3, d.qty)
    statement.setString(4, d.date)
    statement.setString(5, d.age)
    statement.setInt(6, d.rack)
    statement.setDouble(7, d.cost)

    statement.execute()
    println("the drug has been added to the list")
  }

  def delete(id:Int): Unit = {

    val statement1 = connection.prepareStatement("DELETE FROM drugs WHERE id=?")
    if(statement1==null) {
      println("the id doesn't exist")
    }
    else {
      statement1.setInt(1, id)
      statement1.execute()
    }
      }



def getDrugs(ID:Int):Details = {

  val statement = connection.prepareStatement("SELECT * FROM drugs WHERE id=?")

  if (statement == null) {
    println("the drug doesn't exist")
    null
  }
  else {
    statement.setInt(1, ID)

    val result: ResultSet = statement.executeQuery()

    val id = result.getInt("id")
    val title = result.getString("name")
    val qty = result.getInt("qty")
    val date = result.getString("exp")
    val ac = result.getString("ac")
    val rack = result.getInt("rack")
    val tt = result.getDouble("cost")

    val d: Details = new Details(id, title, qty, date, ac, rack, tt)
    d
  }
}

  def update(id:Int):Unit={
    val statement = connection.prepareStatement("UPDATE drugs SET Quantity=Quantity-1 WHERE id=?")
    val statement1=connection.prepareStatement("DELETE FROM drugs WHERE Quantity=0")
    statement.setInt(1, id)
    statement.execute()
    statement1.execute()
  }
}