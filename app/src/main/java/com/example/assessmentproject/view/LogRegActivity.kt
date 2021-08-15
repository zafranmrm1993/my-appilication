package com.example.assessmentproject.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assessmentproject.R
import com.example.assessmentproject.model.User
import com.example.assessmentproject.utility.Config
import com.example.assessmentproject.utility.Util
import kotlinx.android.synthetic.main.activity_log_reg.*


class LogRegActivity : AppCompatActivity() {

    private var pref: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_reg)
        supportActionBar?.hide()
        setupPreference()
        if (intent.hasExtra(Config.ISLOGIN)){
            if (intent.getBooleanExtra(Config.ISLOGIN,true)){
                loginView()
            }else{
                registerView()
            }
        }else{
            setupTheContendView()
        }
        clicks()
    }
    //load Shared Preference object
    private fun setupPreference(){
        pref = getSharedPreferences(Config.APP_PRE_NAME, MODE_PRIVATE);
    }

    //setup contend
    private fun setupTheContendView(){
        if (Util.getRegistrationDetails(pref) != null){
            //login
            loginView()
        }else{
            //register
            registerView()
        }
    }

    private fun registerView(){
        txtTitle.text = getString(R.string.user_register)
        textInputLayoutUsername.visibility = View.VISIBLE
        textInputLayoutAddress.visibility = View.VISIBLE
        textInputLayoutPassword.visibility = View.VISIBLE
        textInputLayoutConPassword.visibility = View.VISIBLE
        btnLoginReg.text = Config.REGISTER
    }

    private fun loginView(){
        txtTitle.text = getString(R.string.user_login)
        textInputLayoutUsername.visibility = View.VISIBLE
        textInputLayoutAddress.visibility = View.GONE
        textInputLayoutPassword.visibility = View.VISIBLE
        textInputLayoutConPassword.visibility = View.GONE
        btnLoginReg.text = Config.LOGIN
    }

    private fun clicks(){
        var message : String? =""
        btnLoginReg.setOnClickListener {
            var userObj :User? = Util.getRegistrationDetails(pref)
            if (btnLoginReg.text == Config.LOGIN){
                if (editTxtUsername.text.toString() == userObj?.name
                    && editTxtPassword.text.toString() == userObj?.password){
                    message = getString(R.string.successfully_logged)
                    startActivity(Intent(this@LogRegActivity, MainActivity::class.java))
                }else{
                    message = getString(R.string.invalid_login)
                }
            }else if (btnLoginReg.text ==  Config.REGISTER){
                if (validation()){
                    message = if (editTxtPassword.text.toString() == editTxtConPassword.text.toString()){
                        var userDetails = User(
                            editTxtUsername.text.toString(),
                            editTxtPassword.text.toString(),
                            editTxtAddress.text.toString()
                        )
                        Util.saveDataSharedPreference(userDetails,pref)
                        loginView()
                        clearTheData()
                        getString(R.string.successfully_registered)
                    }else{
                        getString(R.string.password_does_not_match)
                    }
                }else{
                    message = getString(R.string.please_fill_the_form)
                }

            }
            message?.let { it1 -> Util.showToastMessage(this, it1) }
        }
    }

    private fun validation():Boolean{
        return editTxtUsername.text.toString().isNotEmpty() &&
                editTxtPassword.text.toString().isNotEmpty() &&
        editTxtAddress.text.toString().isNotEmpty() &&
        editTxtConPassword.text.toString().isNotEmpty()
    }

    private fun clearTheData(){
        editTxtUsername.setText("")
        editTxtPassword.setText("")
        editTxtAddress.setText("")
    }
}