package com.android.moneybox.domain.mvi.moneyboxresult

import com.android.moneybox.domain.model.MoneyBoxInvestorResponseWrapper
import com.android.moneybox.domain.model.MoneyBoxLoginResponseWrapper
import com.android.moneybox.domain.mvi.mvibase.MviResult

sealed class MoneyBoxActionResult : MviResult {
    object InitResult : MoneyBoxActionResult()
    sealed class LoginResult : MoneyBoxActionResult() {
        object Loading : LoginResult()
        data class Success(val moneyBoxProcessorWrapper: MoneyBoxLoginResponseWrapper) :
            LoginResult()

        data class Failure(val error: Throwable) : LoginResult()


    }

    sealed class GetInvestorProdcutsResult : MoneyBoxActionResult() {

        object Loading : GetInvestorProdcutsResult()
        data class Success(val moneyBoxProcessorWrapper: MoneyBoxInvestorResponseWrapper) :
            GetInvestorProdcutsResult()

        data class Failure(val error: Throwable) : GetInvestorProdcutsResult()
    }

    sealed class OneOfPaymentResult : MoneyBoxActionResult() {

        object Loading : OneOfPaymentResult()
        data class Success(val moneyBoxProcessorWrapper: MoneyBoxLoginResponseWrapper) :
            OneOfPaymentResult()

        data class Failure(val error: Throwable) : OneOfPaymentResult()
    }


}

