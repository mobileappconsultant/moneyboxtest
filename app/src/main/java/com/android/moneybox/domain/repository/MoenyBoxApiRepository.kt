package com.android.moneybox.domain.repository

import com.android.moneybox.domain.model.*
import com.android.moneybox.domain.remote.MoneyBoxApiService
import io.reactivex.Observable
import javax.inject.Inject

class MoenyBoxApiRepository @Inject constructor(
    private val moneyBoxApiService: MoneyBoxApiService
) :
    Repository {

    override fun login(loginRequestBody: LoginRequestBody): Observable<MoneyBoxLoginResponseWrapper>? {
        return moneyBoxApiService.moneyLoginRequestEndpoint(loginRequestBody).map {
            MoneyBoxLoginResponseWrapper().initializeFromResponse(
                it.body()!!
            )

        }
    }

    override fun getInvestorProducts(): Observable<MoneyBoxInvestorResponseWrapper> {
        return moneyBoxApiService.moneyBoxInvestorProducts().map {
            MoneyBoxInvestorResponseWrapper().initializeFromResponse(it.body()!!)
        }
    }

    override fun makeOneOffPayment(moneyBoxOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest): Observable<MoneyBoxLoginResponseWrapper>? {
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
