package com.example.myapplication.ui.SaveFiles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.File

class SaveFilesFragment : Fragment() {

    private lateinit var saveFilesViewModel: SaveFilesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        saveFilesViewModel =
               ViewModelProvider(this).get(SaveFilesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_files, container, false)

        val layoutManager=LinearLayoutManager(context)
        val recyclerView:RecyclerView=root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager=layoutManager
        saveFilesViewModel.mfileList.observe(viewLifecycleOwner, Observer {
            val adapter=filesAdapter(it)
            recyclerView.adapter=adapter
            adapter.notifyDataSetChanged()
        })
        return root
    }
}