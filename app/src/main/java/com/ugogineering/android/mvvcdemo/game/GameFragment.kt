package com.ugogineering.android.mvvcdemo.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_game, container, false)

        Log.i("GameFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Bind this fragment class to the layout
        binding.gameViewModel = viewModel

        // Specifying the fragment view as the lifecycle owner of the binding. Used so that the
        // binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER") {
                view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if(-1 != checkedId) {
                var answerIndex = 0
                when(checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // Check if answer is correct and process
                viewModel.processAnswer(answerIndex)
            }
        }

        // Observer for the Game Won event
        viewModel.eventGameWon.observe(viewLifecycleOwner, {
            gameWon ->
            if (gameWon) gameWonAction()
        })
        // Observer for the Game Lost event
        viewModel.eventGameLost.observe(viewLifecycleOwner, { gameLost ->
            if (gameLost) gameLostAction()
        })

        return binding.root
    }


    private fun updateTheActionBar() {
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_computers_trivia_question, ((viewModel.questionIndex.value)?.plus(1)),
                viewModel.numQuestions.value)

        viewModel.updateActionBarComplete()
    }

    private fun gameWonAction() {
        Toast.makeText(activity, "Game Won!", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections
            .actionGameFragmentToGameWonFragment(viewModel.numQuestions.value?:0,
            viewModel.questionIndex.value?:0)
        NavHostFragment.findNavController(this).navigate(action)
        //Reset _eventGameWon value
        viewModel.onGameWonActionComplete()
    }

    private fun gameLostAction() {
        Toast.makeText(activity, "Game Lost!", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections
            .actionGameFragmentToGameOverFragment()
        NavHostFragment.findNavController(this).navigate(action)
        // Reset _eventGameLost trigger
        viewModel.onGameLostActionComplete()
    }

}