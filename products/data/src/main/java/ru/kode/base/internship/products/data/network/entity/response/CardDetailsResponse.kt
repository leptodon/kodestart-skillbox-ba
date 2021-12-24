package ru.kode.base.internship.products.data.network.entity.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardDetailsResponse(
  val id: Long,
  val accountId: Long,
  val number: String,
  val expiredAt: String,
  val paymentSystem: String,
  val status: String,
  val name: String,
)
