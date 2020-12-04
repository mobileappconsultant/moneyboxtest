package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Account {
    @SerializedName("Type")
    @Expose
    var type: String? = null

    @SerializedName("Name")
    @Expose
    var name: String? = null

    @SerializedName("Wrapper")
    @Expose
    var wrapper: Wrapper? = null
}