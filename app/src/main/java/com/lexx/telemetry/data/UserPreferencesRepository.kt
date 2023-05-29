package com.lexx.telemetry.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface UserPreferencesRepository {

    suspend fun setName(name: String)
    suspend fun getName(): Result<String>
    suspend fun setServerAddress(serverAddress: String)
    suspend fun getServerAddress(defaultServerAddress: String = ""): Result<String>
}

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : UserPreferencesRepository {

    override suspend fun setName(
        name: String
    ) {
        setStringPreference(KEY_NAME, name)
    }

    override suspend fun getName(): Result<String> {
        return getStringPreference(KEY_NAME)
    }

    override suspend fun setServerAddress(serverAddress: String) {
        setStringPreference(KEY_SERVER_ADDRESS, serverAddress)
    }

    override suspend fun getServerAddress(
        defaultServerAddress: String
    ): Result<String> {
        return getStringPreference(KEY_SERVER_ADDRESS, defaultServerAddress)
    }

    private suspend fun setStringPreference(
        preferenceKey: Preferences.Key<String>,
        preferenceValue: String
    ) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[preferenceKey] = preferenceValue
            }
        }
    }
    private suspend fun getStringPreference(
        preferenceValue: Preferences.Key<String>,
        preferenceDefaultValue: String = ""
    ): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    preferences[preferenceValue]
                }
            val value = flow.firstOrNull() ?: preferenceDefaultValue
            value
        }
    }

    private companion object {
        val KEY_NAME = stringPreferencesKey(name = "name")
        val KEY_SERVER_ADDRESS = stringPreferencesKey(name = "server_address")
    }
}
