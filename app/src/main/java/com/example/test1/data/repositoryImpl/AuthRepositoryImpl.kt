package com.example.test1.data.repositoryImpl

import com.example.test1.domain.repository.AuthRepository
import com.example.test1.domain.result.AuthResult

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(user: String, pass: String): AuthResult {
        return if (user == "admin" && pass == "admin") {
            AuthResult.Success
        } else {
            AuthResult.Failure("Credenciales incorrectas")
        }
    }
}