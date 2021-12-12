package ru.kode.base.internship.products.ui

import ru.kode.base.internship.products.ui.component.Currency
import ru.kode.base.internship.products.ui.component.PaymentSystem


val depositList = mutableListOf(
  Deposit("Карта зарплатная",
    "Физическая",
    "0123",
    PaymentSystem.MASTER_CARD,
    false),
  Deposit("Дополнительная карта",
    "Заблокирована",
    "8435",
    PaymentSystem.VISA,
    true)
)

val savingList = mutableListOf(
  Saving("Мой вклад",
    "1 515 000,78",
    Currency.RUB,
    7.65,
    "31.08.2024"),
  Saving("Накопительный",
    "3 719,19",
    Currency.USD,
    11.05,
    "31.08.2024"),
  Saving("EUR вклад",
    "1 513,62",
    Currency.EUR,
    8.65,
    "31.08.2026")
)

val contentMap = mutableMapOf(
  "deposit" to depositList,
  "saving" to savingList
)
