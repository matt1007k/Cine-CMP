package dev.maxmeza.cineapp.di

import dev.maxmeza.cineapp.data.model.WebSocketClient
import dev.maxmeza.cineapp.data.remote.KtorClient
import dev.maxmeza.cineapp.data.remote.KtorWebSocketClient
import dev.maxmeza.cineapp.data.repository.AuthRepositoryImpl
import dev.maxmeza.cineapp.data.service.ApiService
import dev.maxmeza.cineapp.data.service.AuthService
import dev.maxmeza.cineapp.domain.repository.AuthRepository
import dev.maxmeza.cineapp.domain.useCases.HomeUseCase
import dev.maxmeza.cineapp.domain.useCases.LoginUseCases
import dev.maxmeza.cineapp.ui.manager.AuthViewModel
import dev.maxmeza.cineapp.ui.screens.chat.ChatViewModel
import dev.maxmeza.cineapp.ui.screens.home.HomeViewModel
import dev.maxmeza.cineapp.ui.screens.login.LoginViewModel
import dev.maxmeza.cineapp.ui.screens.search.SearchViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, dataModule, domainModule, viewModelsModule)
    }
}

val dataModule = module {
    single { ApiService(httpClient = KtorClient.getInstance()) }
    single { AuthService(client = KtorClient.getInstance() ) }
    factoryOf(::AuthRepositoryImpl).bind<AuthRepository>()
    single<WebSocketClient> { KtorWebSocketClient() } // Provee la implementación del cliente WebSocket
    factoryOf(::ChatViewModel)

}

val domainModule = module {
    factoryOf(::LoginUseCases)
    factoryOf(::HomeUseCase)
}

val appModule = module {

}

val viewModelsModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::HomeViewModel)
}

expect  val nativeModule: Module