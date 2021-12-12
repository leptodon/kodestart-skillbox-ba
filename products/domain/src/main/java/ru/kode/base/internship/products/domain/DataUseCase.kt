package ru.kode.base.internship.products.domain

import kotlinx.coroutines.CoroutineScope
import ru.kode.base.internship.core.domain.BaseUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.kode.base.internship.products.data.entity.Currency
import ru.kode.base.internship.products.data.entity.Deposit
import ru.kode.base.internship.products.data.entity.PaymentSystem
import ru.kode.base.internship.products.data.entity.Saving
import javax.inject.Inject

interface DataUseCase {
  suspend fun getDepositData()
  val depositState: Flow<LceState>

  suspend fun getSavingData()
  val savingState: Flow<List<Saving>>
}

internal class DataUseCaseImpl @Inject constructor(
  scope: CoroutineScope,
) : BaseUseCase<DataUseCaseImpl.State>(scope, State()), DataUseCase {

  data class State(
    val depositState: LceState = LceState.None,
    val savingState: LceState = LceState.None,
  )

  override suspend fun getDepositData() {
    setState { copy(depositState = LceState.Loading) }
    try {
      setState { copy(depositState = LceState.Content) }
    } catch (e: Exception) {
      setState { copy(depositState = LceState.Error(e.message)) }
    }
  }

  override val depositState: Flow<LceState>
    get() = stateFlow.map { it.depositState }.distinctUntilChanged()

  override suspend fun getSavingData() {
    setState { copy(depositState = LceState.Loading) }
    try {
      setState { copy(depositState = LceState.Content) }
    } catch (e: Exception) {
      setState { copy(depositState = LceState.Error(e.message)) }
    }
  }

  override val savingState: Flow<List<Saving>>
    get() = stateFlow.map { savingList }.distinctUntilChanged()


}

private val depositList = mutableListOf(
  Deposit("Карта зарплатная",
    "Физическая",
    "0123",
    PaymentSystem.MASTER_CARD,
    false),
  Deposit("Дополнительная карта",
    "Заблокирована",
    "8435",
    PaymentSystem.VISA,
    true)
)

private val savingList = mutableListOf(
  Saving("Мой вклад",
    "1 515 000,78",
    Currency.RUB,
    7.65,
    "31.08.2024"),
  Saving("Накопительный",
    "3 719,19",
    Currency.USD,
    11.05,
    "31.08.2024"),
  Saving("EUR вклад",
    "1 513,62",
    Currency.EUR,
    8.65,
    "31.08.2026")
)