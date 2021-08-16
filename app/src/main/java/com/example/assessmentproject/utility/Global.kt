package com.example.assessmentproject.utility

import android.app.Application
import com.example.assessmentproject.model.Data

class Globals : Application() {
    private var instance: Globals? = null

    // Global variable
    private var data :ArrayList<Data>? =null

    fun setData(d: ArrayList<Data>) {
        data = d
    }

    fun getData(): ArrayList<Data>? {
        return data
    }

    @Synchronized
    fun getInstance(): Globals? {
        if (instance == null) {
            instance = Globals()
        }
        return instance
    }
}