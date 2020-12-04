package com.android.moneybox.domain.mvi.moneyboxviewstates

import com.android.moneybox.domain.model.MoneyBoxInvestorResponseWrapper
import com.android.moneybox.domain.model.MoneyBoxLoginResponseWrapper
import com.android.moneybox.domain.mvi.mvibase.MviViewState

data class AuthenticationViewState(
    val isLoading: Boolean = false,
    val loginResponse: MoneyBoxLoginResponseWrapper? = MoneyBoxLoginResponseWrapper(),
    val investorResponse: MoneyBoxInvestorResponseWrapper? = MoneyBoxInvestorResponseWrapper(),
    val error: Throwable? =null
) : MviViewState {
    companion object {
        fun scanInitialState(): AuthenticationViewState = AuthenticationViewState(
            isLoading = false,
            error = null
        )


    }

}