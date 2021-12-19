package ru.kode.base.internship.products.data.entity

import ru.kode.base.internship.products.data.R

enum class Currency(val resId: Int, val symbol: String) {
  EUR(R.drawable.icon_eur, "€"),
  USD(R.drawable.icon_usd, "\$"),
  RUB(R.drawable.icon_rub, "₽"),
}