@file:Suppress("MatchingDeclarationName") // intentionally contains several provider classes
package ru.kode.base.internship.products.data.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.logs.LogSqliteDriver
import ru.kode.base.core.annotations.ApplicationContext
import ru.kode.base.internship.products.data.ProductDataBase
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

internal class InMemoryAccountsDatabaseDriverProvider @Inject constructor(
  @ApplicationContext
  private val context: Context,
) : Provider<SqlDriver> {

  override fun get(): SqlDriver {
    return AndroidSqliteDriver(
      composeSqlDriverSchemes(ProductDataBase.Schema),
      context,
      // use in-memory database
      name = null
    ).let { driver ->
      if (ENABLE_LOGGING) {
        LogSqliteDriver(driver) { log ->
          Timber.e(log)
        }
      } else driver
    }
  }
}


// Would be nicer to have a better "merging" of feature-module databases.
// See https://github.com/cashapp/sqldelight/issues/1455 (workaround stolen from it)
private fun composeSqlDriverSchemes(vararg schemes: SqlDriver.Schema): SqlDriver.Schema =
  object : SqlDriver.Schema {
    override val version: Int = schemes.reduce { first, second ->
      if (first.version != second.version) {
        error("All schemes versions must be the same. first = $first, second: $second")
      }
      second
    }.version

    override fun create(driver: SqlDriver) =
      schemes.forEach {
        it.create(driver)
      }

    override fun migrate(driver: SqlDriver, oldVersion: Int, newVersion: Int) =
      schemes.forEach {
        it.migrate(driver, oldVersion, newVersion)
      }
  }

private const val ENABLE_LOGGING = false
