package ru.kode.base.internship.products.data.entity

import ru.kode.base.internship.products.data.R

enum class PaymentSystem(val resId: Int) {
  VISA(R.drawable.visa) {
    override fun toString(): String {
      return "visa"
    }
  },
  MASTER_CARD(R.drawable.mastercard) {
    override fun toString(): String {
      return "mastercard"
    }
  }
}