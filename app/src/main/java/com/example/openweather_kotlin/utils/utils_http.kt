package com.example.openweather_kotlin.utils

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.adapter.rxjava3.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun handleErrorResponse(e: Throwable): String {

    return when (e) {
        is HttpException -> {
            parseHTTPError(e.response()!!.errorBody())
        }
        is SocketTimeoutException -> {
            "Timeout"
        }
        is IOException -> {
            "IOException"
        }
        else -> "ServerError"
    }
}

fun parseHTTPError(responseBody: ResponseBody?): String {
    try {
        val jsonObject = JSONObject(responseBody!!.string())
        try {
            val error = jsonObject.getString("message")
            return error
        } catch (ex: Exception) {
            responseBody!!.close()
            return ""
        }
    } catch (ex: Exception) {
        responseBody!!.close()
        return ""
    }
}