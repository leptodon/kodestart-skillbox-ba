package ru.kode.base.internship.products.domain.entity

data class CardDetails(
  val id: Long,
  val accountId: Long,
  val number: String,
  val expiredAt: String,
  val paymentSystem: String,
  val status: String,
  val name: String,
)
