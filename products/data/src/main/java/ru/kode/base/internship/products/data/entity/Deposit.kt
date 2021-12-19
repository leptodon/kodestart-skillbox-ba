package ru.kode.base.internship.products.data.entity

data class Deposit(
  val productName: String,
  val cardInfo: String,
  val cardNumber: String,
  val paymentSystem: PaymentSystem,
  val isBlock: Boolean,
)
