package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.domain.DepositRepository
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import javax.inject.Inject

class DepositRepositoryImpl @Inject constructor() : DepositRepository {
  private val stateFlowList = MutableStateFlow(generateDepositList())
  private val stateFlowDetails = MutableStateFlow(generateDepositDetails())

  override suspend fun fetchDeposits(isNew: Boolean) {
    if (isNew) {
      stateFlowList.emit(generateDepositList())
      stateFlowDetails.emit(generateDepositDetails())
    }
  }

  override val deposits: Flow<List<DepositDetails>> = stateFlowDetails

  override fun term(id: Long): Flow<DepositDetails> = flow {
    generateDepositDetails()
      .find { it.name == generateDepositList().find { depo -> depo.depositId == id }?.name }?.let { emit(it) }
  }

  private fun generateDepositList() = listOf(
    Deposit(
      depositId = 1,
      balance = (1000..9999).random().toLong(),
      status = "Активен",
      name = "Накопительный",
      currency = "USD",
    ),
    Deposit(
      depositId = 2,
      balance = (100000..1000000).random().toLong(),
      status = "Активен",
      name = "Сберегательный",
      currency = "RUB",
    ),
    Deposit(
      depositId = 3,
      balance = (1000..9999).random().toLong(),
      status = "Активен",
      name = "Счет в Eur",
      currency = "EUR",
    )
  )

  private fun generateDepositDetails() = listOf(
    DepositDetails(
      currency = "RUB",
      status = "Активен",
      name = "Накопительный",
      balance = (1000..9999).random().toLong(),
      rate = 7.5,
      closeDate = "2022-04-21T00:00:00Z",
    ),
    DepositDetails(
      currency = "USD",
      status = "Активен",
      name = "Сберегательный",
      balance = (1000..9999).random().toLong(),
      rate = 7.5,
      closeDate = "2022-04-21T00:00:00Z",
    ),
    DepositDetails(
      currency = "EUR",
      status = "Активен",
      name = "Счет в Eur",
      balance = (1000..9999).random().toLong(),
      rate = 7.5,
      closeDate = "2022-04-21T00:00:00Z",
    )
  )
}
