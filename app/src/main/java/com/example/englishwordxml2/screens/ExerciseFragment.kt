package com.example.englishwordxml2.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.englishwordxml2.R
import com.example.englishwordxml2.databinding.FragmentExerciseBinding
import com.example.englishwordxml2.databinding.FragmentLetterBinding
import com.example.englishwordxml2.view_model.LetterViewModel
import com.example.englishwordxml2.view_model.RepeatWordsViewModel


class ExerciseFragment : Fragment() {
    private lateinit var binding: FragmentExerciseBinding
    private val repeatModel: RepeatWordsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatModel.apply {
            binding.counterOne.text = guessCount.value.toString()
            binding.counterTwo.text = noGuessCount.value.toString()
            getEnglishWordsMap()
            updateTranslateList()
            binding.apply {
                guessWord.text = englishWordsMap.value.keys.first()
                checkButton(wordOne, 0)
                checkButton(wordTwo, 1)
                checkButton(wordThree, 2)
                checkButton(wordFour, 3)
            }
        }
    }

   private fun checkButton(button: Button, positionTranslate: Int) {
        repeatModel.apply {
            var listTranslate = translateWordsList.value
            var translateWord = listTranslate.get(positionTranslate).translate
            button.text = translateWord

            button.setOnClickListener {
                listTranslate = translateWordsList.value
                translateWord = listTranslate.get(positionTranslate).translate
                guessWord(translateWord).run { increaseCounts(this) }

                binding.counterOne.text = guessCount.value.toString()
                binding.counterTwo.text = noGuessCount.value.toString()

                getEnglishWordsMap()
                updateTranslateList()
                updateButtons()
                binding.guessWord.text = englishWordsMap.value.keys.first()
            }

        }

    }

   private fun updateButtons() {
        val listTranslate = repeatModel.translateWordsList.value
        binding.apply {
            wordOne.text = listTranslate.get(0).translate
            wordTwo.text = listTranslate.get(1).translate
            wordThree.text = listTranslate.get(2).translate
            wordFour.text = listTranslate.get(3).translate
        }
    }

    companion object {
        fun newInstance() = ExerciseFragment()
    }
}