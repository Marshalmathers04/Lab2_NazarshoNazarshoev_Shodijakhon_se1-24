package uz.ttpu.composearchlab.ui.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    // Comment (Task 6): _uiState is private and mutable so state modifications are strictly encapsulated
    // within the ViewModel. Exposing uiState as a read-only State guarantees state encapsulation and prevents
    // the UI from directly mutating state, enforcing unidirectional data flow.
    private val _uiState = mutableStateOf<SignInUiState>(SignInUiState.SignedOut)
    val uiState: State<SignInUiState>
        get() = _uiState

    fun onSignIn(email: String, password: String) {
        // TODO 1: ignore the call if a sign-in is already running (double-tap protection)
        if (_uiState.value is SignInUiState.InProgress) return

        // TODO 2: set InProgress, then launch a coroutine in viewModelScope
        _uiState.value = SignInUiState.InProgress
        viewModelScope.launch {
            // TODO 3: simulate the network with delay(1500)
            delay(1500)

            // TODO 4: "kotlin123" -> SignedIn(email); else -> Error(...)
            if (password == "kotlin123") {
                _uiState.value = SignInUiState.SignedIn(email)
            } else {
                _uiState.value = SignInUiState.Error("Wrong email or password")
            }
        }
    }

    // Task 9: Resets Error state back to SignedOut after the UI consumes the Snackbar event
    fun onErrorShown() {
        if (_uiState.value is SignInUiState.Error) {
            _uiState.value = SignInUiState.SignedOut
        }
    }
}
