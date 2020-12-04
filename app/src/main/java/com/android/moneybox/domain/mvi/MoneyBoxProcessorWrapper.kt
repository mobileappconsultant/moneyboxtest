package com.android.moneybox.domain.mvi

import com.android.moneybox.domain.SchedulerProvider
import com.android.moneybox.domain.model.MoneyBoxOneOffPaymentRequest
import com.android.moneybox.domain.mvi.moneyboxactions.MoneyBoxAction
import com.android.moneybox.domain.mvi.moneyboxactions.MoneyBoxAction.LoginAction
import com.android.moneybox.domain.mvi.moneyboxresult.MoneyBoxActionResult
import com.android.moneybox.domain.mvi.moneyboxresult.MoneyBoxActionResult.LoginResult
import com.android.moneybox.domain.repository.MoenyBoxApiRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoneyBoxProcessorWrapper @Inject constructor(
    private val repository: MoenyBoxApiRepository,
    private val appSchedulerProvider: SchedulerProvider
) {

    lateinit var makeOneOffPaymentRequest: MoneyBoxOneOffPaymentRequest


    private val loginProcessor =
        ObservableTransformer<LoginAction, LoginResult> { action ->
            action.flatMap {
                repository.login(it.loginRequestBody)?.toObservable()
                    ?.map { loginResponse -> LoginResult.Success(loginResponse) }
                    ?.cast(LoginResult::class.java)
                    ?.onErrorReturn(LoginResult::Failure)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribeOn(Schedulers.io())
                    ?.startWith(LoginResult.Loading)
            }
        }


    private val investorProductProcessor =
        ObservableTransformer<MoneyBoxAction.GetAllInvestorProductsAction, MoneyBoxActionResult.GetInvestorProdcutsResult> { actions ->
            actions.flatMap {
                repository.getInvestorProducts().toObservable()
                    .map { investorProducts ->
                        MoneyBoxActionResult.GetInvestorProdcutsResult.Success(
                            investorProducts
                        )
                    }
                    .cast(MoneyBoxActionResult.GetInvestorProdcutsResult::class.java)
                    .onErrorReturn(MoneyBoxActionResult.GetInvestorProdcutsResult::Failure)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.startWith(MoneyBoxActionResult.GetInvestorProdcutsResult.Loading)
            }
        }


    private val oneOffPaymentProcessor =
        ObservableTransformer<MoneyBoxAction.MakeOneOffPaymentAction, MoneyBoxActionResult.OneOfPaymentResult> { actions ->
            actions.flatMap {
                repository.makeOneOffPayment(makeOneOffPaymentRequest)?.toObservable()
                    ?.map { oneOffPaymentResult ->
                        MoneyBoxActionResult.OneOfPaymentResult.Success(
                            oneOffPaymentResult
                        )
                    }
                    ?.cast(MoneyBoxActionResult.OneOfPaymentResult::class.java)
                    ?.onErrorReturn(MoneyBoxActionResult.OneOfPaymentResult::Failure)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
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

                Observable.merge(
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