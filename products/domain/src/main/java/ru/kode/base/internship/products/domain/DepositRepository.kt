package ru.kode.base.internship.products.domain

import kotlinx.coroutines.flow.Flow
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails

interface DepositRepository {
  suspend fun fetchDeposits(isNew: Boolean)
  val deposits: Flow<List<Deposit>>
  fun depositDetails(id: Long): Flow<DepositDetails>
}