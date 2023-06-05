package com.example.englishwordxml2.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.englishwordxml2.databinding.FragmentAddLetterBinding
import com.example.englishwordxml2.view_model.LetterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLetterFragment : Fragment() {
    private lateinit var binding: FragmentAddLetterBinding
    private val letterModel: LetterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLetterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            this.buttonAdd.setOnClickListener {
                letterModel.setEnglishWord(this.addEngWord.text.toString())
                letterModel.setEnglishTranscription(this.addTranslateWord.text.toString())
                letterModel.setTap(true)
                letterModel.setTap(false)
            }
        }
    }

    companion object {

        fun newInstance() =
            AddLetterFragment()
    }
}