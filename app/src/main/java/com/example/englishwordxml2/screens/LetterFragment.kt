package com.example.englishwordxml2.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.englishwordxml2.view_model.LetterViewModel
import com.example.englishwordxml2.databinding.FragmentLetterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LetterFragment : Fragment() {
    private lateinit var binding: FragmentLetterBinding
    private val letterModel: LetterViewModel by viewModels()
    private val args: LetterFragmentArgs by navArgs()
    private var list = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val letter = args.letter
        letterModel.apply {
            setLetter(letter)
            getEnglishList()
            list = englishList.value
        }
        binding.apply {
            titleLetter.text = "Буква $letter"
            listWords.text = list.toString()
        }


    }

    companion object {
        fun newInstance() =
            LetterFragment()
    }
}
