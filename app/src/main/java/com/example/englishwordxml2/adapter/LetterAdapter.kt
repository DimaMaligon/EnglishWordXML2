package com.example.englishwordxml2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordxml2.data.Alphabet
import com.example.englishwordxml2.databinding.LetterOneLayoutBinding
import com.example.englishwordxml2.screens.DictionaryFragmentDirections

class LetterAdapter constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {
    private var alphabets = Alphabet.listLetters

    class LetterViewHolder(val binding: LetterOneLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val binding =
            LetterOneLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        with(holder) {
            val letterAlphabet = alphabets[position]
            binding.apply { letterButton.text = letterAlphabet }.also {
                it.letterButton.setOnClickListener {
                    val action =
                        DictionaryFragmentDirections.actionDictionaryFragmentToLetterFragment(
                            letterAlphabet
                        )
                    findNavController(fragment).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return alphabets.size
    }
}