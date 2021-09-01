package com.ugogineering.android.mvvcdemo.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.databinding.FragmentLoginReportBinding


/**
 * A simple [Fragment] subclass.
 */
class LoginReportFragment : Fragment() {
    private lateinit var binding: FragmentLoginReportBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login_report, container, false)

        // Setting the viewmodel for databinding - this allows the bound layout access to all the data in the ViewModel
        binding.authViewModel = authViewModel
        // Specifying the fragment view as the lifecycle owner of the binding. This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


}