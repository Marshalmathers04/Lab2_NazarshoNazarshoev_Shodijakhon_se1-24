package uz.ttpu.composearchlab.ui.signin

// Comment (Task 5): A sealed interface is a better fit here than an enum because
// individual cases can carry distinct data payloads (Error carries message, SignedIn carries email).
// An enum requires all entries to share identical parameters and cannot model distinct data properties per state.
sealed interface SignInUiState {
    data object SignedOut : SignInUiState
    data object InProgress : SignInUiState
    data class Error(val message: String) : SignInUiState
    data class SignedIn(val email: String) : SignInUiState
}
