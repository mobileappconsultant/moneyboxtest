package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InvestorAccount {
    @SerializedName("ContributionsNet")
    @Expose
    var contributionsNet: Double? = null

    @SerializedName("EarningsNet")
    @Expose
    var earningsNet: Double? = null

    @SerializedName("EarningsAsPercentage")
    @Expose
    var earningsAsPercentage: Double? = null

    @SerializedName("TodaysInterest")
    @Expose
    var todaysInterest: Double? = null
}