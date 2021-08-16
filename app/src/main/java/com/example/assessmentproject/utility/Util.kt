package com.example.assessmentproject.utility
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.assessmentproject.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Util {
    companion object{
        //toast alert message
        fun showToastMessage(context: Context ,message:String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        //get format date
        fun getFormatDate(dateStr : String): String{
            val temperDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+mm:ss").parse(dateStr)
            return SimpleDateFormat("HH:mm").format(temperDate)
        }
        //get Registration Details from SharedPreferences
        fun getRegistrationDetails(pref: SharedPreferences?): User? {
            val json = pref?.getString(Config.REGISTRATION, null)
            val type: Type = object : TypeToken<User?>() {}.type
            return Gson().fromJson<User>(json, type)
        }
        //save Registration Details to SharedPreferences
        fun saveDataSharedPreference(user: User, pref: SharedPreferences?){
            val editor = pref?.edit()
            editor?.putString(Config.REGISTRATION, Gson().toJson(user))
            editor?.apply()
        }
        //get Currency
        fun getCurrencySymbol(symbol:String):String{
            return when(symbol) {
                "EUR" -> {"€"}  else -> { "DOLLAR" }
            }
        }
        //get Currency
        fun getCurrentDate(): String{
            val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val now: LocalDateTime = LocalDateTime.now()
            return dtf.format(now)
        }
    }

}