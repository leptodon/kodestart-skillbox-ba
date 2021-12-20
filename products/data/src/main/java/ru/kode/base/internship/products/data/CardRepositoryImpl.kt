package ru.kode.base.internship.products.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kode.base.internship.products.domain.CardRepository
import ru.kode.base.internship.products.domain.entity.CardDetails
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor() : CardRepository {

  override suspend fun fetchCards(isNew: Boolean) {}

  override fun cardDetails(id: Long): Flow<CardDetails> = flow {
    cardList.find { it.id == id }?.let { emit(it) }
  }

}