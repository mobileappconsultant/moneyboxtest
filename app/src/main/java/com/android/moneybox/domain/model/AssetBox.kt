package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AssetBox {
    @SerializedName("Title")
    @Expose
    var title: String? = null
}