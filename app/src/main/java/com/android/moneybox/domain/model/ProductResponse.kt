package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("Id")
    @Expose
    var id: Int? = null,

    @SerializedName("PlanValue")
    @Expose
    var planValue: Double? = null,

    @SerializedName("Moneybox")
    @Expose
    var moneybox: Double? = null,

    @SerializedName("SubscriptionAmount")
    @Expose
    var subscriptionAmount: Double? = null,

    @SerializedName("TotalFees")
    @Expose
    var totalFees: Double? = null,

    @SerializedName("IsSelected")
    @Expose
    var isSelected: Boolean? = null,

    @SerializedName("IsFavourite")
    @Expose
    var isFavourite: Boolean? = null,

    @SerializedName("CollectionDayMessage")
    @Expose
    var collectionDayMessage: String? = null,

    @SerializedName("WrapperId")
    @Expose
    var wrapperId: String? = null,

    @SerializedName("IsCashBox")
    @Expose
    var isCashBox: Boolean? = null,

    @SerializedName("AssetBox")
    @Expose
    var assetBox: AssetBox? = null,

    @SerializedName("Product")
    @Expose
    var product: Product? = null,

    @SerializedName("InvestorAccount")
    @Expose
    var investorAccount: InvestorAccount? = null,

    @SerializedName("Personalisation")
    @Expose
    var personalisation: Personalisation? = null,

    @SerializedName("Contributions")
    @Expose
    var contributions: Contributions? = null,

    @SerializedName("MoneyboxCircle")
    @Expose
    var moneyboxCircle: MoneyboxCircle? = null,

    @SerializedName("State")
    @Expose
    var state: String? = null
)