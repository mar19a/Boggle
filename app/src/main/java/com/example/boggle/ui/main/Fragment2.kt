package com.example.boggle.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.boggle.R
import java.io.InputStream

class Fragment2 : Fragment() {

    private lateinit var viewModel: MainViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var newGameButton: Button
    private val dictionary: MutableCollection<String> = mutableListOf()

    private var alreadySubmitted: MutableCollection<String> = mutableListOf()
    private var score: Int = 0

    private fun getScoreStr(word: String): String {
        if (!isValidWord(word)) {
            return "Score: $score"
        }

        val vowelCount = word.count { it in "aeiou" }
        val consonantBonus = if (word.any { it in "szpxq" }) 2 else 1
        val baseScore = vowelCount * 5 + (word.length - vowelCount)
        score += baseScore * consonantBonus

        val message = if (consonantBonus > 1) "Bravo! Double points!" else "Bravo!"
        showToast(message)

        return "Score: $score"
    }

    private fun isValidWord(word: String): Boolean {
        when {
            word.length < 4 -> showToast("Words should be at least 4 characters long")
            word.count { it in "aeiou" } < 2 -> showToast("At least 2 vowels should be used")
            alreadySubmitted.contains(word) -> showToast("Word already submitted")
            !dictionary.contains(word) -> showToast("This is not a word in English")
            else -> return true
        }

        score = (score - 10).coerceAtLeast(0)
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val inputStream: InputStream = getResources().assets.open("words.txt")
        inputStream.bufferedReader().forEachLine{ dictionary.add(it.lowercase()) }

        newGameButton = requireView().findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener {
            sharedViewModel.newGameClick.value = true
            score = 0
            alreadySubmitted = mutableListOf()
            val textView: TextView = requireView().findViewById<View>(R.id.score_text) as TextView
            textView.text = "Score: 0"
        }

        sharedViewModel.typedText.observe(viewLifecycleOwner) {str ->
            val textView: TextView = requireView().findViewById<View>(R.id.score_text) as TextView
            textView.text = getScoreStr(str)
        }
    }

}