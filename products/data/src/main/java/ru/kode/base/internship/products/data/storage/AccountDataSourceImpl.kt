package ru.kode.base.internship.products.data.storage

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kode.base.internship.products.data.AccountEntity
import ru.kode.base.internship.products.data.ProductDataBase
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
  private val dataBase: ProductDataBase,
) : AccountDataSource {

  private val queries = dataBase.accountQueries

  override suspend fun getAccountById(id: Long): AccountEntity {
    return withContext(Dispatchers.IO) {
      queries.getAccountById(id).executeAsOne()
    }
  }

  override fun getAllAccounts(): Flow<List<AccountEntity>> {
    return queries.getAllAccount().asFlow().mapToList()
  }

  override suspend fun deleteAccountById(id: Long) {
    withContext(Dispatchers.IO) {
      queries.deleteAccountById(id)
    }
  }

  override suspend fun insertAccount(accountId: Long, number: String, balance: Long, currency: String, status: String, cards: String) {
    withContext(Dispatchers.IO) {
      queries.insertAccount(
        accountId = accountId,
        number = number,
        balance = balance,
        currency = currency,
        status = status,
        cards = cards
      )
    }
  }

}