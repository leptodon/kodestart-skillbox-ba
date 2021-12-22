package ru.kode.base.internship.products.domain

import kotlinx.coroutines.flow.Flow
import ru.kode.base.internship.products.domain.entity.CardDetails

interface CardRepository {
  suspend fun fetchCards(isNew: Boolean)
  fun cardDetails(id: Long): Flow<CardDetails>
}