package com.rakha.simpleprojecttemplate.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rakha.simpleprojecttemplate.R
import com.rakha.simpleprojecttemplate.models.LoginModel
import com.rakha.simpleprojecttemplate.services.SPData

/**
 *   Created By rakha
 *   2020-01-31
 */
class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val login = Gson().fromJson(SPData.getSP(this, SPData.LOGIN, ""), LoginModel::class.java)
        Handler().postDelayed({
            login?.let {
                if (!it.accessToken.isNullOrEmpty()){
                    goToMain()
                }
            }?: launch()
        }, 3000)

    }

    private fun launch(){
//        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val options = ActivityOptions.makeSceneTransitionAnimation(this)
//            startActivity(intent, options.toBundle())
//        } else {
//            startActivity(intent)
//        }
//        finish()
    }

    private fun goToMain(){
//        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
//        startActivity(intent)
//        finish()
    }
}