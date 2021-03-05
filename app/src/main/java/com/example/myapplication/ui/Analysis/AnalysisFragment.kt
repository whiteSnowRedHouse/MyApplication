package com.example.myapplication.ui.Analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R

class AnalysisFragment : Fragment() {

    private lateinit var homeViewModel: AnalysisViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(AnalysisViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_analysis, container, false)
        val textView: TextView = root.findViewById(R.id.textView)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}