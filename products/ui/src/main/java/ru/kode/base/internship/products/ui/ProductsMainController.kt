package ru.kode.base.internship.products.ui

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewIntents
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewState
import ru.kode.base.internship.products.ui.component.Currency
import ru.kode.base.internship.products.ui.component.DepositView
import ru.kode.base.internship.products.ui.component.PaymentSystem
import ru.kode.base.internship.products.ui.component.RowDivider
import ru.kode.base.internship.products.ui.component.SavingView
import ru.kode.base.internship.ui.core.uikit.KodeBankBaseController
import ru.kode.base.internship.ui.core.uikit.component.PrimaryButton
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme

internal class ProductsMainController : KodeBankBaseController<ViewState, ViewIntents>() {

  override fun createConfig(): Config<ViewIntents> {
    return object : Config<ViewIntents> {
      override val intentsConstructor = ::ViewIntents
    }
  }

  private val depositList = mutableListOf(
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

  private val savingList = mutableListOf(
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

  private val contentMap = mutableMapOf(
    "deposit" to depositList,
    "saving" to savingList
  )

  @Composable
  override fun ScreenContent(state: ViewState) {
    Screen(contentMap, "457 334,00 P")
  }

  @Composable
  fun Screen(content: Map<String, List<Products>>, balance: String) {
    val depositContent = content["deposit"]?.filterIsInstance<Deposit>() ?: emptyList()
    val savingsContent = content["saving"]?.filterIsInstance<Saving>() ?: emptyList()

    Column(
      modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
      StatusBar()
      DepositList(
        depositsList = depositContent,
        balance = balance
      )
      Spacer(
        modifier = Modifier.height(16.dp))
      SavingList(
        savingsList = savingsContent
      )
      PrimaryButton(
        modifier = Modifier
          .padding(16.dp, 16.dp, 16.dp, 24.dp)
          .fillMaxWidth(),
        text = stringResource(id = R.string.button_text),
        onClick = {}
      )
    }
  }

  @Composable
  fun StatusBar() {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 30.dp, 16.dp, 16.dp),
      horizontalArrangement = Arrangement.Center) {
      Text(
        text = stringResource(R.string.title),
        color = AppTheme.colors.textPrimary,
        style = AppTheme.typography.bodyMedium,
      )
    }
  }

  @Composable
  fun DepositList(depositsList: List<Deposit>, balance: String) {
    var expanded by remember { mutableStateOf(true) }

    Column(
      modifier = Modifier
        .background(color = AppTheme.colors.backgroundSecondary)
        .padding(16.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Счета",
          modifier = Modifier
            .padding(bottom = 17.dp)
            .fillMaxSize(),
          style = AppTheme.typography.bodySemibold,
          color = AppTheme.colors.contendTertiary)
      }
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Image(
          painter = painterResource(id = R.drawable.icon_rub),
          contentDescription = "")
        Column(
          modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp)
        ) {
          Text(
            text = "Счет расчетный",
            style = AppTheme.typography.body2
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = balance,
            style = AppTheme.typography.body2
          )
        }
        IconButton(
          onClick = { expanded = !expanded }) {
          Image(
            painter = if (expanded) painterResource(id = R.drawable.expand_more) else painterResource(id = R.drawable.expand_less),
            null
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
      if (expanded) {
        Column {
          depositsList.takeIf { it.isNotEmpty() }?.forEachIndexed { index, it ->
            DepositView(
              productName = it.productName,
              cardInfo = it.cardInfo,
              cardNumber = it.cardNumber,
              paymentSystem = it.paymentSystem,
              isBlock = it.isBlock
            )
            if (index != depositsList.size - 1) RowDivider()
          }
        }
      }
    }
  }

  @Composable
  private
  fun SavingList(savingsList: List<Saving>) {
    Column(
      Modifier
        .animateContentSize(
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
          )
        )
        .background(color = AppTheme.colors.backgroundSecondary)
        .padding(16.dp)
    ) {
      Text(
        text = "Вклады",
        modifier = Modifier
          .padding(bottom = 16.dp)
          .fillMaxSize(),
        style = AppTheme.typography.bodySemibold,
        color = AppTheme.colors.contendTertiary
      )
      savingsList.takeIf { it.isNotEmpty() }?.forEachIndexed { index, it ->
        SavingView(
          productName = it.productName,
          balance = it.balance,
          currency = it.currency,
          rate = it.rate,
          expiredAt = it.expiredAt
        )
        if (index != savingsList.size - 1) RowDivider()
      }
    }
  }

  @Composable
  @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
  fun PreviewScreen() {
    AppTheme {
      Screen(contentMap, "457 334,00 P")
    }
  }
}

open class Products {
  open val productName: String = ""
}

data class DepositExpandable(
  override val productName: String,
  val balance: String,
  val currency: Currency,
  val isExpand: Boolean,
) : Products()

data class Deposit(
  override val productName: String,
  val cardInfo: String,
  val cardNumber: String,
  val paymentSystem: PaymentSystem,
  val isBlock: Boolean,
) : Products()

data class Saving(
  override val productName: String,
  val balance: String,
  val currency: Currency,
  val rate: Number,
  val expiredAt: String,
) : Products()