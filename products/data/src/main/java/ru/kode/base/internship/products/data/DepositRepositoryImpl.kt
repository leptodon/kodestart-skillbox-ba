package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.data.network.ProductApi
import ru.kode.base.internship.products.data.network.entity.response.DepositDetailsResponse
import ru.kode.base.internship.products.data.network.entity.response.DepositListResponse
import ru.kode.base.internship.products.domain.DepositRepository
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import javax.inject.Inject

internal class DepositRepositoryImpl @Inject constructor(
  private val api: ProductApi,
) : DepositRepository {
  private val stateFlowList = MutableStateFlow(generateDepositList())

  override suspend fun fetchDeposits(isNew: Boolean) {
    if (isNew) {
      stateFlowList.emit(generateDepositList())
    }
  }

  override val deposits: Flow<List<Deposit>> = flow { emit(toDomainDepositListModel(api.depositList("android"))) }

  override fun depositDetails(id: Long): Flow<DepositDetails> =
    flow { emit(toDomainDepositDetailsModel(api.getDepositsById(id))) }

  private fun toDomainDepositListModel(depositListResponse: DepositListResponse): List<Deposit> {
    return depositListResponse.deposits.map {
      Deposit(
        name = it.name,
        balance = it.balance,
        currency = it.currency,
        status = it.status,
        depositId = it.depositId
      )
    }
  }

  private fun toDomainDepositDetailsModel(depositDetailsResponse: DepositDetailsResponse): DepositDetails {
    return DepositDetails(
      status = depositDetailsResponse.status,
      currency = depositDetailsResponse.currency,
      balance = depositDetailsResponse.balance,
      name = depositDetailsResponse.name,
      rate = depositDetailsResponse.rate,
      closeDate = depositDetailsResponse.closeDate)
  }

  private fun generateDepositList() = listOf(
    Deposit(
      depositId = 1L,
      balance = (1000..9999).random().toLong(),
      status = "Активен",
      name = "Накопительный",
      currency = "USD",
    ),
    Deposit(
      depositId = 2L,
      balance = (100000..1000000).random().toLong(),
      status = "Активен",
      name = "Сберегательный",
      currency = "RUB",
    ),
    Deposit(
      depositId = 3L,
      balance = (1000..9999).random().toLong(),
      status = "Активен",
      name = "Счет в Eur",
      currency = "EUR",
    )
  )

  private fun generateDepositDetails(id: Long): DepositDetails {
    when (id) {
      1L -> return DepositDetails(
        currency = "RUB",
        status = "Активен",
        name = "Накопительный",
        balance = (1000..9999).random().toLong(),
        rate = 7.5,
        closeDate = "2022-04-21T00:00:00Z",
      )
      2L -> return DepositDetails(
        currency = "USD",
        status = "Активен",
        name = "Сберегательный",
        balance = (1000..9999).random().toLong(),
        rate = 7.5,
        closeDate = "2022-04-21T00:00:00Z",
      )
      3L -> return DepositDetails(
        currency = "EUR",
        status = "Активен",
        name = "Счет в Eur",
        balance = (1000..9999).random().toLong(),
        rate = 7.5,
        closeDate = "2022-04-21T00:00:00Z",
      )
      else -> return DepositDetails(
        currency = "EUR",
        status = "Активен",
        name = "Счет в Eur",
        balance = (1000..9999).random().toLong(),
        rate = 7.5,
        closeDate = "2022-04-21T00:00:00Z",
      )
    }
  }
}
