package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wrapper(
    @SerializedName("Id")
    @Expose
    var id: String? = null,

    @SerializedName("TotalValue")
    @Expose
    var totalValue: Double? = null,

    @SerializedName("TotalContributions")
    @Expose
    var totalContributions: Double? = null,

    @SerializedName("EarningsNet")
    @Expose
    var earningsNet: Double? = null,

    @SerializedName("EarningsAsPercentage")
    @Expose
    var earningsAsPercentage: Double? = null
)