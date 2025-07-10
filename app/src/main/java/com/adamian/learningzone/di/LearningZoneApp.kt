package com.adamian.learningzone.di

import android.app.Application
import com.adamian.learningzone.R
import com.adamian.learningzone.data.local.QuestionEntity
import com.adamian.learningzone.domain.repository.QuestionRepository
import com.adamian.learningzone.domain.usecase.CreateInitialQuizzesUC
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
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

    @Inject lateinit var createInitialQuizzesUC: CreateInitialQuizzesUC

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch(Dispatchers.IO) {
            if (repository.isDatabaseEmpty()) {
                val questions = parseQuestionsFromResources()
                repository.insertAll(*questions.toTypedArray())
                val savedQuestions = repository.getAllQuestions()
                createInitialQuizzesUC.createQuizzes(savedQuestions)
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
                    level = jsonObject.optInt("level"),
                    right = jsonObject.optInt("right"),
                    wrong = jsonObject.optInt("wrong"),
                    answered = jsonObject.optInt("answered")
                )
                questions.add(question)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return questions
    }
}