package ru.kode.base.internship.products.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.kode.base.internship.products.data.network.ProductApi
import ru.kode.base.internship.products.data.network.entity.response.DepositDetailsResponse
import ru.kode.base.internship.products.data.network.entity.response.DepositListResponse
import ru.kode.base.internship.products.domain.DepositRepository
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import timber.log.Timber
import javax.inject.Inject

internal class DepositRepositoryImpl @Inject constructor(
  private val api: ProductApi,
  private val db: ProductDataBase,
) : DepositRepository {
  private val stateFlowList = MutableStateFlow(getDepositsFromDb())

  private fun setupDB(){

  }

  override suspend fun fetchDeposits(isNew: Boolean) {
    val depositQueries: DepositQueries = db.depositQueries
    val depositDetailQueries: DepositDetailQueries = db.depositDetailQueries
    val depositListResponse = toDomainDepositListModel(api.depositList())
    val depositsIdList = api.depositList().deposits.map { it.depositId }.toList()
    val depositsListResponse = depositsIdList.map { api.getDepositsById(it) }

    if (isNew) {
      depositQueries.transaction {
        depositListResponse.forEach { deposit ->
          depositQueries.insertDeposit(
            status = deposit.status,
            name = deposit.name,
            balance = deposit.balance,
            currency = deposit.currency,
            depositId = deposit.depositId,
          )
        }
      }
      stateFlowList.emit(getDepositsFromDb())

      depositDetailQueries.transaction {
        depositsListResponse.forEachIndexed { index, depo ->
          depositDetailQueries.insertDepositDetail(
            depositId = depositsIdList[index],
            status = depo.status,
            name = depo.name ?: "",
            balance = depo.balance,
            currency = depo.currency,
            closeDate = depo.closeDate,
            rate = depo.rate
          )
        }
      }
    }
  }

  override val deposits: Flow<List<Deposit>> = stateFlowList

  override fun depositDetails(id: Long): Flow<DepositDetails> = getDetailsFromDb(id)

  private fun getDetailsFromDb(id: Long): Flow<DepositDetails> {
    val executeDetails = db.depositDetailQueries.getDepositDetailById(id)

    return if (executeDetails.executeAsOneOrNull() != null) {
      val toExecuteFlow = executeDetails.asFlow().mapToOne().map {
        DepositDetails(
          rate = it.rate,
          closeDate = it.closeDate,
          currency = it.currency,
          balance = it.balance,
          name = it.name,
          status = it.status,
        )
      }
      toExecuteFlow
    } else {
      flow { emit(toDomainDepositDetailsModel(api.getDepositsById(id))) }
    }
  }

  private fun getDepositsFromDb(): List<Deposit> {
    val executeList = db.depositQueries.getAllDeposit().executeAsList()
    val resultList = executeList.map {
      Deposit(
        currency = it.currency,
        balance = it.balance,
        name = it.name,
        status = it.status,
        depositId = it.depositId,
      )
    }
    return resultList
  }


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
}
