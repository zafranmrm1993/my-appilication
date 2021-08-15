package com.example.assessmentproject.utility
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.assessmentproject.R
import com.example.assessmentproject.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object{
        fun showToastMessage(context: Context ,message:String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        fun getFormatDate(dateStr : String): String{
            val temperDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+mm:ss").parse(dateStr)
            return SimpleDateFormat("HH:mm").format(temperDate)
        }
        fun getRegistrationDetails(pref: SharedPreferences?): User? {
            val gson = Gson()
            val json = pref?.getString(Config.REGISTRATION, null)
            val type: Type = object : TypeToken<User?>() {}.type
            return gson.fromJson<User>(json, type)
        }
        fun saveDataSharedPreference(user: User, pref: SharedPreferences?){
            val editor = pref?.edit()
            val gson = Gson()
            val json = gson.toJson(user)
            editor?.putString(Config.REGISTRATION, json)
            editor?.apply()
        }
    }
}