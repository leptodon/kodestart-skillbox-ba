package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.domain.CardRepository
import ru.kode.base.internship.products.domain.entity.CardDetails
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor() : CardRepository {

  private val stateFlow = MutableStateFlow(generateCardList())

  override suspend fun fetchCards(isNew: Boolean) {
    if (isNew) {
      stateFlow.emit(generateCardList())
    }
  }

  override fun cardDetails(id: Long): Flow<CardDetails> = flow {
    generateCardList().find { it.id == id }?.let { emit(it) }
  }

  private fun generateCardList() = listOf(
    CardDetails(
      id = 2,
      accountId = 0,
      number = (1000..9999).random().toString(),
      status = "DEACTIVE",
      name = "Кредитная карта",
      paymentSystem = "MASTERCARD",
      expiredAt = "2022-04-21T00:00:00Z"
    ),
    CardDetails(
      id = 1,
      accountId = 0,
      number = (1000..9999).random().toString(),
      status = "ACTIVE",
      name = "Дебетовая карта",
      paymentSystem = "VISA",
      expiredAt = "2022-04-21T00:00:00Z"
    )
  )

}