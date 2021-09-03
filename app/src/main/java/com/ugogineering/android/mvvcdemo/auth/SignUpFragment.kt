package com.ugogineering.android.mvvcdemo.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ugogineering.android.mvvcdemo.MainActivity
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.data.model.SignupBody
import com.ugogineering.android.mvvcdemo.databinding.FragmentSignUpBinding


/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    //private lateinit var userPreferences: UserPreferences
    private lateinit var binding: FragmentSignUpBinding
    // Creating a reference to the SignUpViewModel
    private lateinit var viewModel: SignUpViewModel
    //private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_sign_up, container, false)
        // Initializing the viewmodel object
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        //userPreferences = UserPreferences(requireContext())

        // Setting the viewmodel for databinding - this allows the bound layout access to all the data in the ViewModel
        binding.signUpViewModel = viewModel
        // Specifying the fragment view as the lifecycle owner of the binding. This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner
        
        
        
        binding.signupButton.setOnClickListener { 
            if (validateData()) {
                viewModel.signUp(SignupBody(binding.firstName.text.toString().trim(),
                    binding.email.text.toString(), binding.password.text.toString(),
                    binding.passwordAgain.text.toString().trim(), binding.lastName.text.toString().trim(),
                    binding.phone.text.toString()))
                // Trigger navigation to SignUpReportFragment
                //authViewModel.goToSignUpReportFragment()
            }
        }


//        viewModel.signupStatus.observe(viewLifecycleOwner, { saveUserToken ->
//            if(saveUserToken) {
//                lifecycleScope.launch {
//                    userPreferences.saveAuthToken(viewModel.userToken.value.toString())
//                }
//            }
//            goToMainActivity()
//        })



        return binding.root
    }
    private fun goToMainActivity(){
        startActivity(Intent(context, MainActivity::class.java))
        viewModel.goToMainActivityComplete()
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