package com.bangkit2024.facetrack.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveSession(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveIdProgramActive(id: String) {
        dataStore.edit { preferences ->
            preferences[ID_PROGRAM_ACTIVE] = id
        }
    }

    fun getIdProgramActive(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[ID_PROGRAM_ACTIVE] ?: ""
        }
    }

    fun getSession(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun updateRegisterUser(isUpdate: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_UPDATE_DATA_KEY] = isUpdate
        }
    }

    fun isUpdatedData(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_UPDATE_DATA_KEY] ?: false
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_UPDATE_DATA_KEY = booleanPreferencesKey("isUpdateData")
        private val ID_PROGRAM_ACTIVE = stringPreferencesKey("id_program_active")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreference(
                    dataStore
                ).also {
                    INSTANCE = it
                }
            }
        }
    }
}