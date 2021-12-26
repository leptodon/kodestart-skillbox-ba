package ru.kode.base.internship.products.ui

import androidx.compose.runtime.Immutable
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.products.domain.entity.Account
import ru.kode.base.internship.products.domain.entity.AccountWithCards
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import ru.kode.base.internship.products.domain.entity.DepositWithDetails


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
    var accountWithCardDetails: List<AccountWithCards> = emptyList(),
    var depositsWithDetails: List<DepositWithDetails> = emptyList(),
  )

  sealed class ErrorMessage {
    sealed class ValidationError : ErrorMessage() {
    }
  }
}