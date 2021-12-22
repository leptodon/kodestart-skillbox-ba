package ru.kode.base.internship.products.domain.entity

data class DepositDetails(
  val name:String?,
  val balance: Long,
  val currency: String,
  val rate: Double,
  val status: String,
  val closeDate: String,
)