package dev.maxmeza.cineapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.LongState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.maxmeza.cineapp.domain.model.User
import dev.maxmeza.cineapp.ui.AppTheme
import dev.maxmeza.cineapp.ui.common.UiState
import dev.maxmeza.cineapp.ui.manager.AuthViewModel
import dev.maxmeza.cineapp.ui.screens.graphic.InfiniteAnimation
import dev.maxmeza.cineapp.ui.screens.login.paddingContainer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(goSearch: () -> Unit, goGraphic: () -> Unit) {
    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.uiState.collectAsState()

    val homeViewModel: HomeViewModel = koinViewModel()
    val homeState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxWidth()
                    .paddingContainer()
            ) {
                IconButton(onClick = {  }) {
                    Icon(Icons.Outlined.Menu, contentDescription = "Navigation Menu Icon")
                }

                IconButton(onClick = goSearch) {
                    Icon(Icons.Outlined.Search, contentDescription = "Navigation Search Icon")
                }
            }
        },
        containerColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            authState.user?.let { user ->
                Text(
                    "Bienvenidos ${user.fullName}", style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .paddingContainer()
                )
            }
            InfiniteAnimation(
                modifier = Modifier
                    .paddingContainer()
            )

            when (homeState) {
                UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Error -> {
                    val errorMessage = (homeState as UiState.Error).message
                    Text(
                        "Error $errorMessage",
                        modifier = Modifier
                            .paddingContainer()
                    )
                }

                is UiState.Success -> {
                   (homeState as UiState.Success).data.let { data ->
                        Text(
                            "Data: ${data.fullName} name ${data.firstName}",
                            modifier = Modifier
                                .paddingContainer()
                        )
                    }

                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(8) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(16))
                    ) {
                        Icon(
                            Icons.Outlined.Image, contentDescription = "icon $it", modifier = Modifier
                                .size(80.dp)
                        )
                    }
                }
            }

            Text(
                text = "Grid view",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Black
                )
            )

            Text(
                text = "Grid view", style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
            )

            ScrollBoxesSmooth()


            LazyVerticalGrid(
                columns = GridCells.Adaptive(145.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(29) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .size(100.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(16))
                    ) {
                        Icon(
                            Icons.Outlined.Image, contentDescription = "icon $it", modifier = Modifier
                                .size(80.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            goSearch = {},
            goGraphic = {}
        )
    }
}

@Composable
private fun ScrollBoxesSmooth() {
    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(state)
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}