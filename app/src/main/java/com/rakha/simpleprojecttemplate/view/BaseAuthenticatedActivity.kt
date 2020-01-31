package com.rakha.simpleprojecttemplate.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rakha.simpleprojecttemplate.models.LoginModel
import com.rakha.simpleprojecttemplate.models.ProfileModel
import com.rakha.simpleprojecttemplate.services.SPData

/**
 *   Created By rakha
 *   2020-01-31
 */
open class BaseAuthenticatedActivity: AppCompatActivity() {
    lateinit var userLogin: LoginModel
    lateinit var userProfile: ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserLoginData()
        getProfileData()
    }

    fun getUserLoginData() : LoginModel {
        userLogin = Gson().fromJson(SPData.getSP(this, SPData.LOGIN, ""), LoginModel::class.java)
        return userLogin
    }

    fun getProfileData() : ProfileModel {
        userProfile = Gson().fromJson(SPData.getSP(this, SPData.PROFILE, Gson().toJson(ProfileModel())), ProfileModel::class.java)
        return userProfile
    }
}