package com.rakha.simpleprojecttemplate.models

import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   2020-01-31
 */
data class LoginModel(

    @field:SerializedName("access_token")
    var accessToken: String? = null,

    @field:SerializedName("refresh_token")
    var refreshToken: String? = null
)