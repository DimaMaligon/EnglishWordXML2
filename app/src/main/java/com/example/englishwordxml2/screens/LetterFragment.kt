package com.example.englishwordxml2.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishwordxml2.R
import com.example.englishwordxml2.adapter.WordAdapter
import com.example.englishwordxml2.data.EnglishWord
import com.example.englishwordxml2.databinding.FragmentLetterBinding
import com.example.englishwordxml2.view_model.LetterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LetterFragment : Fragment() {
    private lateinit var binding: FragmentLetterBinding
    private val args: LetterFragmentArgs by navArgs()
    private val letterModel: LetterViewModel by activityViewModels()
    private val adapter = WordAdapter(this)
    private lateinit var listEnglishWords: ArrayList<EnglishWord>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addActionBarMenu()

        val letter = args.letter
        letterModel.setLetter(letter)
        binding.titleLetter.text = "Буква $letter"

        letterModel.apply {
            getEnglishList()
            listEnglishWords = englishList.value as ArrayList<EnglishWord>
        }
        setListRecycler(listEnglishWords)
        searchWord()
    }

    private fun addActionBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_action_bar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.add_word -> {
                        findNavController().navigate(R.id.action_letterFragment_to_addLetterFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    fun searchWord() {
        binding.searchLetter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val listWords = filterList(newText)
                    setListRecycler(listWords as ArrayList<EnglishWord>)
                }
                if (newText?.isEmpty() == true){
                    setListRecycler(listEnglishWords)
                }
                return false
            }
        })
    }

    fun setListRecycler(listEnglishWords: ArrayList<EnglishWord>) {
        binding.apply {
            recyclerWords.layoutManager = LinearLayoutManager(activity)
            recyclerWords.adapter = adapter
            adapter.setLessonList(listEnglishWords)
        }
    }

    fun filterList(query: String): MutableList<EnglishWord> {
        val newListWords = mutableListOf<EnglishWord>()
        for (item in listEnglishWords) {
            if (item.word.lowercase().contains(query)) {
                newListWords.add(item)
                return newListWords
            }
        }
        return listEnglishWords
    }

    companion object {
        fun newInstance() =
            LetterFragment()
    }
}
