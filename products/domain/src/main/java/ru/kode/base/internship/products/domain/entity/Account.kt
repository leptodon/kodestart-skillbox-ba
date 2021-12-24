package ru.kode.base.internship.products.domain.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
  val accountId: Long,
  val number: String,
  val balance: Long,
  val currency: String,
  val status: String,
  val cards: List<String>,
)