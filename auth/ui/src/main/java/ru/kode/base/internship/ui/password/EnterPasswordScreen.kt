package ru.kode.base.internship.ui.password

import androidx.compose.runtime.Immutable
import ru.kode.base.core.BaseViewIntents
import ru.kode.base.internship.core.domain.entity.LceState

internal object EnterPasswordScreen {
  class ViewIntents : BaseViewIntents() {
    val navigateOnBack = intent(name = "navigateOnBack")
    val changeText = intent<String>(name = "changeText")
    val login = intent(name = "login")
    val togglePasswordVisibility = intent(name = "togglePasswordVisibility")
    val dismissError = intent(name = "dismissError")
  }

  @Immutable
  data class ViewState(
    val loginLceState: LceState = LceState.None,
    val isPasswordProtected: Boolean = true,
    val enteredPassword: String = "",
    val errorMessage: ErrorMessage? = null,
  )

  sealed class ErrorMessage {
    object LoginError : ErrorMessage()
  }
}
