package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Fund {
    @SerializedName("FundId")
    @Expose
    var fundId: Int? = null

    @SerializedName("Name")
    @Expose
    var name: String? = null

    @SerializedName("LogoUrl")
    @Expose
    var logoUrl: String? = null

    @SerializedName("IsFundDMB")
    @Expose
    var isFundDMB: Boolean? = null
}