package Backend

import java.sql.Date

import com.corundumstudio.socketio.listener.{ConnectListener, DataListener}
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import play.api.libs.json.{JsValue, Json}

class PharmacyServer {

  var data:Database=new Database

    var usernameToSocket: Map[String, SocketIOClient] = Map()
    var socketToUsername: Map[SocketIOClient, String] = Map()

    val config: Configuration = new Configuration {
      setHostname("0.0.0.0")
      setPort(8080)
    }

    val server: SocketIOServer = new SocketIOServer(config)

//    server.addConnectListener(new ConnectionListener(this))
//  server.addEventListener("next", classOf[String], new Page(this))
    server.addEventListener("add_drug", classOf[String], new AddDrugListener(this))
    server.addEventListener("search_drug", classOf[Int], new SearchDrugListener(this))
    server.addEventListener("delete_drug", classOf[Int], new DeleteDrugListener(this))

    server.start()

    def tasksJSON(id:Int): String = {
      val tasks:Details = data.getDrugs(id)
      val tasksJSON: JsValue = tasks.asJsValue()
      Json.stringify(Json.toJson(tasksJSON))
    }

  }

  object PharmacyServer {
    def main(args: Array[String]): Unit = {
      new PharmacyServer()
    }
  }

  class Page(server: PharmacyServer) extends DataListener[String] {

  override def onData(socket: SocketIOClient, username: String, ackRequest: AckRequest): Unit = {
    server.socketToUsername += (socket -> username)
    server.usernameToSocket += (username -> socket)
    socket.sendEvent("select")

    }

  }


  class AddDrugListener(server: PharmacyServer) extends DataListener[String] {
    print(1)

    override def onData(socket: SocketIOClient, taskJSON: String, ackRequest: AckRequest): Unit = {

      println(1)
      val task: JsValue = Json.parse(taskJSON)
      val id: Int = (task \ "id").as[Int]
      val name: String = (task \ "name").as[String]
      val qty:Int=(task\"qty").as[Int]
      val exp:String=(task\"exp").as[String]
      val age:String=(task\"ac").as[String]
      val rack:Int=(task\"rack").as[Int]
      val cost:Double=(task\"cost").as[Double]

      val d:Details=new Details(id,name,qty,exp,age,rack,cost)

      server.data.addDrugs(d)
    }

  }


  class SearchDrugListener(server: PharmacyServer) extends DataListener[Int] {

    override def onData(socket: SocketIOClient, taskId: Int, ackRequest: AckRequest): Unit = {
      server.data.getDrugs(taskId)
      server.server.getBroadcastOperations.sendEvent("all_tasks", server.tasksJSON(taskId))
    }
  }

class DeleteDrugListener(server: PharmacyServer)extends DataListener[Int]{
  override def onData(client: SocketIOClient, data: Int, ackRequest: AckRequest): Unit = {
    server.data.delete(data)
    println("Deleted drug:")
    server.server.getBroadcastOperations.sendEvent("all_tasks",server.tasksJSON(data))
  }
}

