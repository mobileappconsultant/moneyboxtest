package com.android.moneybox.domain.mvi

import com.android.moneybox.domain.SchedulerProvider
import com.android.moneybox.domain.model.MoneyBoxOneOffPaymentRequest
import com.android.moneybox.domain.mvi.moneyboxactions.MoneyBoxAction
import com.android.moneybox.domain.mvi.moneyboxactions.MoneyBoxAction.LoginAction
import com.android.moneybox.domain.mvi.moneyboxresult.MoneyBoxActionResult
import com.android.moneybox.domain.mvi.moneyboxresult.MoneyBoxActionResult.LoginResult
import com.android.moneybox.domain.repository.MoenyBoxApiRepository
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MoneyBoxProcessorWrapper @Inject constructor(
    private val repository: MoenyBoxApiRepository,
    private val appSchedulerProvider: SchedulerProvider
) {

    lateinit var makeOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest


    private val loginProcessor =
        ObservableTransformer<LoginAction, LoginResult> { action ->
            action.flatMap {
                repository.login(it.loginRequestBody)
                    ?.map { loginResponse -> LoginResult.Success(loginResponse) }
                    ?.cast(LoginResult::class.java)
                    ?.onErrorReturn(LoginResult::Failure)
                    ?.subscribeOn(appSchedulerProvider.io())
                    ?.observeOn(appSchedulerProvider.ui())
                    ?.startWith(LoginResult.Loading)
            }
        }

    private val investorProductProcessor =
        ObservableTransformer<MoneyBoxAction.GetAllInvestorProductsAction, MoneyBoxActionResult.GetInvestorProdcutsResult> { actions ->
            actions.flatMap {
                repository.getInvestorProducts()
                    .map { investorProducts ->
                        MoneyBoxActionResult.GetInvestorProdcutsResult.Success(
                            investorProducts
                        )
                    }
                    .cast(MoneyBoxActionResult.GetInvestorProdcutsResult::class.java)
                    .onErrorReturn(MoneyBoxActionResult.GetInvestorProdcutsResult::Failure)
                    ?.subscribeOn(appSchedulerProvider.io())
                    ?.observeOn(appSchedulerProvider.ui())
                    ?.startWith(MoneyBoxActionResult.GetInvestorProdcutsResult.Loading)
            }
        }


    private val oneOffPaymentProcessor =
        ObservableTransformer<MoneyBoxAction.MakeOneOffPaymentAction, MoneyBoxActionResult.OneOfPaymentResult> { actions ->
            actions.flatMap {
                repository.makeOneOffPayment(makeOneOffPaymentRequest)
                    ?.map { oneOffPaymentResult ->
                        MoneyBoxActionResult.OneOfPaymentResult.Success(
                            oneOffPaymentResult
                        )
                    }
                    ?.cast(MoneyBoxActionResult.OneOfPaymentResult::class.java)
                    ?.onErrorReturn(MoneyBoxActionResult.OneOfPaymentResult::Failure)
                    ?.subscribeOn(appSchedulerProvider.io())
                    ?.observeOn(appSchedulerProvider.ui())
                    ?.startWith(MoneyBoxActionResult.OneOfPaymentResult.Loading)
            }
        }


    val actionProcessor =
        ObservableTransformer<MoneyBoxAction, MoneyBoxActionResult> { actions ->
            //publish to ConnectableObservable so upstream is subscribed only once and multicast to observers
            actions.publish { actionObservable ->
                /*         actionObservable.ofType(LoginAction::class.java)
                             .compose(loginProcessor)*/
                /*         actionObservable.ofType(MoneyBoxAction.MakeOneOffPaymentAction::class.java)
                             .compose(oneOffPaymentProcessor)
                         actionObservable.ofType(MoneyBoxAction.GetAllInvestorProductsAction::class.java)
                             .compose(investorProductProcessor)*/

                io.reactivex.Observable.merge(
                    actionObservable.ofType(LoginAction::class.java)
                        .compose(loginProcessor),
                    actionObservable.ofType(MoneyBoxAction.MakeOneOffPaymentAction::class.java)
                        .compose(oneOffPaymentProcessor),
                    actionObservable.ofType(MoneyBoxAction.GetAllInvestorProductsAction::class.java)
                        .compose(investorProductProcessor)
                )
            }

        }


}