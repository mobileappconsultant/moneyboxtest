package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Contributions {
    @SerializedName("Status")
    @Expose
    var status: String? = null
}