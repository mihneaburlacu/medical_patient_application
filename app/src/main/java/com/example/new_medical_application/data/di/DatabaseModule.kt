package com.example.new_medical_application.data.di

import android.content.Context
import androidx.room.Room
import com.example.new_medical_application.data.database.AppDatabase
import com.example.new_medical_application.data.database.model.PatientDao
import com.example.new_medical_application.data.database.repos.IPatientRepository
import com.example.new_medical_application.data.database.repos.PatientRepositoryImpl
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import com.example.new_medical_application.domain.usecases.PatientUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePatientDao(appDatabase: AppDatabase): PatientDao {
        return appDatabase.patientDao()
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        patientDao: PatientDao
    ): IPatientRepository = PatientRepositoryImpl(patientDao)

    @Provides
    @Singleton
    fun providePatientUseCase(
        iPatientRepository: IPatientRepository
    ): IPatientUseCase = PatientUseCaseImpl(iPatientRepository)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MedicalDB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}