package com.example.test1.data.repositoryImpl

import com.example.test1.domain.repository.AuthRepository
import com.example.test1.domain.result.AuthResult
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(user: String, pass: String): AuthResult {
        return if (user == "admin" && pass == "admin") {
            delay(10000)
            AuthResult.Success
        } else {
            delay(10000)
            AuthResult.Failure("Credenciales incorrectas")
        }
    }
}