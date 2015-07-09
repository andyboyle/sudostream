package controllers.dao.customer

import java.util.Date

import com.mongodb.BasicDBObject
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoCollection
import model._
import org.joda.time.{LocalDateTime, LocalDate}

trait CustomerRepositoryMongoComponent extends CustomerRepositoryComponent
{

  val customerCollection: MongoCollection

  def customerLocator = new CustomerLocatorMongoDb(customerCollection)

  def customerUpdater = new CustomerUpdaterMongoDb(customerCollection)

  class CustomerLocatorMongoDb(val customersCollection: MongoCollection) extends CustomerLocator
  {

    def retrieveAllCustomers(): Seq[Customer] =
    {
      println("Retrieving customers now ...")
      val customersCursor = customersCollection.find().sort(MongoDBObject("enquirydatetime" -> -1))
      val allCustomers = for {customerObj <- customersCursor} yield {
        println("the cust is : " + customerObj)
        val customerName = customerObj.get("name").asInstanceOf[String]
        val customerEmail = customerObj.get("email").asInstanceOf[String]
        val customerPhone = customerObj.get("phone").asInstanceOf[String]
        val message = customerObj.get("message").asInstanceOf[String]
        val initialMessageDate = customerObj.get("initialContactDateTime").asInstanceOf[java.util.Date]

        Customer(customerName,
          customerEmail,
          customerPhone,
          message,
          initialMessageDate)
      }
      allCustomers.toSeq
    }

  }

  class CustomerUpdaterMongoDb(val customerCollection: MongoCollection) extends CustomerUpdater
  {
    def save(customer: Customer): Unit =
    {
      try {

        val customerObj = MongoDBObject(
          "name" -> customer.name,
          "phone" -> customer.phone,
          "email" -> customer.email,
          "message" -> customer.message,
          "initialContactDateTime" -> customer.initialContactDateTime
        )

        customerCollection += customerObj
      }
      catch {
        case e: Exception => {
          println("Error is : " + e)
          println("Error is : " + e.getMessage)
        }
      }
    }
  }


}
