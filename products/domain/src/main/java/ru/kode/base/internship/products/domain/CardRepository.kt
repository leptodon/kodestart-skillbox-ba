package ru.kode.base.internship.products.domain

import kotlinx.coroutines.flow.Flow
import ru.kode.base.internship.products.domain.entity.CardDetails

interface CardRepository {
  suspend fun fetchCards(isNew: Boolean)
  fun cardDetailsMock(id: String): Flow<CardDetails>
  fun cardDetails(id: String): Flow<CardDetails>
}