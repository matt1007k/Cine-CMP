package dev.maxmeza.cineapp.ui.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import dev.maxmeza.cineapp.domain.model.Tokens
import dev.maxmeza.cineapp.domain.model.User
import dev.maxmeza.cineapp.domain.useCases.LoginUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import com.russhwolf.settings.Settings

//@OptIn(ExperimentalSettingsApi::class)
//class UserAppSetting() {
//    private var observableSettings: ObservableSettings = (Settings() as ObservableSettings)
//
//    val token: Flow<String> =  observableSettings.getStringFlow("token", "")
//
//    fun setToken(newToken: String) {
//        observableSettings.putString("token", newToken)
//    }
//}
//
//fun getToken(): String {
//    val userAppSetting = UserAppSetting()
//    runBlocking {
//        return@runBlocking userAppSetting.token.single()
//    }
//    return ""
//}
//
//fun setToken(newToken: String) {
//    val userAppSetting = UserAppSetting()
//    userAppSetting.setToken(newToken)
//}

class AuthViewModel(
    private val loginUseCases: LoginUseCases,
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun login(email: String, password: String) {
       loginUseCases.invoke(email = email, password = password)
           .onStart {
               _uiState.update { AuthUiState(isLoading = true) }
           }.onEach { result ->
              result.onSuccess { data ->
//                  setToken(data.tokens.token)
                  _uiState.update { AuthUiState(user = data.user, tokens = data.tokens) }
              }.onFailure { error ->
                  _uiState.update { AuthUiState(error = error.message.toString()) }
              }
           }.launchIn(viewModelScope)
    }

    data class AuthUiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val user: User? = null,
        val tokens: Tokens? = null
    )

}

