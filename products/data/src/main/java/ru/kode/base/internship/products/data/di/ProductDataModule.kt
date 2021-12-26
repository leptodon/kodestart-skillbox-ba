package ru.kode.base.internship.products.data.di

import ru.kode.base.internship.products.data.AccountRepositoryImpl
import ru.kode.base.internship.products.data.CardRepositoryImpl
import ru.kode.base.internship.products.data.DepositRepositoryImpl
import ru.kode.base.internship.products.data.ProductDataBase
import ru.kode.base.internship.products.data.network.ProductApi
import ru.kode.base.internship.products.data.storage.AccountDataSource
import ru.kode.base.internship.products.data.storage.AccountDataSourceImpl
import ru.kode.base.internship.products.domain.AccountRepository
import ru.kode.base.internship.products.domain.CardRepository
import ru.kode.base.internship.products.domain.DepositRepository
import toothpick.config.Module

class ProductDataModule: Module() {
  init {
    bind(AccountRepository::class.java)
      .to(AccountRepositoryImpl::class.java)
      .singletonInScope()

    bind(CardRepository::class.java)
      .to(CardRepositoryImpl::class.java)
      .singletonInScope()

    bind(DepositRepository::class.java)
      .to(DepositRepositoryImpl::class.java)
      .singletonInScope()

    bind(ProductApi::class.java)
      .toProvider(ProductApiProvider::class.java)
      .providesSingletonInScope()

    bind(AccountDataSource::class.java)
      .to(AccountDataSourceImpl::class.java)
      .singletonInScope()

    bind(ProductDataBase::class.java)
      .toProvider(ProductDatabaseProvider::class.java)
      .providesSingletonInScope()
  }
}