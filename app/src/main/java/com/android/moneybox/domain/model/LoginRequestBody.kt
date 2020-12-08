package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("Email")
    @Expose
    val Email: String,
    @SerializedName("Password")
    @Expose
    val Password: String
) {
    @SerializedName("Idfa")
    @Expose
    val Idfa: String = "ANYTHING"
}
