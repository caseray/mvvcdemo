package com.ugogineering.android.mvvcdemo.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.ugogineering.android.mvvcdemo.R
import com.ugogineering.android.mvvcdemo.databinding.FragmentSignUpReportBinding


/**
 * A simple [Fragment] subclass.
 */
class SignUpReportFragment : Fragment() {

    private lateinit var binding: FragmentSignUpReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_sign_up_report, container, false)


        return binding.root
    }


}