package com.android.moneybox.domain.model

class MoneyBoxInvestorResponseWrapper {

    var moneyboxEndOfTaxYear: String? = null

    var totalPlanValue: Double? = null


    var totalEarnings: Double? = null

    var totalContributionsNet: Double? = null

    var totalEarningsAsPercentage: Double? = null


    var productResponses: List<ProductResponse>? = null


    var accounts: List<Account>? = null


}

fun MoneyBoxInvestorResponseWrapper.initializeFromResponse(moneyBoxInvestorResponse: GetInvestorResponse): MoneyBoxInvestorResponseWrapper {
    this.moneyboxEndOfTaxYear = moneyBoxInvestorResponse?.moneyboxEndOfTaxYear

    this.totalPlanValue = moneyBoxInvestorResponse?.totalPlanValue


    this.totalEarnings = moneyBoxInvestorResponse?.totalEarnings

    this.totalContributionsNet = moneyBoxInvestorResponse?.totalContributionsNet

    this.totalEarningsAsPercentage = moneyBoxInvestorResponse?.totalEarningsAsPercentage


    this.productResponses = moneyBoxInvestorResponse?.productResponses


    this.accounts = moneyBoxInvestorResponse?.accounts
    return this
}