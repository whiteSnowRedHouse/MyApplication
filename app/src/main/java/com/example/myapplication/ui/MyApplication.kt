package com.example.myapplication.ui

import android.app.Application
import android.content.Context

/**
 * @author  白雪红房子
 * @date  2021/2/20 15:19
 * @version 1.0
 */
class MyApplication:Application(){
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}