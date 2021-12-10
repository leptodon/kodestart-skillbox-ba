package ru.kode.base.internship.products.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ru.kode.base.internship.ui.core.uikit.KodeBankBaseController
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewState
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewIntents

internal class ProductsMainController : KodeBankBaseController<ViewState, ViewIntents>() {

  override fun createConfig(): Config<ViewIntents> {
    return object : Config<ViewIntents> {
      override val intentsConstructor = ::ViewIntents
    }
  }

  @Composable
  override fun ScreenContent(state: ViewState) {
    AppTheme {
      Text(text = "Some Text")
    }
  }
}