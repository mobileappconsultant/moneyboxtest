package com.android.moneybox.domain.mvi.moneyintents

import com.android.moneybox.domain.model.Account
import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.mvi.mvibase.MviIntent

sealed class MoneyBoxIntent : MviIntent {
    object InitialIntent:MoneyBoxIntent()
    data class LoginIntent(val loginRequestBody: LoginRequestBody) : MoneyBoxIntent()
    object GetAllInvestorProductsIntent : MoneyBoxIntent()
    object MakeOneOffPaymentIntent : MoneyBoxIntent()
}



