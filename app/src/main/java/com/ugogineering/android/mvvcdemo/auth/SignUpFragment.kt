package com.ugogineering.android.mvvcdemo.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.data.model.SignupBody
import com.ugogineering.android.mvvcdemo.databinding.FragmentSignUpBinding


/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_sign_up, container, false)

        // Setting the viewmodel for databinding - this allows the bound layout access to all the data in the ViewModel
        binding.authViewModel = authViewModel
        // Specifying the fragment view as the lifecycle owner of the binding. This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner
        
        
        
        binding.signupButton.setOnClickListener { 
            if (validateData()) {
                authViewModel.signUp(SignupBody(binding.firstName.text.toString().trim(),
                    binding.email.text.toString(), binding.password.text.toString(),
                    binding.passwordAgain.text.toString().trim(), binding.lastName.text.toString().trim(),
                    binding.phone.text.toString()))
                // Trigger navigation to SignUpReportFragment
                //authViewModel.goToSignUpReportFragment()
            }
        }

        // Observer for trigger to goToSignUpReportFragment
        authViewModel.eventGoToSignUpReportFragment.observe(viewLifecycleOwner, { goToSignUpReport ->
            if(goToSignUpReport) goToSignUpReportFragment()
        })

        return binding.root
    }

    private fun goToSignUpReportFragment() {
        Toast.makeText(context, "Inputted data has been processed", Toast.LENGTH_SHORT).show()
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpReportFragment()
        NavHostFragment.findNavController(this).navigate(action)
        // Set navigation trigger to false.
        authViewModel.goToSignUpReportFragmentComplete()
    }


    private fun validateData(): Boolean {
        if(binding.firstName.text.toString().isEmpty()) {
            showMessage("Please enter your first name")
            return false
        }
        if(binding.lastName.text.toString().isEmpty()) {
            showMessage("Please enter your last name")
            return false
        }
        if(binding.email.text.toString().isEmpty()) {
            showMessage("Please enter your email")
            return false
        }
        if(binding.phone.text.toString().isEmpty()) {
            showMessage("Please enter your phone number")
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
        if(binding.password.text.toString().trim() != binding.passwordAgain.text.toString().trim())
        {
            showMessage("The password you re-entered does not match")
            return false
        }
        return true
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.signupButton.isEnabled = false
    }

    private fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.signupButton.isEnabled = true
    }

    private fun showMessage(s: String) {
        Snackbar.make(binding.rootLayout, s, Snackbar.LENGTH_LONG)
            .show()

    }

}