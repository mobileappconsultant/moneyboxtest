package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HideAccounts {
    @SerializedName("Enabled")
    @Expose
    var enabled: Boolean? = null

    @SerializedName("IsHidden")
    @Expose
    var isHidden: Boolean? = null

    @SerializedName("Sequence")
    @Expose
    var sequence: Int? = null
}