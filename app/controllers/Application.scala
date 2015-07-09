package controllers

import play.api.mvc._

object Application extends Controller with EmailEnquiry {

  def index = Action {
    Ok(views.html.index("Sudo Stream"))
  }

  def aboutus = Action {
    Ok(views.html.aboutus())
  }

  def emailEnquiry = Action { implicit request =>
    val customer = buildCustomerFromRequest(request)
    if ( customer.isDefined ) {
      ApplicationCake.customerService.save(customer.get)
    }
    handleEmailSending(customer)
  }

}