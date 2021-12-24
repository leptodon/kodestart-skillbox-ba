package ru.kode.base.internship.products.data.network.entity.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepositListResponse(
  val deposits: List<DepositResponse>,
)