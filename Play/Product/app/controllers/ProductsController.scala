package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.mvc.Flash
import models.Product

/**
  * Created by llxmedici on 7/28/16.
  */
class ProductsController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def list = Action {
    implicit request =>
      val products = Product.findAll
      Ok(views.html.list(products))
  }

  def show(ean: Long) = Action {
    implicit request =>
      Product.findByEan(ean).map {
        product =>
          Ok(views.html.details(product))
      }.getOrElse(NotFound)
  }

  def newProduct = Action {
    implicit request =>
      val flash = request.flash
      val form = if (flash.get("error").isDefined)
        productForm.bind(flash.data)
      else
        productForm
      Ok(views.html.forms.edit_product(form))
  }


  def save = Action {
    implicit request =>
      val newProductForm = productForm.bindFromRequest()

      newProductForm.fold(

        hasErrors = {
          form => Redirect(routes.ProductsController.newProduct())
            .flashing(Flash(form.data) + ("error" -> Messages("validation.errors")))
          //form => Redirect(routes.Products.newProduct())
        },

        success = {
          newProduct =>
            Product.add(newProduct)
            val message = Messages("products.new.success", newProduct.name)
            Redirect(routes.ProductsController.show(newProduct.ean))
              .flashing("success" -> message)
            //Redirect(routes.Products.show(newProduct.ean))
        }

      )

  }

  private val productForm: Form[Product] = Form(
    mapping(
      "ean" -> longNumber.verifying(
        "validation.ean.duplicate", Product.findByEan(_).isEmpty),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
      )(Product.apply)(Product.unapply)
  )

}