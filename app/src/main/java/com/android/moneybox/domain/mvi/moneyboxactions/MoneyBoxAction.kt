package com.android.moneybox.domain.mvi.moneyboxactions

import com.android.moneybox.domain.model.Account
import com.android.moneybox.domain.model.LoginRequestBody
import com.android.moneybox.domain.mvi.mvibase.MviAction

sealed class MoneyBoxAction : MviAction {
    object Load : MoneyBoxAction()
    data class LoginAction(val loginRequestBody: LoginRequestBody) : MoneyBoxAction()
    object GetAllInvestorProductsAction : MoneyBoxAction()
    object MakeOneOffPaymentAction : MoneyBoxAction()
}

