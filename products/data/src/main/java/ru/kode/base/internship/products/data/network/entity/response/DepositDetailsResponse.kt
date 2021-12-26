package ru.kode.base.internship.products.data.network.entity.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepositDetailsResponse(
  val name:String?,
  val balance: Long,
  val currency: String,
  val rate: Double,
  val status: String,
  val closeDate: String,
)