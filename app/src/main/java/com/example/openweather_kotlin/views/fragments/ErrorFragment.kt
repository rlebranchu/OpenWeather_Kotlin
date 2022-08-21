package com.example.openweather_kotlin.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.openweather_kotlin.R

class ErrorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_error, container, false)

        val homeBtn = view.findViewById<Button>(R.id.homeBtn)
        homeBtn.setOnClickListener{
            findNavController().navigate(R.id.action_errorFragment_to_mainFragment)
        }

        return view
    }
}