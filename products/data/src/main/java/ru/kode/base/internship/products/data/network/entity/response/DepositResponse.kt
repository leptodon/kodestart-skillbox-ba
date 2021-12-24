package ru.kode.base.internship.products.data.network.entity.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepositResponse(
  val depositId: Long,
  val balance: Long,
  val currency: String,
  val status: String,
  val name: String
)
