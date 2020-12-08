package com.android.moneybox.domain.remote

import com.android.moneybox.domain.model.GetInvestorResponse
import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.model.MoneyBoxLoginResponse
import com.android.moneybox.domain.model.MoneyBoxOneOffPaymentRequest
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoneyBoxApiService {

    @POST("/users/login/")
    fun moneyLoginRequestEndpoint(@Body loginRequestBody: LoginRequestBody): Observable<Response<MoneyBoxLoginResponse>>

    @GET("/investorproducts")
    fun moneyBoxInvestorProducts(): Observable<Response<GetInvestorResponse>>

    @POST("/oneoffpayments")
    fun moneyBoxOneOffPayments(@Body moneyBoxOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest): Observable<Response<MoneyBoxLoginResponse>>
}