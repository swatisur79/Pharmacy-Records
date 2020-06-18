package Backend

import java.sql.Date

import play.api.libs.json.{JsValue, Json}

class Details (var id:Int, var name:String, var qty:Int, var date:String, var age:String, var rack:Int, var cost:Double){
  def asJsValue(): JsValue ={
    val drug: Map[String, JsValue] = Map(
      "Name" -> Json.toJson(name),
      "id" -> Json.toJson(id),
      "Quantity" -> Json.toJson(qty),
      "date"-> Json.toJson(date),
      "Age"->Json.toJson(age),
      "rack"->Json.toJson(rack),
      "cost"->Json.toJson(cost)
    )
    Json.toJson(drug)
  }
}

object Details{

  def short(input: String, maxLength: Int = 1): String = {
    var output = input
    if (output.length > maxLength) {
      output = output.slice(0, maxLength)
    }
    output
  }

  def combine(id:Int,name:String, qty:Int,date:String,age:String, rack:Int,cost:Double):Details={

    var d:Details= new Details(id,name,qty,date,short(age),rack,cost)
    d
  }
}


