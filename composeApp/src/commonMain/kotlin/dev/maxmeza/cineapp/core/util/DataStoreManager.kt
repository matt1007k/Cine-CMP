package dev.maxmeza.cineapp.core.util
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.map
//
//const val USER_PREFERENCES_NAME = "user_preferences"
//
//val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)
//
//class DataStoreManager(val context: Context) {
//    companion object {
//        val IS_ONBOARDING_COMPLETE = booleanPreferencesKey("is_onboarding_complete")
//    }
//
//    suspend fun saveToDataStore(userPreferences: UserPreferences) {
//        context.preferenceDataStore.edit { preferences ->
//            preferences[IS_ONBOARDING_COMPLETE] = userPreferences.isOnboardingComplete
//        }
//    }
//
//    fun getDataStore() = context.preferenceDataStore
//        .data.map { preferences ->
//           UserPreferences(
//               isOnboardingComplete = preferences[IS_ONBOARDING_COMPLETE] ?: false
//           )
//        }
//
//    suspend fun clearDataStore() {
//        context.preferenceDataStore.edit { preferences ->
//            preferences.clear()
//        }
//    }
//}
//
//data class UserPreferences(
//    val isOnboardingComplete: Boolean
//)