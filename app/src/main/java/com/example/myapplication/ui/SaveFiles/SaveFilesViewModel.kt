package com.example.myapplication.ui.SaveFiles

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.MyApplication
import com.google.android.material.internal.ContextUtils.getActivity
import java.io.File

class SaveFilesViewModel : ViewModel() {
//    val path = "/storage/emulated/0/Android/data/com.example.myapplication/files"
    val path= MyApplication.context.getExternalFilesDir(null)?.path
    val file = File(path) ///storage/emulated/0/Android/data/com.example.myapplication/files
    private val fileList:MutableLiveData<Array<File>> =MutableLiveData<Array<File>>().apply {
        value=file.listFiles()
    }
    val mfileList = fileList
}