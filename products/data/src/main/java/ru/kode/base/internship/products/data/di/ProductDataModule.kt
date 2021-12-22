package ru.kode.base.internship.products.data.di

import ru.kode.base.internship.products.data.AccountRepositoryImpl
import ru.kode.base.internship.products.data.CardRepositoryImpl
import ru.kode.base.internship.products.data.DepositRepositoryImpl
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
  }
}