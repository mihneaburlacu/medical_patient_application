package com.example.new_medical_application.data.di

import com.example.new_medical_application.domain.repositoryapi.FitbitRepositoryImpl
import com.example.new_medical_application.domain.repositoryapi.IFitbitRepository
import com.example.new_medical_application.domain.repositoryapi.IMedicalTopicDetailRepository
import com.example.new_medical_application.domain.repositoryapi.IMedicalTopicsRepository
import com.example.new_medical_application.domain.repositoryapi.MedicalTopicDetailRepositoryImpl
import com.example.new_medical_application.domain.repositoryapi.MedicalTopicsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideFitbitRepository(): IFitbitRepository = FitbitRepositoryImpl()

    @Provides
    @Singleton
    fun provideMedicalTopicsRepository(): IMedicalTopicsRepository = MedicalTopicsRepositoryImpl()

    @Provides
    @Singleton
    fun provideMedicalTopicDetailRepository(): IMedicalTopicDetailRepository =
        MedicalTopicDetailRepositoryImpl()
}