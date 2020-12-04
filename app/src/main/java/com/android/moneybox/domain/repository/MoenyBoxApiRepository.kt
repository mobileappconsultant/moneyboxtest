package com.android.moneybox.domain.repository

import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.model.MoneyBoxInvestorResponseWrapper
import com.android.moneybox.domain.model.MoneyBoxLoginResponseWrapper
import com.android.moneybox.domain.model.MoneyBoxOneOffPaymentRequest
import com.android.moneybox.domain.remote.MoneyBoxApiService
import io.reactivex.Single
import javax.inject.Inject

class MoenyBoxApiRepository @Inject constructor(
    private val moneyBoxApiService: MoneyBoxApiService
) :
    Repository {

    override fun login(loginRequestBody: LoginRequestBody): Single<MoneyBoxLoginResponseWrapper>? {
        return moneyBoxApiService.moneyLoginRequestEndpoint(loginRequestBody).map {
            MoneyBoxLoginResponseWrapper().apply {
                actionMessage = it?.body()?.ActionMessage
                session = it?.body()?.Session
                user = it?.body()?.User
                informationMessage = it?.body()?.InformationMessage
            }

        }
    }

    override fun getInvestorProducts(): Single<MoneyBoxInvestorResponseWrapper> {
        return moneyBoxApiService.moneyBoxInvestorProducts().map {
            MoneyBoxInvestorResponseWrapper().apply {
                moneyboxEndOfTaxYear = it.body()?.moneyboxEndOfTaxYear

                totalPlanValue = it.body()?.totalPlanValue


                totalEarnings = it.body()?.totalEarnings

                totalContributionsNet = it.body()?.totalContributionsNet

                totalEarningsAsPercentage = it.body()?.totalEarningsAsPercentage


                productResponses = it.body()?.productResponses


                accounts = it.body()?.accounts
            }

        }
    }

    override fun makeOneOffPayment(moneyBoxOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest): Single<MoneyBoxLoginResponseWrapper>? {
        return moneyBoxApiService.moneyBoxOneOffPayments(moneyBoxOneOffPaymentRequest)
            .map {
                MoneyBoxLoginResponseWrapper().apply {
                    actionMessage = it?.body()?.ActionMessage
                    session = it?.body()?.Session
                    user = it?.body()?.User
                    informationMessage = it?.body()?.InformationMessage
                }

            }
    }


}
