package ru.kode.base.internship.products.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.kode.base.internship.core.domain.BaseUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.products.domain.entity.Account
import ru.kode.base.internship.products.domain.entity.AccountWithCards
import ru.kode.base.internship.products.domain.entity.CardDetails
import ru.kode.base.internship.products.domain.entity.Deposit
import ru.kode.base.internship.products.domain.entity.DepositDetails
import ru.kode.base.internship.products.domain.entity.DepositWithDetails
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface ProductUseCase {
  fun fetchAccounts(update: Boolean = false)
  val fetchAccountState: Flow<LceState>
  val accountsList: Flow<List<Account>>

  fun fetchCards(update: Boolean = false)
  val fetchCardState: Flow<LceState>
  fun cardDetails(id: String): Flow<CardDetails>
  val accountListWithCards: Flow<List<AccountWithCards>>

  fun fetchDeposits(update: Boolean = false)
  val fetchDepositState: Flow<LceState>
  val depositsList: Flow<List<Deposit>>
  fun depositsDetails(id: Long): Flow<DepositDetails>
  val depositListWithDetails: Flow<List<DepositWithDetails>>
}

@ExperimentalCoroutinesApi
internal class ProductUseCaseImpl @Inject constructor(
  private val scope: CoroutineScope,
  private val accountRepository: AccountRepository,
  private val cardRepository: CardRepository,
  private val depositRepository: DepositRepository,
) : BaseUseCase<ProductUseCaseImpl.State>(scope, State()), ProductUseCase {

  data class State(
    val accountsState: LceState = LceState.None,
    val depositsState: LceState = LceState.None,
    val cardsState: LceState = LceState.None,
  )

  override fun fetchAccounts(update: Boolean) {
    scope.launch(Dispatchers.IO) {
      setState { copy(accountsState = LceState.Loading) }
      try {
        delay(TimeUnit.SECONDS.toMillis(1))
        accountRepository.fetchAccount(isNew = update)
        setState { copy(accountsState = LceState.Content) }
      } catch (e: Exception) {
        setState { copy(accountsState = LceState.Error(e.message)) }
      }
    }
  }

  override val accountsList: Flow<List<Account>>
    get() = accountRepository.accounts

  override val fetchAccountState: Flow<LceState>
    get() = stateFlow.map { it.accountsState }.distinctUntilChanged()


  override fun fetchCards(update: Boolean) {
    scope.launch(Dispatchers.IO) {
      setState { copy(cardsState = LceState.Loading) }
      try {
        delay(TimeUnit.SECONDS.toMillis(1))
        cardRepository.fetchCards(isNew = update)
        setState { copy(cardsState = LceState.Content) }
      } catch (e: Exception) {
        setState { copy(cardsState = LceState.Error(e.message)) }
      }
    }
  }

  override fun cardDetails(id: String): Flow<CardDetails> = cardRepository.cardDetails(id = id)

  override val accountListWithCards: Flow<List<AccountWithCards>>
    get() {
      return accountRepository.accounts.flatMapLatest { accounts ->
        val listOfAccountWithCardsFlow = accounts.map { account ->
          val cardsFlow = account.cards.map { cardId -> cardRepository.cardDetails(cardId) }
          combine(cardsFlow) { it.toList() }
            .map { itListCardDetails ->
              AccountWithCards(
                accountId = account.accountId,
                currency = account.currency,
                status = account.status,
                balance = account.balance,
                number = account.number,
                cards = itListCardDetails
              )
            }
        }
        combine(listOfAccountWithCardsFlow) { it.toList() }
      }
    }

  override fun depositsDetails(id: Long): Flow<DepositDetails> = depositRepository.depositDetails(id)

  override val depositListWithDetails: Flow<List<DepositWithDetails>>
    get() {
      return depositRepository.deposits.flatMapLatest { deposits ->
        val depositTermsFlow = deposits.map { deposit ->
          depositsDetails(deposit.depositId)
            .map {
              DepositWithDetails(
                depositId = deposit.depositId,
                status = deposit.status,
                currency = deposit.currency,
                balance = deposit.balance,
                name = deposit.name,
                details = it
              )
            }
        }
        combine(depositTermsFlow) { it.toList() }
      }
    }


  override val fetchCardState: Flow<LceState>
    get() = stateFlow.map { it.cardsState }.distinctUntilChanged()

  override fun fetchDeposits(update: Boolean) {
    scope.launch(Dispatchers.IO) {
      setState { copy(depositsState = LceState.Loading) }
      try {
        delay(TimeUnit.SECONDS.toMillis(1))
        depositRepository.fetchDeposits(isNew = update)
        setState { copy(depositsState = LceState.Content) }
      } catch (e: Exception) {
        setState { copy(depositsState = LceState.Error(e.message)) }
      }
    }
  }

  override val fetchDepositState: Flow<LceState>
    get() = stateFlow.map { it.depositsState }.distinctUntilChanged()

  override val depositsList: Flow<List<Deposit>>
    get() = depositRepository.deposits

}