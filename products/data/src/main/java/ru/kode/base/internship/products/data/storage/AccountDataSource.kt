package ru.kode.base.internship.products.data.storage

import kotlinx.coroutines.flow.Flow
import ru.kode.base.internship.products.data.AccountEntity


internal interface AccountDataSource {
  suspend fun getAccountById(id: Long): AccountEntity

  fun getAllAccounts(): Flow<List<AccountEntity>>

  suspend fun deleteAccountById(id: Long)

  suspend fun insertAccount(accountId: Long, number: String, balance: Long, currency: String, status: String, cards: String)
}