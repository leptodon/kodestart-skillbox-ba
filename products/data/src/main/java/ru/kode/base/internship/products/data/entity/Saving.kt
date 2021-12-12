package ru.kode.base.internship.products.data.entity

data class Saving(
  val productName: String,
  val balance: String,
  val currency: Currency,
  val rate: Number,
  val expiredAt: String,
)