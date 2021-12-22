package ru.kode.base.internship.products.ui

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import ru.kode.base.core.model.ComponentConfig
import ru.kode.base.core.model.ScreenKey

@Parcelize
object ProductsMainKey : ScreenKey() {
  @Suppress("INAPPLICABLE_IGNORED_ON_PARCEL")
  @IgnoredOnParcel
  override val componentConfig = ComponentConfig(
    presenterClass = ProductsMainPresenter::class.java,
    controllerClass = ProductsMainController::class.java,
  )
}