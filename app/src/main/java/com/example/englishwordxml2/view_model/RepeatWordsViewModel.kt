package com.example.englishwordxml2.view_model

import androidx.lifecycle.ViewModel
import com.example.englishwordxml2.data.EnglishWord
import com.example.englishwordxml2.db.MyDbManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RepeatWordsViewModel @Inject constructor(val myDbManager: MyDbManager) : ViewModel() {

    private val guessCountMutable = MutableStateFlow(0)
    val guessCount: StateFlow<Int> = guessCountMutable
    private val noGuessCountMutable = MutableStateFlow(0)
    val noGuessCount: StateFlow<Int> = noGuessCountMutable
    private val englishWordsMapMutable: MutableStateFlow<MutableMap<String, String>> =
        MutableStateFlow(mutableMapOf())
    val englishWordsMap: StateFlow<Map<String, String>> = englishWordsMapMutable
    private val translateWordsListMutable: MutableStateFlow<MutableList<EnglishWord>> =
        MutableStateFlow(mutableListOf())
    val translateWordsList: MutableStateFlow<MutableList<EnglishWord>> = translateWordsListMutable

    fun getEnglishWordsMap() {
        englishWordsMapMutable.value = myDbManager.readRandomWordsTable()
    }

    fun updateTranslateList() {
        val listTranscription = ArrayList<EnglishWord>()
        englishWordsMapMutable.value.let { it ->
            var firsKey = it.keys.first()
            it.forEach {
                listTranscription.add(
                    EnglishWord(
                        it.key,
                        it.value,
                        when (it.key) {
                            firsKey -> true
                            else -> false
                        }
                    )
                )
            }
        }
        translateWordsListMutable.value = listTranscription.apply { shuffle() }
    }


    fun guessWord(translate: String): Boolean {
        translateWordsListMutable.value
            .let {
                it.forEach { element ->
                    if (element.translate == translate) {
                        return element.rightTranslate
                    }
                }
            }
        return false
    }

    fun increaseCounts(guess: Boolean) {
        when (guess) {
            true -> guessCountMutable.value++
            false -> noGuessCountMutable.value++
        }
    }
}