package com.gawel.babyjudas.featureMain.data.repositories

import android.util.Log
import com.gawel.babyjudas.core.models.Result
import com.gawel.babyjudas.featureMain.data.datasource.WikiService
import com.gawel.babyjudas.featureMain.data.models.SampleOP
import com.gawel.babyjudas.featureMain.domain.models.Sample
import com.gawel.babyjudas.featureMain.domain.repositories.WikiRepository
import retrofit2.Retrofit
import java.lang.Exception
import java.security.SecureRandom
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "WikiRepositoryImpl"
class WikiRepositoryImpl @Inject constructor(
    private val service: WikiService
) : WikiRepository {

    override suspend fun getDataAboutKids() : Result<Sample> {
        return try {
            val kidsInfo = service.getKidsInfo(Random(100).nextInt(0, 100))
            val result = kidsInfo.body()
            return if (kidsInfo.isSuccessful && result != null) {
                Result.Success(result.mapToDomain())
            } else {
                Log.e(TAG, "getDataAboutKids: ${kidsInfo.message()},  Kod odpowiedzi: ${kidsInfo.code()}")
                Result.Error(("An error occurred") +  "\nKod odpowiedzi: ${kidsInfo.code()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "getDataAboutKids: ", e)
            Result.Error(e.localizedMessage ?: "An error occurred.")
        }
    }
}