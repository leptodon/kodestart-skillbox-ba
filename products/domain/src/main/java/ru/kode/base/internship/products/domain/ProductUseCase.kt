package ru.kode.base.internship.products.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kode.base.internship.core.domain.BaseUseCase
import ru.kode.base.internship.core.domain.entity.LceState
import ru.kode.base.internship.products.domain.entity.Account
import ru.kode.base.internship.products.domain.entity.CardDetails
import ru.kode.base.internship.products.domain.entity.Deposit
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface ProductUseCase {
  fun fetchAccounts(update: Boolean = false)
  val fetchAccountState: Flow<LceState>
  val accountsList: Flow<List<Account>>

  fun fetchCards(update: Boolean = false)
  val fetchCardState: Flow<LceState>
  fun cardDetails(id: Long): Flow<CardDetails>
  fun resultCardDetailsList(): Flow<List<CardDetails>>

  fun fetchDeposits(update: Boolean = false)
  val fetchDepositState: Flow<LceState>
  val depositsList: Flow<List<Deposit>>

}

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
        delay(TimeUnit.SECONDS.toMillis(1)) // Для визуального отображения, что крутилка работает
        accountRepository.fetchAccount(isNew = update)
        accountRepository.accounts
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
        delay(TimeUnit.SECONDS.toMillis(1)) // Для визуального отображения, что крутилка работает
        cardRepository.fetchCards(isNew = update)
        setState { copy(cardsState = LceState.Content) }
      } catch (e: Exception) {
        setState { copy(cardsState = LceState.Error(e.message)) }
      }
    }
  }

  override fun cardDetails(id: Long): Flow<CardDetails> = cardRepository.cardDetails(id = id)

  override fun resultCardDetailsList(): Flow<List<CardDetails>> = flow {
    val cardDetailsList = mutableListOf<CardDetails>()
    accountRepository.accounts.collect { accounts ->
      accounts.forEach {
        it.cards.forEach { id ->
          withContext(Dispatchers.IO) {
            cardDetails(id)
          }.collect { cd -> cardDetailsList.add(cd) }
        }
      }
    }
    emit(cardDetailsList.toList())
  }

  override val fetchCardState: Flow<LceState>
    get() = stateFlow.map { it.cardsState }.distinctUntilChanged()

  override fun fetchDeposits(update: Boolean) {
    scope.launch(Dispatchers.IO) {
      setState { copy(depositsState = LceState.Loading) }
      try {
        delay(TimeUnit.SECONDS.toMillis(1)) // Для визуального отображения, что крутилка работает
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