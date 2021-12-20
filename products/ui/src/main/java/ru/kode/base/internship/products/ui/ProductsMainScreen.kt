package ru.kode.base.internship.products.ui

import androidx.compose.runtime.Immutable
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.products.domain.entity.Account
import ru.kode.base.internship.products.domain.entity.CardDetails
import ru.kode.base.internship.products.domain.entity.Deposit


internal object ProductsMainScreen {
  class ViewIntents : BaseViewIntents() {
    val getData = intent(name = "getMockData")
    val dismissError = intent(name = "dismissError")
  }

  @Immutable
  data class ViewState(
    var cardsLceState: LceState = LceState.None,
    var accountsLceState: LceState = LceState.None,
    var depositsLceState: LceState = LceState.None,
    var accounts: List<Account> = emptyList(),
    var cardDetails: List<CardDetails> = emptyList(),
    var deposits: List<Deposit> = emptyList(),
    val errorMessage: ErrorMessage? = null,
  )

  sealed class ErrorMessage {
    sealed class ValidationError : ErrorMessage() {
    }
  }
}