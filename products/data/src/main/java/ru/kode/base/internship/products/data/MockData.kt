package ru.kode.base.internship.products.data

import ru.kode.base.internship.products.domain.entity.Account
import ru.kode.base.internship.products.domain.entity.CardDetails
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails


val account = Account(
  accountId = 0,
  number = "1228568263446708",
  balance = (10000..999999).random().toLong(),
  currency = "EUR",
  status = "Активен",
  cards = listOf(1, 2)
)

val cardList = listOf(
  CardDetails(
    id = 2,
    accountId = 0,
    number = (1000..9999).random().toString(),
    status = "DEACTIVE",
    name = "Кредитная карта",
    paymentSystem = "MASTERCARD",
    expiredAt = "2022-04-21T00:00:00Z"
  ),
  CardDetails(
    id = 1,
    accountId = 0,
    number = (1000..9999).random().toString(),
    status = "ACTIVE",
    name = "Дебетовая карта",
    paymentSystem = "VISA",
    expiredAt = "2022-04-21T00:00:00Z"
  )
)

val depositList = listOf(
  Deposit(
    depositId = 1,
    balance = (1000..9999).random().toLong(),
    status = "Активен",
    name = "Накопительный",
    currency = "USD",
  ),
  Deposit(
    depositId = 2,
    balance = (100000..1000000).random().toLong(),
    status = "Активен",
    name = "Сберегательный",
    currency = "RUB",
  ),
  Deposit(
    depositId = 3,
    balance = (1000..9999).random().toLong(),
    status = "Активен",
    name = "Счет в Eur",
    currency = "EUR",
  )
)

val depositDetails = listOf(
  DepositDetails(
    currency = "RUB",
    status = "Активен",
    name = "Накопительный",
    balance = 125134,
    rate = 7.5,
    closeDate = "2022-04-21T00:00:00Z",
  ),
  DepositDetails(
    currency = "RUB",
    status = "Активен",
    name = "Сберегательный",
    balance = 125134,
    rate = 7.5,
    closeDate = "2022-04-21T00:00:00Z",
  ),
  DepositDetails(
    currency = "RUB",
    status = "Активен",
    name = "Счет в Eur",
    balance = 125134,
    rate = 7.5,
    closeDate = "2022-04-21T00:00:00Z",
  )
)