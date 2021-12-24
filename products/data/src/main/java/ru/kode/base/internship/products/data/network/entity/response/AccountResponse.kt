package ru.kode.base.internship.products.data.network.entity.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountResponse(
  val accountId: Long,
  val number: String,
  val balance: Long,
  val currency: String,
  val status: String,
  val cards: List<CardResponse>,
)