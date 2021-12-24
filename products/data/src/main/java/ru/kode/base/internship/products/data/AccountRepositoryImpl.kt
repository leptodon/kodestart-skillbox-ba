package ru.kode.base.internship.products.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kode.base.internship.products.data.network.ProductApi
import ru.kode.base.internship.products.data.network.entity.response.AccountListResponse
import ru.kode.base.internship.products.domain.AccountRepository
import ru.kode.base.internship.products.domain.entity.Account
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
  private val api: ProductApi,
  private val db: ProductDataBase,
) : AccountRepository {

  val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  private val stateFlow = MutableStateFlow(getFromDb())

  override suspend fun fetchAccount(isNew: Boolean) {
    val accountQueries: AccountQueries = db.accountQueries
    val accountListResponse = toDomainModel(api.accountList())
    if (isNew) {
      accountQueries.transaction {
        accountListResponse.forEach { account ->
          accountQueries.insertAccount(
            accountId = account.accountId,
            status = account.status,
            number = account.number,
            balance = account.balance,
            currency = account.currency,
            cards = account.cards.toString()
          )
        }
      }
      stateFlow.emit(getFromDb())
    }
  }

  override val accounts: Flow<List<Account>> = stateFlow

  private fun getFromDb():List<Account>{
    val executeList = db.accountQueries.getAllAccount().executeAsList()
    val resultList = executeList.map {
      Account(
        cards = moshi.parseList(it.cards.orEmpty()) ?: emptyList(),
        number = it.number,
        balance = it.balance,
        currency = it.currency,
        accountId = it.accountId,
        status = it.status
      )
    }
    return resultList
  }

  private fun toDomainModel(response: AccountListResponse): List<Account> = response.accounts.map {
    val account = Account(
      cards = it.cards.map { card -> card.card_id },
      number = it.number,
      balance = it.balance,
      status = it.status,
      currency = it.currency,
      accountId = it.accountId,
    )
    account
  }
}

inline fun <reified T> Moshi.parseList(jsonString: String): List<T>? {
  return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(jsonString)
}