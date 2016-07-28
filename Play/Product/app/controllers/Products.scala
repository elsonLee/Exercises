package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.Product

/**
  * Created by llxmedici on 7/28/16.
  */
@Singleton
class Products @Inject() extends Controller {
  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.list(products))
  }
}