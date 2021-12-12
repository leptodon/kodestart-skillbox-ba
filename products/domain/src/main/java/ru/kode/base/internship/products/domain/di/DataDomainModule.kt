package ru.kode.base.internship.products.domain.di

import ru.kode.base.internship.products.domain.DataUseCase
import ru.kode.base.internship.products.domain.DataUseCaseImpl
import toothpick.config.Module

class DataDomainModule: Module() {
  init {
    bind(DataUseCase::class.java)
      .to(DataUseCaseImpl::class.java)
      .singletonInScope()
  }
}