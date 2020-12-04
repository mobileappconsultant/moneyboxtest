package com.android.moneybox.domain.repository

import com.android.moneybox.domain.model.*
import io.reactivex.Single
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

interface Repository {
    fun getInvestorProducts(): Single<MoneyBoxInvestorResponseWrapper>
    fun makeOneOffPayment(moneyBoxOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest): io.reactivex.Single<MoneyBoxLoginResponseWrapper>?
    fun login(loginRequestBody: LoginRequestBody): @NonNull io.reactivex.Single<MoneyBoxLoginResponseWrapper>?
}
