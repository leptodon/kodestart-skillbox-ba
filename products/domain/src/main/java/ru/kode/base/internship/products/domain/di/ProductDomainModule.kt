package ru.kode.base.internship.products.domain.di

import ru.kode.base.internship.products.domain.AccountRepository
import ru.kode.base.internship.products.domain.ProductUseCase
import ru.kode.base.internship.products.domain.ProductUseCaseImpl
import toothpick.config.Module

class ProductDomainModule: Module() {
  init {
    bind(ProductUseCase::class.java)
      .to(ProductUseCaseImpl::class.java)
      .singletonInScope()

  }
}