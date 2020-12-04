package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Documents {
    @SerializedName("KeyFeaturesUrl")
    @Expose
    var keyFeaturesUrl: String? = null
}