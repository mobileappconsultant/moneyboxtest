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