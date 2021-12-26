package ru.kode.base.internship.products.data.di

import com.squareup.sqldelight.db.SqlDriver
import ru.kode.base.internship.products.data.ProductDataBase
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider


internal class ProductDatabaseProvider @Inject constructor(
  @Named(IN_MEMORY_DB_DRIVER)
  private val driver: SqlDriver,
) : Provider<ProductDataBase> {
  override fun get(): ProductDataBase {
    return ProductDataBase(driver = driver)
  }
}

private const val IN_MEMORY_DB_DRIVER = "db-product"
