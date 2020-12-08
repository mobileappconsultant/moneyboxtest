package com.android.moneybox.domain.repository

import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.model.MoneyBoxInvestorResponseWrapper
import com.android.moneybox.domain.model.MoneyBoxLoginResponseWrapper
import com.android.moneybox.domain.model.MoneyBoxOneOffPaymentRequest
import io.reactivex.Observable


interface Repository {
    fun getInvestorProducts(): Observable<MoneyBoxInvestorResponseWrapper>
    fun makeOneOffPayment(moneyBoxOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest): Observable<MoneyBoxLoginResponseWrapper>?
    fun login(loginRequestBody: LoginRequestBody): Observable<MoneyBoxLoginResponseWrapper>?
}
