package ru.kode.base.internship.products.data.network.entity.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardResponse(
  val card_id: String,
  val number: String,
  val status: String,
  val name: String,
  val payment_system: String,
  val card_type: String,
)
