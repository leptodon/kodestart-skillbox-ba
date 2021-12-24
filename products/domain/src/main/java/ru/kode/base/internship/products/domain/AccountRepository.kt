package ru.kode.base.internship.products.domain

import kotlinx.coroutines.flow.Flow
import ru.kode.base.internship.products.domain.entity.Account

interface AccountRepository {
  suspend fun fetchAccount(isNew: Boolean)
  val accountsMock: Flow<List<Account>>
  val accounts: Flow<List<Account>>
}