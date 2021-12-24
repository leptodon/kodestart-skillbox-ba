package ru.kode.base.internship.products.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kode.base.internship.products.data.network.entity.response.AccountListResponse
import ru.kode.base.internship.products.data.network.entity.response.CardDetailsResponse
import ru.kode.base.internship.products.data.network.entity.response.CardResponse
import ru.kode.base.internship.products.data.network.entity.response.DepositDetailsResponse
import ru.kode.base.internship.products.data.network.entity.response.DepositListResponse

internal interface ProductApi {
  @GET("27774161/api/core/account/list")
  suspend fun accountList(@Query("__example") android:String): AccountListResponse

  @GET("27774161/api/core/deposit/list")
  suspend fun depositList(@Query("__example") android:String): DepositListResponse

  @GET("27774161/api/core/card/{cardId}")
  suspend fun getCardsById(@Path("cardId") id: String): CardDetailsResponse

  @GET("27774161/api/core/deposit/{depositId}")
  suspend fun getDepositsById(@Path("depositId") id: Long): DepositDetailsResponse
}