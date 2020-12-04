package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("Name")
    @Expose
    var name: String? = null

    @SerializedName("CategoryType")
    @Expose
    var categoryType: String? = null

    @SerializedName("Type")
    @Expose
    var type: String? = null

    @SerializedName("FriendlyName")
    @Expose
    var friendlyName: String? = null

    @SerializedName("CanWithdraw")
    @Expose
    var canWithdraw: Boolean? = null

    @SerializedName("ProductHexCode")
    @Expose
    var productHexCode: String? = null

    @SerializedName("AnnualLimit")
    @Expose
    var annualLimit: Double? = null

    @SerializedName("DepositLimit")
    @Expose
    var depositLimit: Double? = null

    @SerializedName("BonusMultiplier")
    @Expose
    var bonusMultiplier: Double? = null

    @SerializedName("MinimumWeeklyDeposit")
    @Expose
    var minimumWeeklyDeposit: Double? = null

    @SerializedName("MaximumWeeklyDeposit")
    @Expose
    var maximumWeeklyDeposit: Double? = null

    @SerializedName("Documents")
    @Expose
    var documents: Documents? = null

    @SerializedName("State")
    @Expose
    var state: String? = null

    @SerializedName("Lisa")
    @Expose
    var lisa: Lisa? = null

    @SerializedName("InterestRate")
    @Expose
    var interestRate: String? = null

    @SerializedName("InterestRateAmount")
    @Expose
    var interestRateAmount: Double? = null

    @SerializedName("LogoUrl")
    @Expose
    var logoUrl: String? = null

    @SerializedName("Fund")
    @Expose
    var fund: Fund? = null
}