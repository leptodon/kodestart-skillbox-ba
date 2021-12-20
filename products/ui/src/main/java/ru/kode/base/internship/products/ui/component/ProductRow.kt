package ru.kode.base.internship.products.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.sp
import ru.kode.base.internship.products.ui.R
import ru.kode.base.internship.products.ui.utils.getResByCurrency
import ru.kode.base.internship.products.ui.utils.getResByPaymentSystem
import ru.kode.base.internship.products.ui.utils.getSymbol
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme

@Composable
internal fun AccountList(
  isExpand: Boolean,
  balance: Long,
  currency: String,
  status: String,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = painterResource(id = currency.getResByCurrency()),
      contentDescription = "icon",
      modifier = Modifier.padding(end = 16.dp)
    )
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      Text(
        text = "Счет расчетный",
        style = AppTheme.typography.body2)
      Spacer(
        modifier = Modifier.height(2.dp)
      )
      Text(
        text = "$balance ${currency.getSymbol()}",
        style = AppTheme.typography.body2.copy(fontWeight = FontWeight.W400),
        color = AppTheme.colors.contendAccentSecondary
      )
    }
    IconButton(onClick = { }
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_card_background_40),
        contentDescription = "icon",
        modifier = Modifier
          .width(40.dp)
          .height(28.dp)
      )
      Image(
        painter = if (isExpand) painterResource(id = R.drawable.ic_expand_more_40) else painterResource(id = R.drawable.ic_expand_less_40),
        null,
        Modifier
          .width(40.dp)
          .height(28.dp)
      )
    }
  }
}

@Composable
internal fun CardView(
  number: String,
  status: String,
  name: String,
  paymentSystem: String,
  cardType: String,
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Image(
      painter = painterResource(id = R.drawable.ic_input_40),
      contentDescription = "icon",
      modifier = Modifier.padding(end = 16.dp)
    )
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      Text(
        text = name,
        style = AppTheme.typography.body2)
      Spacer(
        modifier = Modifier.height(3.dp)
      )
      Text(
        text = if (cardType=="physical") "Физическая" else "Виртуальная",
        style = AppTheme.typography.body2,
        color = if (status == "DEACTIVATED") AppTheme.colors.indicatorContendError else AppTheme.colors.textSecondary
      )
    }
    IconButton(onClick = { }
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_card_background_40),
        contentDescription = "icon",
        modifier = Modifier
          .width(40.dp)
          .height(28.dp)
      )
      Column {
        Text(
          text = number,
          modifier = Modifier.padding(12.dp, 0.dp, 3.dp, 1.dp),
          style = AppTheme.typography
            .caption2.copy(
              fontSize = 10.sp,
              fontWeight = FontWeight.W400
            ),
          color = if (status == "DEACTIVATED") AppTheme.colors.textSecondary else AppTheme.colors.textButton
        )
        Image(
          painter = painterResource(id = paymentSystem.getResByPaymentSystem()),
          contentDescription = paymentSystem,
          Modifier.padding(19.dp, 0.dp, 5.dp, 1.dp)
        )
      }
    }
  }
}


@Composable
internal fun DepositView(
  balance: Long,
  currency: String,
  name: String,
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Image(painter = painterResource(id = currency.getResByCurrency()),
      contentDescription = "icon",
      Modifier.padding(end = 16.dp))
    Column(
      modifier = Modifier
        .weight(1f)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = name,
          Modifier.weight(1f),
          style = AppTheme.typography.body2,
          color = AppTheme.colors.textPrimary,
        )
        Text(text = "Ставка ",
          textAlign = TextAlign.End,
          style = AppTheme.typography.caption2,
          color = AppTheme.colors.textSecondary)
      }
      Spacer(modifier = Modifier.height(4.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "$balance ${currency.getSymbol()}",
          Modifier.weight(1f),
          style = AppTheme.typography.body2,
          color = AppTheme.colors.contendAccentSecondary,
        )
        Text(modifier = Modifier.padding(end = 5.dp),
          text = "до ", //$expiredAt
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

@Composable
@Preview(
  uiMode = UI_MODE_NIGHT_YES
)
fun AccountsPreview() {
  AppTheme {
    Column {
      AccountList(
        true,
        457_334,
        "RUB",
        "DEACTIVE")
    }
  }
}

@Composable
@Preview(
  uiMode = UI_MODE_NIGHT_YES
)
fun CardsPreview() {
  AppTheme {
    Column {
      CardView(
        "8435",
        "ACTIVE",
        "Карта зарплатная",
        "Visa",
        "physical"
      )

      CardView(
        "5738",
        "DEACTIVE",
        "Дополнительная карта",
        "MasterCard",
        "digital"
      )
    }
  }
}

@Composable
@Preview(
  uiMode = UI_MODE_NIGHT_YES
)
fun DepositsPreview() {
  AppTheme {
    Column {
      DepositView(
        currency = "RUB",
        name = "Мой вклад",
        balance = 1_515_000_78,
      )
      RowDivider()
      DepositView(
        currency = "USD",
        name = "Накопительный",
        balance = 3_719_19,
      )
      RowDivider()
      DepositView(
        currency = "EUR",
        name = "EUR вклад",
        balance = 1_513_62,
      )
    }
  }
}