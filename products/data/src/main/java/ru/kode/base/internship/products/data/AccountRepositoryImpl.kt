package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.domain.AccountRepository
import ru.kode.base.internship.products.domain.entity.Account
import ru.kode.base.internship.products.domain.entity.Card
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor() : AccountRepository {

  override suspend fun fetchAccount(isNew: Boolean) {}
  override val accounts: Flow<List<Account>>
    get() = flow { emit(listOf(account)) }
}
