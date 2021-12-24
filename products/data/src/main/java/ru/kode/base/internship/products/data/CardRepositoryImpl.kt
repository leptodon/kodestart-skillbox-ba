package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.data.network.ProductApi
import ru.kode.base.internship.products.data.network.entity.response.CardDetailsResponse
import ru.kode.base.internship.products.data.network.entity.response.CardResponse
import ru.kode.base.internship.products.domain.CardRepository
import ru.kode.base.internship.products.domain.entity.CardDetails
import timber.log.Timber
import javax.inject.Inject

internal class CardRepositoryImpl @Inject constructor(
  private val api: ProductApi,
) : CardRepository {

  private val stateFlow = MutableStateFlow(generateCardList())

  override suspend fun fetchCards(isNew: Boolean) {
    if (isNew) {
      stateFlow.emit(generateCardList())
    }
  }

  override fun cardDetailsMock(id: String): Flow<CardDetails> = flow {
    generateCardList().find { it.id == id }?.let { emit(it) }
  }

  override fun cardDetails(id: String): Flow<CardDetails> = flow {
    emit(toDomainModel(api.getCardsById(id)))
  }

  private fun toDomainModel(cardResponse: CardDetailsResponse): CardDetails {
   val card = CardDetails(
      accountId = 0,
      status = cardResponse.status,
      number = cardResponse.number,
      id = cardResponse.id.toString(),
      name = cardResponse.name,
      paymentSystem = cardResponse.paymentSystem,
      expiredAt = cardResponse.expiredAt,
    )
    return card
  }

  private fun generateCardList() = listOf(
    CardDetails(
      id = "66f6e46c-f6a1-4af8-a1bd-49666bc01304",
      accountId = 0,
      number = (1000..9999).random().toString(),
      status = "DEACTIVE",
      name = "Кредитная карта",
      paymentSystem = "MASTERCARD",
      expiredAt = "2022-04-21T00:00:00Z"
    ),
    CardDetails(
      id = "66f6e46c-f6a1-4af8-a1bd-49666bc01304",
      accountId = 0,
      number = (1000..9999).random().toString(),
      status = "ACTIVE",
      name = "Дебетовая карта",
      paymentSystem = "VISA",
      expiredAt = "2022-04-21T00:00:00Z"
    )
  )

}