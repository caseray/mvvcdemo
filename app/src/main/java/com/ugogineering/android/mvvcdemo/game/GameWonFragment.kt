package com.ugogineering.android.mvvcdemo.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.databinding.FragmentGameWonBinding


/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {

    private lateinit var binding: FragmentGameWonBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_game_won, container, false)

        // Add OnClick Handler for Next Match button
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

        return binding.root
    }

}