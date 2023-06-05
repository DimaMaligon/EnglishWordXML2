package com.example.englishwordxml2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordxml2.data.EnglishWord
import com.example.englishwordxml2.databinding.WordOneLayoutBinding

class WordAdapter constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {
    private var listWords = mutableListOf<EnglishWord>()

    @SuppressLint("NotifyDataSetChanged")
    fun setLessonList(listWords: List<EnglishWord>) {
        this.listWords = listWords.toMutableList()
        notifyDataSetChanged()
    }

    class WordViewHolder(val binding: WordOneLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding =
            WordOneLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listWords.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        with(holder) {
            val englishWord = listWords[position]
            binding.wordDescription.text = "${englishWord.word} - ${englishWord.translate}"
        }
    }
}