package com.lexx.telemetry.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lexx.domain.features.settings.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : UserPreferencesRepository {
    private val serverAddressPreferencesKey = stringPreferencesKey(name = "server_address")

    override suspend fun setServerAddress(serverAddress: String) {
        setStringPreference(serverAddressPreferencesKey, serverAddress)
    }

    override suspend fun getServerAddress(
        defaultServerAddress: String
    ): String {
        return getStringPreference(serverAddressPreferencesKey, defaultServerAddress)
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
        preferenceKey: Preferences.Key<String>,
        preferenceDefaultValue: String = ""
    ): String {
        return try {
            userDataStorePreferences.data.first()[preferenceKey] ?: preferenceDefaultValue
        } catch (e: Exception) {
            throw e
        }
    }
}
