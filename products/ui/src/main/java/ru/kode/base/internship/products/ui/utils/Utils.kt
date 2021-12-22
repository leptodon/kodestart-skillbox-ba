package ru.kode.base.internship.products.ui.utils

import ru.kode.base.internship.products.ui.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/** Конвертируем полученый код валюты в символ */
fun String.getSymbol(): String {
  val map = mutableMapOf(
    "RUB" to "₽",
    "USD" to "$",
    "EUR" to "€",
    "BRL" to "R$",
    "CUP" to "₱",
    "CZK" to "Kč",
    "DKK" to "kr",
    "INR" to "Rs",
    "GBP" to "£",
    "KPW" to "₩",
    "JPY" to "¥"
  )
  return if (map.contains(this)) map[this]!! else ""
}

/** Возвращаем иконку в соответствии с переданым кодом валюты */
fun String.getResByCurrency(): Int {
  val map = mutableMapOf(
    "RUB" to R.drawable.ic_rub_40,
    "USD" to R.drawable.ic_usd_40,
    "EUR" to R.drawable.ic_eur_40,
  )
  return if (map.contains(this)) map[this]!! else R.drawable.ic_base_currency_icon_40
}

/** Возвращаем иконку в соответствии с переданой платежной системой */
fun String.getResByPaymentSystem(): Int {
  val map = mutableMapOf(
    "VISA" to R.drawable.visa,
    "MASTERCARD" to R.drawable.mastercard,
  )
  return if (map.contains(this)) map[this]!! else 0
}

/** Конвертация даты к виду 31.08.2024 */
fun String.toDate():String = OffsetDateTime.parse(this).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))