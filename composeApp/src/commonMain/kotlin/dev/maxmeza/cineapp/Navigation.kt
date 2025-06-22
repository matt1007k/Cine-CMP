package dev.maxmeza.cineapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.maxmeza.cineapp.ui.common.ObserveAsEvents
import dev.maxmeza.cineapp.ui.controller.SnackbarController
import dev.maxmeza.cineapp.ui.manager.AuthViewModel
import dev.maxmeza.cineapp.ui.screens.home.HomeScreen
import dev.maxmeza.cineapp.ui.screens.login.LoginScreen
import dev.maxmeza.cineapp.ui.screens.search.SearchScreen
import dev.maxmeza.cineapp.ui.screens.second.SecondScreen
import dev.maxmeza.cineapp.ui.screens.start.StartScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationRoot() {
    val scope = rememberCoroutineScope()
    val navController  = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val state by authViewModel.uiState.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(flow = SnackbarController.events) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
    SharedTransitionLayout {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = if (state.tokens != null) SubGraph.Dashboard else SubGraph.Onboarding
            ) {
                navigation<SubGraph.Onboarding>(startDestination = AppDestination.Start) {
                    composable<AppDestination.Start> {
                        StartScreen(onSecond = {
                            navController.navigate(AppDestination.Second)
                        })
                    }
                    composable<AppDestination.Second> {
                        SecondScreen(onLoginClick = {
                            navController.navigate(AppDestination.Login)
                        })
                    }
                }
                navigation<SubGraph.Auth>(startDestination = AppDestination.Login) {
                    composable<AppDestination.Login> {
                        LoginScreen(
                            onNavHome = {
                                navController.navigate(AppDestination.Home)
                            }
                        )
                    }
                }

                navigation<SubGraph.Dashboard>(startDestination = AppDestination.Home) {
                    composable<AppDestination.Home> {
                        HomeScreen(goSearch = {
                            navController.navigate(AppDestination.Search)
                        })
                    }

                    composable<AppDestination.Search> {
                        SearchScreen(onBack = {
                            navController.popBackStack()
                        })
                    }
            }
        }
    }
    }
}

sealed class SubGraph {
    @Serializable
    object Onboarding: SubGraph()
    @Serializable
    object Auth: SubGraph()
    @Serializable
    object Dashboard: SubGraph()
}

sealed class AppDestination {
    @Serializable
    object Start: AppDestination()
    @Serializable
    object Second: AppDestination()
    @Serializable
    object Login: AppDestination()
    @Serializable
    object Home: AppDestination()
    @Serializable
    object Search: AppDestination()
}