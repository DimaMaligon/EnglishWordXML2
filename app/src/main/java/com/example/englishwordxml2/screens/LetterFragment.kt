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
import com.example.englishwordxml2.R
import com.example.englishwordxml2.databinding.FragmentLetterBinding
import com.example.englishwordxml2.view_model.LetterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LetterFragment : Fragment() {
    private lateinit var binding: FragmentLetterBinding
    private val args: LetterFragmentArgs by navArgs()
    private var list = listOf<String>()
    private val letterModel: LetterViewModel by activityViewModels()

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
        getListWords()

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

    fun searchWord(){
        binding.searchLetter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val q = query
                letterModel.apply {
                        getEnglishTranslateWord(searchWord.value)
                        binding.listWords.text = translateWord.value
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.isEmpty() == true){
                    getListWords()
                }
                letterModel.apply {
                        newText?.let { setSearchWord(it) }
                        getEnglishTranslateWord(searchWord.value)
                        binding.listWords.text = translateWord.value
                }
                Log.d("querynewText","111 $newText")
                return false
            }
        })
    }

    fun  getListWords(){
        letterModel.apply {
            getEnglishList()
            list = englishList.value
        }

        binding.apply {
            var textWord = ""
            for (word in list) {
                textWord += "$word \n"
            }
            listWords.text = textWord
        }
    }

    companion object {
        fun newInstance() =
            LetterFragment()
    }
}
