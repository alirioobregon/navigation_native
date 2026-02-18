package com.example.test1.domain.result

// -------------------- RESULT (domain) --------------------
sealed interface AuthResult {
    data object Success : AuthResult
    data class Failure(val message: String) : AuthResult
}
