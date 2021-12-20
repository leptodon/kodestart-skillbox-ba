package ru.kode.base.internship.products.domain.entity

data class Deposit(
  val depositId: Long,
  val balance: Long,
  val currency: String,
  val status: String,
  val name: String,
)
