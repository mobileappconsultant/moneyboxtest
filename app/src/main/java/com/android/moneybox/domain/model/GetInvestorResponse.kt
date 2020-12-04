package com.android.moneybox.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetInvestorResponse(

@SerializedName("MoneyboxEndOfTaxYear")
@Expose
var moneyboxEndOfTaxYear: String? = null,

@SerializedName("TotalPlanValue")
@Expose
var totalPlanValue: Double? = null,

@SerializedName("TotalEarnings")
@Expose
var totalEarnings: Double? = null,

@SerializedName("TotalContributionsNet")
@Expose
var totalContributionsNet: Double? = null,

@SerializedName("TotalEarningsAsPercentage")
@Expose
var totalEarningsAsPercentage: Double? = null,

@SerializedName("ProductResponses")
@Expose
var productResponses: List<ProductResponse>? = null,

@SerializedName("Accounts")
@Expose
var accounts: List<Account>? = null

)