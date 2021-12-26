package ru.kode.base.internship.products.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kode.base.internship.products.data.network.ProductApi
import ru.kode.base.internship.products.data.network.entity.response.CardDetailsResponse
import ru.kode.base.internship.products.domain.CardRepository
import ru.kode.base.internship.products.domain.entity.CardDetails
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class CardRepositoryImpl @Inject constructor(
  private val api: ProductApi,
  private val db: ProductDataBase,
) : CardRepository {

  val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  init {
    setupDb()
  }

  private fun setupDb() {
    CoroutineScope(Dispatchers.IO).launch {
      val cardDetailQueries = db.cardDetailQueries
      val cards = api.accountList().accounts
        .flatMap { it.cards }
        .map { it.card_id }
        .toList()
        .map { api.getCardsById(it.toLong()) }

      cardDetailQueries.transaction {
        cards.forEach { detail ->
          cardDetailQueries.insertCardDetail(
            status = detail.status,
            number = detail.number,
            accountId = detail.accountId,
            name = detail.name,
            expiredAt = detail.expiredAt,
            paymentSystem = detail.paymentSystem,
            id = detail.id
          )
        }
      }
    }
  }

  override suspend fun fetchCards(isNew: Boolean) {
    if (isNew) {
      setupDb()
    }
  }

  override fun cardDetails(id: Long): Flow<CardDetails> = getFromDb(id)

  private fun getFromDb(id: Long): Flow<CardDetails> {
    val execute = db.cardDetailQueries.getCardDetailById(id)
    if (execute.executeAsOneOrNull() != null) {
      val toExecuteFlow = execute.asFlow().mapToOne().map {
        CardDetails(
          id = it.id,
          paymentSystem = it.paymentSystem,
          expiredAt = it.expiredAt,
          name = it.name,
          accountId = it.accountId,
          number = it.number,
          status = it.status
        )
      }
      return toExecuteFlow
    } else {
      return flow { emit(toDomainModel(api.getCardsById(id))) }
    }
  }

  private fun toDomainModel(cardResponse: CardDetailsResponse): CardDetails {
    return CardDetails(
      accountId = 0,
      status = cardResponse.status,
      number = cardResponse.number,
      id = cardResponse.id,
      name = cardResponse.name,
      paymentSystem = cardResponse.paymentSystem,
      expiredAt = cardResponse.expiredAt,
    )
  }
}

//    val executeDetails = db.cardDetailQueries.getCardDetailById(id).executeAsOneOrNull()
//
//    return if (executeDetails != null) {
//        CardDetailsResponse(
//          id = executeDetails.id,
//          paymentSystem = executeDetails.paymentSystem,
//          expiredAt = executeDetails.expiredAt,
//          name = executeDetails.name,
//          accountId = executeDetails.accountId,
//          number = executeDetails.number,
//          status = executeDetails.status
//        )
//    } else {
//      null
//    }
//  }

