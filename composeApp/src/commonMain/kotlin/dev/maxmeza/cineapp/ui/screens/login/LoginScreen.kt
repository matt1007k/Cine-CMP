package dev.maxmeza.cineapp.ui.screens.login

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cineapp.composeapp.generated.resources.Res
import cineapp.composeapp.generated.resources.facebook_logo
import cineapp.composeapp.generated.resources.google_logo
import cineapp.composeapp.generated.resources.ic_logo_cine
import dev.maxmeza.cineapp.ui.AppTheme
import dev.maxmeza.cineapp.ui.Blue
import dev.maxmeza.cineapp.ui.ColorText
import dev.maxmeza.cineapp.ui.component.CineTextField
import dev.maxmeza.cineapp.ui.controller.SnackbarController
import dev.maxmeza.cineapp.ui.controller.SnackbarEvent
import dev.maxmeza.cineapp.ui.manager.AuthViewModel
import dev.maxmeza.cineapp.util.AppLogger
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(onNavHome: () -> Unit) {
    val vm: LoginViewModel = koinViewModel()
    val authViewModel: AuthViewModel = koinViewModel()
    val state = vm.uiState
    val stateLogin by authViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    LaunchedEffect(stateLogin.error) {
        if (stateLogin.error != "") {
            scope.launch {
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = stateLogin.error
                    )
                )
            }
        }

    }

    LaunchedEffect(stateLogin.user) {
        AppLogger.i("LaunchedEffect:: LoginScreen", stateLogin.user.toString())
        if (stateLogin.user != null) {
            onNavHome()
        }
    }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .safeDrawingPadding()
                    .paddingContainer()
                    .verticalScroll(rememberScrollState())
                    .widthIn(max = 450.dp)
            ) {
                Text(
                    "Welcome to CornPass", modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp), // bodyLarge
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Blue,
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    "Let’s Sign You In! 🍿", modifier =
                        Modifier
                            .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(Res.drawable.ic_logo_cine),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 52.dp),
                )

                Spacer(Modifier.height(32.dp))

                CineTextField(
                    value = state.email,
                    onValueChange = vm::onChangeEmail,
                    label = "Email",
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, contentDescription = "Icon leading")
                    },
                    isError = state.emailError != null,
                    supportingText = state.emailError,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                CineTextField(
                    value = state.password,
                    onValueChange = vm::onChangePassword,
                    label = "Password",
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, contentDescription = "Icon image")
                    },
                    isError = state.passwordError != null,
                    supportingText = state.passwordError,
                    modifier = Modifier
                        .fillMaxWidth()

                )

                TextButton(onClick = {}, modifier = Modifier.align(Alignment.End)) {
                    Text("Forgot password?")
                }

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        scope.launch {
                            authViewModel.login(state.email, state.password)
                        }
                    },
                    enabled = state.isFormValid,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text("Sign in")
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ButtonIcon(
                        onClick = {},
                        label = "Google",
                        icon = painterResource(Res.drawable.google_logo),
                        modifier = Modifier.weight(1f)
                    )
                    ButtonIcon(
                        onClick = {},
                        label = "Facebook",
                        icon = painterResource(Res.drawable.facebook_logo),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(32.dp))
                TextButton(
                    onClick = {}, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text("Don't have an account?", color = ColorText.Primary)
                        Text("Create one")
                    }

                }
            }
            if (stateLogin.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }


}

@Composable
fun Modifier.paddingContainer(): Modifier {
    return this then Modifier.padding(horizontal = 16.dp)
}

@Composable
private fun ButtonIcon(onClick: () -> Unit, label: String, icon: Painter, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = MaterialTheme.colorScheme.secondary)
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        Surface {
            LoginScreen(onNavHome = {})
        }

    }
}


@Preview
@Composable
fun AnimateVisibility() {
    var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
    val transition = updateTransition(selected, label = "selected state")
    val borderColor by transition.animateColor(label = "border color") { isSelected ->
        if (isSelected) Color.Magenta else Color.White
    }
    val elevation by transition.animateDp(label = "elevation") { isSelected ->
        if (isSelected) 10.dp else 2.dp
    }
    Surface(
        onClick = { selected = !selected },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Hello, world!")
            // AnimatedVisibility as a part of the transition.
            transition.AnimatedVisibility(
                visible = { targetSelected -> targetSelected },
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(text = "It is fine today.")
            }
            // AnimatedContent as a part of the transition.
            transition.AnimatedContent { targetState ->
                if (targetState) {
                    Text(text = "Selected")
                } else {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                }
            }
        }
    }
}