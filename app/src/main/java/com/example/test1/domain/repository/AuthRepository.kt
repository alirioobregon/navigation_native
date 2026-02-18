package com.example.test1.domain.repository

import com.example.test1.domain.result.AuthResult

// -------------------- REPOSITORY --------------------
interface AuthRepository {
    suspend fun login(user: String, pass: String): AuthResult
}