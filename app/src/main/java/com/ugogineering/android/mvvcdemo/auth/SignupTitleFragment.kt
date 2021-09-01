package com.ugogineering.android.mvvcdemo.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.databinding.FragmentSignupTitleBinding


/**
 * A simple [Fragment] subclass.
 */
class SignupTitleFragment : Fragment() {

    private lateinit var binding: FragmentSignupTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_title, container, false)


        binding.loginButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_signupTitleFragment_to_loginFragment)
        }

        binding.goToSignupButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_signupTitleFragment_to_signUpFragment)
        }


        return binding.root
    }


}