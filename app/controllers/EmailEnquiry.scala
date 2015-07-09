package controllers

import _root_.model.Customer
import play.api.libs.mailer.{Email, MailerPlugin}
import play.api.mvc.{Action, Results}
import play.api.Play.current

trait EmailEnquiry extends EmailEnquiryBase
{
  this: Results =>

  def emailOfEnquiry = Action { implicit request =>
    val customer = buildCustomerFromRequest(request)
    if ( customer.isDefined ) {
      ApplicationCake.customerService.save(customer.get)
    }
    handleEmailSending(customer)
  }

  override def sendEmails(customer: Customer)
  {
    val emailEnquiry = Email(
      "Enquiry To Sudo Stream",
      "No Reply Sudo Stream <" + INFO_SUDOSTREAM + ">",
      Seq("Info Sudo Stream <" + INFO_SUDOSTREAM + ">"),

      bodyText = Some(
        "Customer Name: " + customer.name + "\n" +
          "Email: " + customer.email + "\n" +
          "Phone: " + customer.phone + "\n" +
          "\nCustomer Message: \n\t" + customer.message + "\n\n\n"
      )
    )

    try {
      MailerPlugin.send(emailEnquiry)
    } catch {
      case ex: Exception =>
        println("oh oh : " + ex)
        ex.printStackTrace()
        throw ex
    }

  }

}
