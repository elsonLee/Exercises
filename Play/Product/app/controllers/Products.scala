package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{I18nSupport, MessagesApi}
import models.Product

/**
  * Created by llxmedici on 7/28/16.
  */
class Products @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.list(products))
  }
}