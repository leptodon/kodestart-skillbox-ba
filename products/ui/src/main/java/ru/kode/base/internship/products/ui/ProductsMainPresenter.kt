package ru.kode.base.internship.products.ui

import ru.dimsuz.unicorn.coroutines.MachineDsl
import ru.kode.base.core.coroutine.BasePresenter
import ru.kode.base.internship.products.domain.DataUseCase
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewIntents
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewState
import ru.kode.base.internship.routing.AppFlow
import javax.inject.Inject

internal class ProductsMainPresenter @Inject constructor(
  private val dataUseCase: DataUseCase,
  private val coordinator: AppFlow.Coordinator,
) : BasePresenter<ViewState, ViewIntents, Unit>() {

  override fun MachineDsl<ViewState, Unit>.buildMachine() {
    initial = ViewState() to null

    onEach(dataUseCase.savingState) {
      transitionTo { state, _ ->
        state.copy(
          savingData = state.savingData
        )
      }
    }

    onEach(intent(ViewIntents::getData)) {
      action { _, newState, _ ->
        if (newState.errorMessage == null) {
          executeAsync {
            newState.savingData = dataUseCase.getSavingData()
          }
        }
      }
    }

    onEach(dataUseCase.depositState) {
      transitionTo { state, _ ->
        state.copy(
          depositData = state.depositData
        )
      }
    }

    onEach(intent(ViewIntents::getData)) {
      action { _, newState, _ ->
        if (newState.errorMessage == null) {
          executeAsync {
            newState.depositData = dataUseCase.getDepositData()
          }
        }
      }
    }
  }

}