package ru.kode.base.internship.products.ui

import androidx.compose.runtime.Immutable
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.internship.core.domain.entity.LceState


internal object ProductsMainScreen {
  class ViewIntents : BaseViewIntents() {
    val dismissError = intent(name = "dismissError")
  }

  @Immutable
  data class ViewState(
    val productsLceState: LceState = LceState.None,
    val errorMessage: ErrorMessage? = null,
  )

  sealed class ErrorMessage {
    sealed class ValidationError : ErrorMessage() {
    }
  }
}