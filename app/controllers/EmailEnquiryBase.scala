package controllers

import _root_.model.Customer
import play.api.Play
import play.api.mvc.Results._
import play.api.mvc.{AnyContent, Request, Result}

trait EmailEnquiryBase
{
  //  val sudoStreamInfoAddress = Play.current.configuration.getString("arty.info.email")
  val INFO_SUDOSTREAM = "andy@sudostream.io"

  def sendEmails(customer: Customer)

  def handleEmailSending(customer: Option[Customer]): Result =
  {
    if (customer.isDefined) {
      try {
        sendEmails(customer.get)
        Ok(views.html.thankyou())
      } catch {
        case ex: Exception =>
          Ok(views.html.issuesending())
      }
    } else {
      Ok(views.html.issuesending())
    }
  }

  def buildCustomerFromRequest(request: Request[AnyContent]): Option[Customer] =
  {
    var customerName = ""
    var customerPhone = ""
    var customerEmail = ""
    var customerMessage = ""
    var human = ""

    val formDetailsOption = request.body.asFormUrlEncoded
    if (formDetailsOption.isDefined) {
      val formDetails = formDetailsOption.get
      for (key <- formDetails.keys) {
        val formValue = formDetails(key)
        key match {
          case "name" => customerName = formValue.head
          case "email" => customerEmail = formValue.head
          case "phone" => customerPhone = formValue.head
          case "message" => customerMessage = formValue.head
          case "human" => human = formValue.head
          case _ =>
        }
      }
    }

    if (human == "5") {

      Some(
        new Customer(
          customerName,
          customerEmail,
          customerPhone,
          customerMessage,
          new java.util.Date())
      )

    } else {

      None

    }
  }

}
