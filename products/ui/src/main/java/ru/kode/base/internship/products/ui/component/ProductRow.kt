package ru.kode.base.internship.products.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kode.base.internship.products.ui.R
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme

@Composable
internal fun DepositExpandableView(
  productName: String,
  balance: String,
  currency: Currency,
  isExpand: Boolean,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = painterResource(id = currency.resId),
      contentDescription = "icon",
      modifier = Modifier.padding(end = 16.dp)
    )
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      Text(
        text = productName,
        style = AppTheme.typography.body2)
      Spacer(
        modifier = Modifier.height(2.dp)
      )
      Text(
        text = "$balance ${currency.symbol}",
        style = AppTheme.typography.body2.copy(fontWeight = FontWeight.W400),
        color = AppTheme.colors.contendAccentSecondary
      )
    }
    IconButton(onClick = { }
    ) {
      Image(
        painter = painterResource(id = R.drawable.group),
        contentDescription = "icon",
        modifier = Modifier
          .width(40.dp)
          .height(28.dp)
      )
      Image(
        painter = if (isExpand) painterResource(id = R.drawable.expand_more) else painterResource(id = R.drawable.expand_less),
        null,
        Modifier
          .width(40.dp)
          .height(28.dp)
      )
    }
  }
}

@Composable
internal fun DepositView(
  productName: String,
  cardInfo: String,
  cardNumber: String,
  paymentSystem: PaymentSystem,
  isBlock: Boolean,
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Image(
      painter = painterResource(id = R.drawable.input),
      contentDescription = "icon",
      modifier = Modifier.padding(end = 16.dp)
    )
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      Text(
        text = productName,
        style = AppTheme.typography.body2)
      Spacer(
        modifier = Modifier.height(3.dp)
      )
      if (isBlock) {
        Text(
          text = cardInfo,
          style = AppTheme.typography.body2,
          color = AppTheme.colors.indicatorContendError
        )
      } else {
        Text(
          text = cardInfo,
          style = AppTheme.typography.body2,
          color = AppTheme.colors.textSecondary
        )
      }
    }
    IconButton(onClick = { }
    ) {
      Image(
        painter = painterResource(id = R.drawable.group),
        contentDescription = "icon",
        modifier = Modifier
          .width(40.dp)
          .height(28.dp)
      )
      Column {
        if (isBlock) {
          Text(
            text = cardNumber,
            modifier = Modifier.padding(12.dp, 2.dp, 3.dp, 1.dp),
            style = AppTheme.typography.caption3,
            color = AppTheme.colors.textSecondary
          )
        } else {
          Text(
            text = cardNumber,
            modifier = Modifier.padding(12.dp, 2.dp, 3.dp, 1.dp),
            style = AppTheme.typography.caption3,
            color = AppTheme.colors.textButton
          )
        }
        Image(
          painter = painterResource(id = paymentSystem.resId),
          contentDescription = paymentSystem.toString(),
          Modifier.padding(19.dp, 0.dp, 5.dp, 1.dp)
        )
      }
    }
  }
}


@Composable
internal fun SavingView(
  productName: String,
  balance: String,
  currency: Currency,
  rate: Number,
  expiredAt: String,
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Image(painter = painterResource(id = currency.resId),
      contentDescription = "icon",
      Modifier.padding(end = 16.dp))
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = productName,
          Modifier.weight(1f),
          style = AppTheme.typography.body2,
          color = AppTheme.colors.textPrimary,
        )
        Text(text = "Ставка $rate%",
          textAlign = TextAlign.End,
          style = AppTheme.typography.caption2,
          color = AppTheme.colors.textSecondary)
      }
      Spacer(modifier = Modifier.height(4.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "$balance ${currency.symbol}",
          Modifier.weight(1f),
          style = AppTheme.typography.body2,
          color = AppTheme.colors.contendAccentSecondary,
        )
        Text(modifier = Modifier.padding(end = 5.dp),
          text = "до $expiredAt",
          textAlign = TextAlign.End,
          style = AppTheme.typography.caption2,
          color = AppTheme.colors.textSecondary)
      }
    }
  }
}

@Composable
internal fun RowDivider() {
  Spacer(
    modifier = Modifier.height(8.dp)
  )
  Divider(
    modifier = Modifier.padding(start = 56.dp),
    color = AppTheme.colors.contendSecondary,
    thickness = 1.dp
  )
  Spacer(
    modifier = Modifier.height(8.dp)
  )
}


//₽ $ €
enum class Currency(val resId: Int, val symbol: String) {
  EUR(R.drawable.icon_eur, "€"),
  USD(R.drawable.icon_usd, "\$"),
  RUB(R.drawable.icon_rub, "₽"),
}

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

@Composable
@Preview(
  uiMode = UI_MODE_NIGHT_YES
)
fun DepositExpandablePreview() {
  AppTheme {
    Column {
      DepositExpandableView("Карта зарплатная",
        "457 334,00",
        Currency.RUB,
        true)
    }
  }
}

@Composable
@Preview(
  uiMode = UI_MODE_NIGHT_YES
)
fun DepositPreview() {
  AppTheme {
    Column {
      DepositView("Карта зарплатная",
        "Физическая",
        "0123",
        PaymentSystem.MASTER_CARD,
        false)

      DepositView("Дополнительная карта",
        "Заблокирована",
        "8435",
        PaymentSystem.VISA,
        true)
    }
  }
}

@Composable
@Preview(
  uiMode = UI_MODE_NIGHT_YES
)
fun SavingPreview() {
  AppTheme {
    Column {
      SavingView(productName = "Мой вклад",
        balance = "1 515 000,78",
        currency = Currency.RUB,
        7.65,
        "31.08.2024")
      RowDivider()
      SavingView(productName = "Накопительный",
        balance = "3 719,19",
        currency = Currency.USD,
        11.05,
        "31.08.2024")
      RowDivider()
      SavingView(productName = "EUR вклад",
        balance = "1 513,62",
        currency = Currency.EUR,
        8.65,
        "31.08.2026")
    }
  }
}

/*
DepositExpandableView(
  productName: String,
  balance: String,
  currency: Currency,
  isExpand: Boolean
)
DepositView(
  productName: String,
  cardInfo: String,
  cardNumber: String,
  paymentSystem: PaymentSystem,
  isBlock: Boolean,
)
SavingView(
  productName: String,
  balance: String,
  currency: Currency,
  rate: Number,
  expiredAt: String,
)
*/

