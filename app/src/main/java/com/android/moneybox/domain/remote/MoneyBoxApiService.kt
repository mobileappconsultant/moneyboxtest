package com.android.moneybox.domain.remote

import com.android.moneybox.domain.model.GetInvestorResponse
import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.model.MoneyBoxLoginResponse
import com.android.moneybox.domain.model.MoneyBoxOneOffPaymentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoneyBoxApiService {

    @POST("/users/login/")
    fun moneyLoginRequestEndpoint(@Body loginRequestBody: LoginRequestBody): io.reactivex.Single<Response<MoneyBoxLoginResponse>>

    @GET("/investorproducts")
    fun moneyBoxInvestorProducts(): io.reactivex.Single<Response<GetInvestorResponse>>

    @POST("/oneoffpayments")
    fun moneyBoxOneOffPayments(@Body moneyBoxOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest): io.reactivex.Single<Response<MoneyBoxLoginResponse>>
}