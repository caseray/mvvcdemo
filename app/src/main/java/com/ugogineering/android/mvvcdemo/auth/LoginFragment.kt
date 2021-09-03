package com.ugogineering.android.mvvcdemo.auth

import UserPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.data.model.LoginBody
import com.ugogineering.android.mvvcdemo.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var userPreferences: UserPreferences
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login, container, false)

        // Setting the viewmodel for databinding - this allows the bound layout access to all the data in the ViewModel
        binding.authViewModel = authViewModel
        // Specifying the fragment view as the lifecycle owner of the binding. This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner


        binding.loginButton.setOnClickListener {
            if (validateData()) {
                authViewModel.login(
                    LoginBody(binding.email.text.toString(),
                    binding.password.text.toString()
                    )
                )
                // Trigger navigation to LoginReportFragment
                //authViewModel.goToLoginReportFragment()
            }
        }

        // Observer for trigger to goToLoginFragment
        authViewModel.eventGoToLoginFragment.observe(viewLifecycleOwner, { goToLoginReport ->
            if(goToLoginReport) goToLoginReportFragment()
        })
        // Observer to save User Token


        return binding.root
    }

    private fun goToLoginReportFragment() {
        Toast.makeText(context, "Inputted data has been processed", Toast.LENGTH_SHORT).show()
        val action = LoginFragmentDirections.actionLoginFragmentToLoginReportFragment()
        NavHostFragment.findNavController(this).navigate(action)
        // Set navigation trigger to false
        authViewModel.goToLoginReportFragmentComplete()
    }

    private fun validateData(): Boolean {

        if(binding.email.text.toString().isEmpty()) {
            showMessage("Please enter your email")
            return false
        }

        if(binding.password.text.toString().isEmpty()) {
            showMessage("Please enter your password")
            return false
        }
        if(!binding.email.text.toString().matches("(.*)@(.*).(.*)".toRegex())){
            showMessage("The email you entered is not valid")
            return false
        }

        return true
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
    }

    private fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.loginButton.isEnabled = true
    }

    private fun showMessage(s: String) {
        Snackbar.make(binding.rootLayout, s, Snackbar.LENGTH_LONG)
            .show()
    }

}