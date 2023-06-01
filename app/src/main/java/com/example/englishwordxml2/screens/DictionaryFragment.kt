package com.example.englishwordxml2.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.englishwordxml2.adapter.LetterAdapter
import com.example.englishwordxml2.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryBinding
    private val adapter = LetterAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerLetter.layoutManager = GridLayoutManager(activity, 4)
            recyclerLetter.adapter = adapter
        }
    }

    companion object {
        fun newInstance() =
            DictionaryFragment()
    }
}