package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoneyboxCircle {
    @SerializedName("State")
    @Expose
    var state: String? = null
}