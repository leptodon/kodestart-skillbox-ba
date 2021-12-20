package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.domain.DepositRepository
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import javax.inject.Inject

class DepositRepositoryImpl @Inject constructor() : DepositRepository {
  override suspend fun fetchDeposits(isNew: Boolean) {}

  override val deposits: Flow<List<Deposit>>
    get() = flow { emit(depositList) }

  override fun term(id: Long): Flow<DepositDetails> = flow {
    depositDetails
      .find { it.name == depositList.find { depo -> depo.depositId == id }?.name }?.let { emit(it) }
  }
}
