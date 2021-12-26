package ru.kode.base.internship.products.data.storage

import com.squareup.sqldelight.db.SqlDriver
import ru.kode.base.core.util.ToothpickModuleBindings
import ru.kode.base.internship.core.data.storage.preferences.di.AppPreferencesBindings
import ru.kode.base.internship.products.data.di.InMemoryAccountsDatabaseDriverProvider
import toothpick.config.Module

object SqlDelightBindings : ToothpickModuleBindings {
  override fun bindInto(module: Module) {
    module.bind(SqlDriver::class.java)
      .withName(IN_MEMORY_DB_DRIVER)
      .toProvider(InMemoryAccountsDatabaseDriverProvider::class.java)
      .providesSingletonInScope()

    AppPreferencesBindings.bindInto(module)
  }
}

private const val IN_MEMORY_DB_DRIVER = "db-product"
