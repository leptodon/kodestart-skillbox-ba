package ru.kode.base.internship.products.ui

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
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewIntents
import ru.kode.base.internship.products.ui.ProductsMainScreen.ViewState
import ru.kode.base.internship.products.ui.component.CardView
import ru.kode.base.internship.products.ui.component.DepositView
import ru.kode.base.internship.products.ui.component.RowDivider
import ru.kode.base.internship.products.ui.utils.getResByCurrency
import ru.kode.base.internship.products.ui.utils.getSymbol
import ru.kode.base.internship.products.ui.utils.toDate
import ru.kode.base.internship.ui.core.uikit.KodeBankBaseController
import ru.kode.base.internship.ui.core.uikit.component.PrimaryButton
import ru.kode.base.internship.ui.core.uikit.theme.AppTheme

internal class ProductsMainController : KodeBankBaseController<ViewState, ViewIntents>() {

  override fun createConfig(): Config<ViewIntents> {
    return object : Config<ViewIntents> {
      override val intentsConstructor = ::ViewIntents
    }
  }

  @Composable
  override fun ScreenContent(state: ViewState) {
    SwipeRefresh(
      state = rememberSwipeRefreshState(isRefreshing = state.accountsLceState == LceState.Loading),
      onRefresh = {
        intents.getData()
      },
      indicator = { iState, trigger ->
        SwipeRefreshIndicator(
          state = iState,
          refreshTriggerDistance = trigger,
          backgroundColor = AppTheme.colors.backgroundPrimary
        )
      }
    ) {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
      ) {
        StatusBar()

        if (!state.accountWithCardDetails.isNullOrEmpty()) {
          AccountList(state)
        }

        Spacer(
          modifier = Modifier.height(16.dp))

        if (!state.accountWithCardDetails.isNullOrEmpty()) {
          DepositsList(state)
        }

        PrimaryButton(
          modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 24.dp)
            .fillMaxWidth(),
          text = stringResource(id = R.string.button_text),
          onClick = {}
        )
      }
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
  fun AccountList(state: ViewState) {
    var expanded by remember { mutableStateOf(true) }
    val accounts = state.accountWithCardDetails?.get(0)!!

    Column(
      modifier = Modifier
        .background(color = AppTheme.colors.backgroundSecondary)
        .padding(16.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = stringResource(id = R.string.account),
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
          painter = painterResource(id = accounts.currency.getResByCurrency()),
          contentDescription = "")
        Column(
          modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp)
        ) {
          Text(
            text = stringResource(id = R.string.main_account),
            style = AppTheme.typography.body2
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "${accounts.balance} ${accounts.currency.getSymbol()}",
            style = AppTheme.typography.body2
          )
        }
        IconButton(
          onClick = { expanded = !expanded }) {
          Image(
            painter = if (expanded) painterResource(id = R.drawable.ic_expand_more_40) else painterResource(id = R.drawable.ic_expand_less_40),
            null
          )
        }
      }
      Spacer(modifier = Modifier.height(16.dp))
      if (expanded) {
        Column {
          accounts.takeIf { it.cards.isNotEmpty() }?.cards?.forEachIndexed { index, it ->
            CardView(
              name = it.name,
              number = it.number.takeLast(4),
              paymentSystem = it.paymentSystem,
              status = it.status,
              cardType = stringResource(id = R.string.phisical)
            )
            if (index != accounts.cards.size.minus(1)) RowDivider()
          }
        }
      }
    }
  }

  @Composable
  private
  fun DepositsList(state: ViewState) {
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
        text = stringResource(R.string.deposits),
        modifier = Modifier
          .padding(bottom = 16.dp)
          .fillMaxSize(),
        style = AppTheme.typography.bodySemibold,
        color = AppTheme.colors.contendTertiary
      )
      state.depositsWithDetails.takeIf { it.isNotEmpty() }?.forEachIndexed { index, it ->
        DepositView(
          name = it.name,
          balance = it.balance,
          currency = it.currency,
          rate = it.details.rate,
          expire = it.details.closeDate.toDate()
        )
        if (index != state.depositsWithDetails.size.minus(1)) RowDivider()
      }
    }
  }
}