package views.forms

import views.html

/**
  * Created by llxmedici on 7/30/16.
  */
object FormHelpers {
  import views.html.helper.FieldConstructor
  implicit val customFields = FieldConstructor(html.forms.field_constructor.f)
}
