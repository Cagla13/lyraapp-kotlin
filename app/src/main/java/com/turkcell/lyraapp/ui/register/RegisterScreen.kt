package com.turkcell.lyraapp.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RegisterContract.Effect.NavigateToHome -> onNavigateToHome()
                is RegisterContract.Effect.NavigateToLogin -> onNavigateToLogin()
                is RegisterContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hesap oluştur", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))


        OutlinedTextField(
            value = state.firstName,
            onValueChange = { viewModel.onEvent(RegisterContract.Event.OnFirstNameChanged(it)) },
            label = { Text("Ad") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(RegisterContract.Event.OnPasswordChanged(it)) },
            label = { Text("Şifre") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.isTermsAccepted,
                onCheckedChange = { viewModel.onEvent(RegisterContract.Event.OnTermsAcceptedChanged(it)) }
            )
            Text("Kullanım Koşullarını kabul ediyorum.")
        }
        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { viewModel.onEvent(RegisterContract.Event.OnRegisterClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Kayıt ol")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        TextButton(onClick = { viewModel.onEvent(RegisterContract.Event.OnLoginClicked) }) {
            Text("Zaten hesabın var mı? Giriş yap")
        }
    }
}