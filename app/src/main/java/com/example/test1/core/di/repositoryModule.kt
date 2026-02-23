package com.example.test1.core.di

import com.example.test1.data.repositoryImpl.AuthRepositoryImpl
import com.example.test1.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl() }
}