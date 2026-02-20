package com.example.test1.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onNavigateHome: () -> Unit
) {

    val vm = viewModel<LoginViewModel>()
//    val uiState = vm.uiState.collectAsState().value

    val uiState by vm.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
//        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            // manejar eventos con el ciclo de vida, problema con la navegación!
//        }

        vm.effects.collectLatest { effect ->
            when (effect) {
                LoginEffect.NavigateHome -> {
                    onNavigateHome()
                }

                is LoginEffect.ShowMessage -> {
                    // En real: SnackbarHostState
                    // aquí lo omitimos para mantenerlo simple
                }
            }
        }
    }

//    val vm2 by viewModel<LoginViewModel>()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Login", style = MaterialTheme.typography.headlineSmall)
        Text("data: ${uiState.isLoading}", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.user,
            onValueChange = { vm.onIntent(LoginIntent.UserChanged(it)) },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.pass,
            onValueChange = { vm.onIntent(LoginIntent.PassChanged(it)) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        uiState.error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { vm.onIntent(LoginIntent.Submit) },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color.White)
            } else {
                Text("Ingresar")
            }
        }

        Button(
            onClick = { onNavigateHome() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Home (demo)")
        }
    }
}
