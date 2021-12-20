package ru.kode.base.internship.products.ui

import android.util.Log
import ru.dimsuz.unicorn.coroutines.MachineDsl
import ru.kode.base.core.coroutine.BasePresenter
import ru.kode.base.internship.products.domain.ProductUseCase
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewIntents
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewState
import ru.kode.base.internship.routing.AppFlow
import javax.inject.Inject

internal class ProductsMainPresenter @Inject constructor(
  private val productUseCase: ProductUseCase,
  private val coordinator: AppFlow.Coordinator,
) : BasePresenter<ViewState, ViewIntents, Unit>() {

  override fun MachineDsl<ViewState, Unit>.buildMachine() {
    initial = ViewState() to { productUseCase.fetchAccounts() }

    onEach(intent(ViewIntents::getData)) {
      action { state, newState, _ ->
        productUseCase.fetchAccounts(true)
        productUseCase.fetchCards(true)
        productUseCase.fetchDeposits(true)
      }
    }

    onEach(productUseCase.fetchAccountState) {
      transitionTo { state, payload ->
        state.copy(
          accountsLceState = payload
        )
      }
    }

    onEach(productUseCase.fetchCardState) {
      transitionTo { state, payload ->
        state.copy(
          cardsLceState = payload
        )
      }
    }

    onEach(productUseCase.fetchDepositState) {
      transitionTo { state, payload ->
        state.copy(
          depositsLceState = payload
        )
      }
    }

    onEach(productUseCase.resultCardDetailsList()) {
      transitionTo { state, payload ->
        state.copy(
          cardDetails = payload
        )
      }
    }

    onEach(productUseCase.accountsList) {
      transitionTo { state, payload ->
        state.copy(
          accounts = payload
        )
      }
    }

    onEach(productUseCase.depositsList) {
      transitionTo { state, payload ->
        state.copy(
          deposits = payload
        )
      }
    }
  }
}