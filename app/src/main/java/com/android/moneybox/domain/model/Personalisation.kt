package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Personalisation {
    @SerializedName("QuickAddDeposit")
    @Expose
    var quickAddDeposit: QuickAddDeposit? = null

    @SerializedName("HideAccounts")
    @Expose
    var hideAccounts: HideAccounts? = null
}