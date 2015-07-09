package controllers

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoCollection
import controllers.dao.customer.CustomerRepositoryMongoComponent
import controllers.service.customer.DefaultCustomerServiceComponent

object ApplicationCake
{
  val customerServiceComponent = new DefaultCustomerServiceComponent with CustomerRepositoryMongoComponent
  {
    override val customerCollection: MongoCollection = MongoConnection()("sudostream")("customer")
  }
  val customerService = customerServiceComponent.customerService
}
