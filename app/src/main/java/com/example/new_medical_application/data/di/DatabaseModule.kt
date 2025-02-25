package com.example.new_medical_application.data.di

import android.content.Context
import androidx.room.Room
import com.example.new_medical_application.data.database.AppDatabase
import com.example.new_medical_application.data.database.model.EmergencyContactDao
import com.example.new_medical_application.data.database.model.MedicalDataDao
import com.example.new_medical_application.data.database.model.PatientDao
import com.example.new_medical_application.data.database.repos.EmergencyContactRepositoryImpl
import com.example.new_medical_application.data.database.repos.IEmergencyContactRepository
import com.example.new_medical_application.data.database.repos.IMedicalDataRepository
import com.example.new_medical_application.data.database.repos.IPatientRepository
import com.example.new_medical_application.data.database.repos.MedicalDataRepositoryImpl
import com.example.new_medical_application.data.database.repos.PatientRepositoryImpl
import com.example.new_medical_application.data.local.SharedPreferencesHelper
import com.example.new_medical_application.domain.usecases.EmergencyContactUseCaseImpl
import com.example.new_medical_application.domain.usecases.IEmergencyContactUseCase
import com.example.new_medical_application.domain.usecases.IMedicalDataUseCase
import com.example.new_medical_application.domain.usecases.IPatientUseCase
import com.example.new_medical_application.domain.usecases.MedicalDataUseCaseImpl
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
    fun provideMedicalDataDao(appDatabase: AppDatabase): MedicalDataDao {
        return appDatabase.medicalDataDao()
    }

    @Provides
    @Singleton
    fun provideEmergencyContactDao(appDatabase: AppDatabase): EmergencyContactDao {
        return appDatabase.emergencyContactDao()
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        patientDao: PatientDao
    ): IPatientRepository = PatientRepositoryImpl(patientDao)

    @Provides
    @Singleton
    fun provideMedicalDataRepository(
        medicalDataDao: MedicalDataDao
    ): IMedicalDataRepository = MedicalDataRepositoryImpl(medicalDataDao)

    @Provides
    @Singleton
    fun provideEmergencyContactRepository(
        emergencyContactDao: EmergencyContactDao
    ): IEmergencyContactRepository = EmergencyContactRepositoryImpl(emergencyContactDao)

    @Provides
    @Singleton
    fun providePatientUseCase(
        iPatientRepository: IPatientRepository,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): IPatientUseCase = PatientUseCaseImpl(iPatientRepository, sharedPreferencesHelper)

    @Provides
    @Singleton
    fun provideMedicalDataUseCase(
        iMedicalDataRepository: IMedicalDataRepository
    ): IMedicalDataUseCase = MedicalDataUseCaseImpl(iMedicalDataRepository)

    @Provides
    @Singleton
    fun provideEmergencyContactUseCase(
        iEmergencyContactRepository: IEmergencyContactRepository
    ): IEmergencyContactUseCase = EmergencyContactUseCaseImpl(iEmergencyContactRepository)

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