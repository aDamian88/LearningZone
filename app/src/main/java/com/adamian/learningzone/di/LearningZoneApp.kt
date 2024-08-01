package com.adamian.learningzone.di

import android.app.Application
import com.adamian.learningzone.R
import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.repository.QuestionRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.InputStream
import javax.inject.Inject

@HiltAndroidApp
class LearningZoneApp : Application() {

    @Inject
    lateinit var repository: QuestionRepository

    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch(Dispatchers.IO) {
            if (repository.isDatabaseEmpty()) {
                val questions = parseQuestionsFromResources()
                repository.insertAll(*questions.toTypedArray())
            }
        }
    }

    private fun parseQuestionsFromResources(): List<QuestionEntity> {
        val questions = mutableListOf<QuestionEntity>()
        try {
            val inputStream: InputStream = resources.openRawResource(R.raw.questions)
            val json = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val question = QuestionEntity(
                    title = jsonObject.optString("title"),
                    question = jsonObject.optString("question"),
                    answerDescription = jsonObject.optString("answerDescription"),
                    firstOption = jsonObject.optString("firstOption"),
                    secondOption = jsonObject.optString("secondOption"),
                    thirdOption = jsonObject.optString("thirdOption"),
                    fourthOption = jsonObject.optString("fourthOption"),
                    correctOption = jsonObject.optString("correctOption"),
                    chapter = jsonObject.optInt("chapter"),
                    level = jsonObject.optInt("level")
                )
                questions.add(question)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return questions
    }
}