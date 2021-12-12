package ru.kode.base.internship.products.ui

import androidx.compose.runtime.Immutable
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.products.data.entity.Deposit
import ru.kode.base.internship.products.data.entity.Saving


internal object ProductsMainScreen {
  class ViewIntents : BaseViewIntents() {
    val getData = intent(name = "getMockData")
    val dismissError = intent(name = "dismissError")
  }

  @Immutable
  data class ViewState(
    val depositLceState: LceState = LceState.None,
    var depositData: List<Deposit> = mutableListOf(),
    val savingLceState: LceState = LceState.None,
    var savingData: List<Saving> = mutableListOf(),
    val errorMessage: ErrorMessage? = null,
  )

  sealed class ErrorMessage {
    sealed class ValidationError : ErrorMessage() {
    }
  }
}