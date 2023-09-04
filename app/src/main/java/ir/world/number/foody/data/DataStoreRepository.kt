package ir.world.number.foody.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ir.world.number.foody.util.Constans.Companion.DEFAULT_DIET_TYPE
import ir.world.number.foody.util.Constans.Companion.DEFAULT_MEAL_TYPE
import ir.world.number.foody.util.Constans.Companion.PREFERENCE_BACK_ONLINE
import ir.world.number.foody.util.Constans.Companion.PREFERENCE_DIET_TYPE
import ir.world.number.foody.util.Constans.Companion.PREFERENCE_DIET_TYPE_ID
import ir.world.number.foody.util.Constans.Companion.PREFERENCE_MAME
import ir.world.number.foody.util.Constans.Companion.PREFERENCE_MEAL_TYPE
import ir.world.number.foody.util.Constans.Companion.PREFERENCE_MEAL_TYPE_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {


    private object PreferenceKeys {
        val selectedMealType = preferencesKey<String>(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = preferencesKey<String>(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = preferencesKey<Int>(PREFERENCE_DIET_TYPE_ID)
        val backOnline = preferencesKey<Boolean>(PREFERENCE_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(

            name = PREFERENCE_MAME
    )

    suspend fun saveMealAndDietType(
            mealType: String,
            mealTypeId: Int,
            dietType: String,
            dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
            //Log.d("eeeee",preferences[PreferenceKeys.selectedMealType].toString())

        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline

        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                    Log.d("asdf","qqqq")
                } else {
                    throw  exception
                    Log.d("asdf","qqqq2")
                }
            }
            .map { preferences ->
                val selectedMealType = preferences[PreferenceKeys.selectedMealType]
                        ?: DEFAULT_MEAL_TYPE
                val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
                val selectedDietType = preferences[PreferenceKeys.selectedDietType]
                        ?: DEFAULT_DIET_TYPE
                val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
                MealAndDietType(
                        selectedMealType,
                        selectedMealTypeId,
                        selectedDietType,
                        selectedDietTypeId
                )

            }
    val readBackOnline: Flow<Boolean> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw  exception
                }
            }
            .map { preferences ->
                val backOnline = preferences[PreferenceKeys.backOnline] ?: false
                backOnline

            }
}

data class MealAndDietType(
        val selectedMealType: String,
        val selectedMealTypeId: Int,
        val selectedDietType: String,
        val selectedDietTypeId: Int,
)