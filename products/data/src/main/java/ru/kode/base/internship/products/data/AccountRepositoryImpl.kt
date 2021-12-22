package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kode.base.internship.products.domain.AccountRepository
import ru.kode.base.internship.products.domain.entity.Account
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor() : AccountRepository {

  private val stateFlow = MutableStateFlow(generateAccount())

  override suspend fun fetchAccount(isNew: Boolean) {
    if (isNew) {
      stateFlow.emit(generateAccount())
    }
  }

  override val accounts: Flow<List<Account>> = stateFlow

  private fun generateAccount() = listOf(
    Account(
      accountId = 0,
      number = "1228568263446708",
      balance = (10000..999999).random().toLong(),
      currency = "EUR",
      status = "Активен",
      cards = listOf(1, 2)
    )
  )
}
