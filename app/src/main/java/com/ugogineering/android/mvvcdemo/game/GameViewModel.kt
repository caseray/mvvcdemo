package com.ugogineering.android.mvvcdemo.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    // The questions data class
    data class Question(
        val text: String,
        val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What are the three departments that comprise Hazontech?",
            answers = listOf("Data, Growth, Software", "Engineering, Marketing, Sales",
            "Engineering, Analytics, Marketing", "Business Development, Engineering, Research")),
        Question(text = "Who is generally regarded as the father of computing",
            answers = listOf("Charles Babbage", "Charles Dickens", "Nikolas Tesla", "Alan Turing" )),
        Question(text = "Which of the following companies is the oldest?",
            answers = listOf("IBM", "Alphabet", "Microsoft", "Apple")),
        Question(text = "What is Android Jetpack?",
            answers = listOf("All of these", "Tools", "Documentation", "Libraries")),
        Question(text = "In which of the following sectors can you apply technology?",
            answers = listOf("All of these", "Agriculture", "Medicine", "Trade")),
        Question(text = "Who created Git?",
            answers = listOf("Linus Torvalds", "Junio Hamano", "The Github company", "Julio Montana")),
        Question(text = "In what year was Git created?",
            answers = listOf("2005", "2006", "2007", "2008")),
        Question(text = "When was the first computer invented?",
            answers = listOf("1943", "1890", "1934", "1959")),
        Question(text = "What was the name of the first computer invented?",
            answers = listOf("Electronic Numerical Integrator and Computer (ENIAC)", "IBM System/360",
            "Pentium Pro PC", "UNIVersal Automatic Computer (UNIVAC)")),
        Question(text = "Which of these was the first supercomputer?",
            answers = listOf("CDC 6600", "Sierra", "Summit", "Cray-1"))

    )

    // The current question
    private var _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question>
        get() = _currentQuestion

    private var _answersArray = MutableLiveData<List<String>>()
    val answersArray: LiveData<List<String>>
        get() = _answersArray

    private val _questionIndex = MutableLiveData<Int>()
    val questionIndex: LiveData<Int>
        get() = _questionIndex

    private val _numQuestions = MutableLiveData<Int>()
    val numQuestions: LiveData<Int>
        get() = _numQuestions

    // Event which triggers that the game has been won
    private val _eventGameWon = MutableLiveData<Boolean>()
    val eventGameWon: LiveData<Boolean>
        get() = _eventGameWon

    // Event which triggers that the game has been lost
    private val _eventGameLost = MutableLiveData<Boolean>()
    val eventGameLost: LiveData<Boolean>
        get() = _eventGameLost

    // Event which triggers update of action bar
    private val _eventUpdateActionBar = MutableLiveData<Boolean>()
    val eventUpdateActionBar: LiveData<Boolean>
        get() = _eventUpdateActionBar

    init {
        Log.i("GameViewModel", "Game View Model Created!")
        // Shuffles the questions and sets the question index to the first question
        randomizeQuestions()
        _numQuestions.value = Math.min((questions.size + 1) / 2, 3)
    }

    // Randomizes the questions and sets the first
    private fun randomizeQuestions() {
        questions.shuffle()
        _questionIndex.value = 0
        setQuestion()
    }


    private fun setQuestion() {

        _currentQuestion.value = questions[questionIndex.value?:0]
        // Randomize the answers into a copy of the array
        _answersArray.value = currentQuestion.value?.answers?.shuffled()

        updateActionBar()
    }

    // Method to process answer
    fun processAnswer(answerIndex: Int) {
        // The first answer in the original question is always the correct one, so if our
        // answer matches we have the correct answer

        if(answersArray.value?.get(answerIndex) ?: 0 == currentQuestion.value?.answers?.get(0) ?: 0) {
            _questionIndex.value = questionIndex.value?.plus(1)

            // Advance to the next question
            if (questionIndex.value!! < numQuestions.value!!) {
                _currentQuestion.value = questions[questionIndex.value?:0]
                setQuestion()
            } else {
                // The game has been won. Trigger gameWon event which navigates from game fragment to gameWon fragment
                onGameWon()
            }
        } else {
            // Game Lost. A wrong answer was clicked. Navigate to game lost fragment.
            onGameLost()
        }

    }// End of process answer method

    private fun updateActionBar() {
        _eventUpdateActionBar.value = true
    }

    fun updateActionBarComplete() {
        _eventUpdateActionBar.value = false
    }

    private fun onGameWon() {
        _eventGameWon.value = true
    }
    private fun onGameLost() {
        _eventGameLost.value = true
    }
    fun onGameWonActionComplete() {
        _eventGameWon.value = false
    }
    fun onGameLostActionComplete() {
        _eventGameLost.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "Game View Model Destroyed!")
    }

}