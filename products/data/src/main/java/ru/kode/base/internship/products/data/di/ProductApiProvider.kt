package ru.kode.base.internship.products.data.di

import retrofit2.Retrofit
import ru.kode.base.internship.products.data.network.ProductApi
import javax.inject.Inject
import javax.inject.Provider

internal class ProductApiProvider @Inject constructor(
  private val retrofit: Retrofit,
) : Provider<ProductApi> {
  override fun get(): ProductApi {
    return retrofit.create(ProductApi::class.java)
  }
}