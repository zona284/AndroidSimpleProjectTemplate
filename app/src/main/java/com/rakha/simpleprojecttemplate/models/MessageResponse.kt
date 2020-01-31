package com.rakha.simpleprojecttemplate.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   2020-01-31
 */
class MessageResponse {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("body")
    @Expose
    var body: Any? = null
        private set

    fun setBody(body: String) {
        this.body = body
    }
}