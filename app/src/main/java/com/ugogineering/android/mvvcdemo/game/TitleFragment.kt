package com.ugogineering.android.mvvcdemo.game

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.auth.AuthActivity
import com.ugogineering.android.mvvcdemo.databinding.FragmentTitleBinding


/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        binding.goToSignupButton.setOnClickListener {
            goToAuthActivity()
        }

        return binding.root
    }

    private fun goToAuthActivity() {
        val intent = Intent(context, AuthActivity::class.java)
        startActivity(intent)
    }


}