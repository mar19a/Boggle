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
import com.example.boggle.R
import androidx.fragment.app.activityViewModels

class Fragment1 : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var buttons: List<Button>
    private lateinit var clearButton: Button
    private lateinit var submitButton: Button
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var lastButton: Int = -1
    private var curText: String = ""
    private var alreadyClicked: MutableList<Int> = mutableListOf()
    private var posToChar = arrayOf("S", "T", "N", "G", "E", "I", "A", "E", "D", "R", "L", "S", "S", "E", "P", "O")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons(view)
        setupListeners()
        createNewGrid()
    }

    private fun setupButtons(view: View) {
        buttons = (1..16).map { view.findViewById<Button>(resources.getIdentifier("button$it", "id", requireActivity().packageName)) }
        clearButton = view.findViewById(R.id.clear)
        submitButton = requireActivity().findViewById(R.id.submit)
    }

    private fun setupListeners() {
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { handleTextButton(it, index + 1) }
        }
        clearButton.setOnClickListener { handleClearButton() }
        submitButton.setOnClickListener { handleSubmitButton() }

        sharedViewModel.newGameClick.observe(viewLifecycleOwner) {
            handleClearButton()
            createNewGrid()
        }
    }

    private fun handleTextButton(view: View, buttonNum: Int) {
        if (ifValidButtonPress(buttonNum, lastButton)) {
            updateGameState(buttonNum)
            view.setBackgroundColor(0xFF009200.toInt())
            updateTextView(curText)
        } else {
            Toast.makeText(activity, "This is not a valid letter selection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateGameState(buttonNum: Int) {
        lastButton = buttonNum
        alreadyClicked.add(buttonNum)
        curText += posToChar[buttonNum - 1]
    }

    private fun updateTextView(text: String) {
        val textView: TextView = requireView().findViewById(R.id.current_word)
        textView.text = text
    }

    private fun handleClearButton() {
        lastButton = -1
        curText = ""
        alreadyClicked.clear()
        buttons.forEach { it.setBackgroundColor(0xFF0000FF.toInt()) }
        updateTextView(curText)
    }

    private fun handleSubmitButton() {
        val textView: TextView = requireView().findViewById(R.id.current_word)
        sharedViewModel.typedText.value = textView.text.toString().lowercase()
        handleClearButton()
    }

    private fun createNewGrid() {
        val vowels = listOf('a', 'e', 'i', 'o', 'u')
// Repeat the vowels 4 times and concatenate with other characters
        val allowedChars = ('a'..'z').toList() + List(4) { vowels }.flatten() + listOf('s', 'z', 'p', 'x', 'q')
        posToChar = (1..16).map { allowedChars.random().toString().uppercase() }.toTypedArray()
        buttons.forEachIndexed { index, button -> button.text = posToChar[index] }

    }

    private fun ifValidButtonPress(butNum: Int, lastBut: Int): Boolean {
        if (lastBut == -1) return true
        val possibilities = mutableListOf<Int>().apply {
            addAll(listOf(lastBut - 4, lastBut + 4))
            when (lastBut % 4) {
                0 -> addAll(listOf(lastBut - 1, lastBut - 5, lastBut + 3))
                1 -> addAll(listOf(lastBut + 1, lastBut - 3, lastBut + 5))
                2, 3 -> addAll(listOf(lastBut - 1, lastBut + 1, lastBut - 5, lastBut + 3, lastBut - 3, lastBut + 5))
            }
        }
        return butNum in possibilities && butNum !in alreadyClicked
    }
}