package com.rakha.simpleprojecttemplate.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *   Created By rakha
 *   2020-01-31
 */
class BasicResponse<T> {
    @SerializedName("rescode")
    @Expose
    private var rescode: String? = null

    @SerializedName("message")
    @Expose
    private var message: MessageResponse? = null

    @SerializedName("data")
    @Expose
    private var data: T? = null

    fun getRescode(): String? {
        return rescode
    }

    fun setRescode(rescode: String) {
        this.rescode = rescode
    }

    fun getMessage(): MessageResponse? {
        return message
    }

    fun setMessage(message: MessageResponse) {
        this.message = message
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }
}