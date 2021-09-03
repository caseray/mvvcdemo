package com.ugogineering.android.mvvcdemo.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.databinding.FragmentSignupTitleBinding


/**
 * A simple [Fragment] subclass.
 */
class SignupTitleFragment : Fragment() {
    //private lateinit var binding: FragmentSignupTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup_title, container, false)
        val binding: FragmentSignupTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_signup_title, container, false
        )



        binding.goToLoginButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_signupTitleFragment_to_loginFragment)
        }
        //binding.goToLoginButton.setOnClickListener { goToLoginFragment()  }

        binding.goToSignupButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_signupTitleFragment_to_signUpFragment)
        }
        //binding.goToSignupButton.setOnClickListener { goToSignUpFragment() }

        return binding.root
    }

//    private fun goToSignUpFragment() {
//        val action = SignupTitleFragmentDirections.actionSignupTitleFragmentToSignUpFragment()
//        NavHostFragment.findNavController(this).navigate(action)
//    }
//
//    private fun goToLoginFragment() {
//        val action = SignupTitleFragmentDirections.actionSignupTitleFragmentToLoginFragment()
//        NavHostFragment.findNavController(this).navigate(action)
//    }

}