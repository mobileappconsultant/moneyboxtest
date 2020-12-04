package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuickAddDeposit {
    @SerializedName("Amount")
    @Expose
    var amount: Double? = null
}