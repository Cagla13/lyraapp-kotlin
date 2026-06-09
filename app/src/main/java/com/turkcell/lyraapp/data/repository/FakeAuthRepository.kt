package com.turkcell.lyraapp.data.repository

import kotlinx.coroutines.delay

class FakeAuthRepository {


    suspend fun register(
        firstName: String,
        lastName: String,
        phone: String,
        password: String
    ): Result<Boolean> {
        delay(1500)
        return if (password.length >= 8) {
            Result.success(true)
        } else {
            Result.failure(Exception("Şifre en az 8 karakter olmalıdır."))
        }
    }


    suspend fun login(phone: String, password: String): Result<Boolean> {
        delay(1500)

        return if (phone.isNotEmpty() && password.isNotEmpty()) {
            Result.success(true)
        } else {
            Result.failure(Exception("Telefon veya şifre hatalı."))
        }
    }
}